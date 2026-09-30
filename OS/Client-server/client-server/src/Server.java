
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Server {
    public static Path dir;
    public static void main(String[] args)throws Exception {
        int port = Integer.parseInt(args[0]);
        boolean nio = args[1].equalsIgnoreCase("nio");
        dir = Paths.get(args.length > 2 ? args[2] : "files").toAbsolutePath().normalize();
        
        System.out.println("Serving " + dir + " on port " + port + " mode=" + (nio ? "NIO" : "Traditional"));

        // Virtual thread ต่อ 1 connection
        ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor();

        if (nio) {
            // NIO: ต้องใช้ SocketChannel เพื่อให้ FileChannel.transferTo ทำ zero-copy ได้จริง
            try(ServerSocketChannel ssc = ServerSocketChannel.open()){
                ssc.bind(new InetSocketAddress(port));
                while (true) {
                    SocketChannel ch = ssc.accept();
                    pool.submit(() -> handle(ch.socket(), ch));
                }
            }
        } else{
            // Traditional: Socket + InputStream/OutputStream
            try(ServerSocket ss = new ServerSocket(port)){
                while (true) {
                    Socket s = ss.accept();
                    pool.submit(() -> handle(s, null));
                }
            }
        }
    }
    // จัดการ 1 connection: อ่านคำสั่งวนไปจนกว่า client จะปิด
    public static void handle(Socket socket, SocketChannel ch){
        try(socket;
            var br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.ISO_8859_1));
            var out = new BufferedOutputStream(socket.getOutputStream(), 64 * 1024)){
                // socket.setTcpNoDelay(true);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] p = line.trim().split("\\s+");
                    switch (p[0].toUpperCase()) {
                        case "LIST" -> list(out);
                        case "INFO" -> info(p, out);
                        case "GET" -> get(p, out, ch);
                        default -> reply(out, "ERROR 400 Unknown command");
                    }
                }
        }catch(IOException e){
            // client ตัดการเชื่อมต่อกลางทาง
            System.err.println("handle() error: " + e);
        }
    }

    static void list(OutputStream out)throws IOException{
        try(Stream<Path> s = Files.list(dir)){
            for (Path f : (Iterable<Path>) s.filter(Files::isRegularFile)::iterator) 
                write(out, "FILE " + f.getFileName() + " " + Files.size(f) + "\n"); 
        }

        reply(out, "END");
    }

    static void info(String[] p, OutputStream out)throws IOException{
        if (p.length != 2) {
            reply(out, "ERROR 400 Usage: INFO <filename>");
            return;
        }
        Path f = resolve(p[1]);

        if (f == null) {
            reply(out, "ERROR 404 File not found"); return;
        }
        reply(out, "SIZE " + Files.size(f));
    }

    static void get(String[] p, OutputStream out, SocketChannel ch)throws IOException{
        if (p.length != 4) {
            reply(out, "ERROR 400 Usage: GET <filename> <offset> <length>"); 
            return;
        }

        Path f = resolve(p[1]);
        if (f == null) {
            reply(out, "ERROR 404 File not Found");
            return;
        }

        long offset, length;
        try {
            offset = Long.parseLong(p[2]);
            length = Long.parseLong(p[3]);
        } catch (NumberFormatException e) {
            reply(out, "ERROR 400 Offset/length must be numbers");
            return;
        }

        long size = Files.size(f);
        // เขียน offset > size - length แทน offset + length > size เพื่อกัน long overflow
        if (offset < 0 || length <= 0 || offset > size - length) {
            reply(out, "ERROR 416 Invalid range");
            return;
        }

        // header ต้อง flush ก่อนส่ง payload
        reply(out, "OK " + length);
        if (ch != null) {
            sendNio(f, offset, length, ch);
        }
        else{
            sendTraditional(f, offset, length, out);
        }
        out.flush();
    }

    /** Traditional I/O: RandomAccessFile.seek + read/write ผ่าน buffer */
    static void sendTraditional(Path f, long offset, long length, OutputStream out)throws IOException{
        try(var raf = new RandomAccessFile(f.toFile(), "r")){
            raf.seek(offset);
            byte[] buf = new byte[64 * 1024];
            long remaining = length;
            while (remaining > 0) {
                int n = raf.read(buf, 0, (int) Math.min(buf.length, remaining));

                if (n < 0) throw new EOFException("File shrank during transfer");
                out.write(buf, 0, n);
                remaining -= n;
            }
        }
    }

    /** NIO: FileChannel.transferTo -> SocketChannel*/
    static void sendNio(Path f, long offset, long length, SocketChannel ch)throws IOException{
        try(FileChannel fc = FileChannel.open(f, StandardOpenOption.READ)){
            long pos = offset, remaining = length;
            
            // transferTo อาจส่งได้น้อยกว่าที่ขอ ต้องวนลูป
            while (remaining > 0) {
                long n = fc.transferTo(pos, remaining, ch);

                if (n <= 0) throw new IOException("transferTo made no progress");
                pos += n;
                remaining -= n;
            }
        }
    }

    // ---------- helpers ----------
    /** คืน Path ของไฟล์ถ้าปลอดภัยและมีอยู่จริง ไม่งั้นคืน null (กัน path traversal) */
    static  Path resolve(String name){
        if (name.contains("/") || name.contains("\\") || name.contains("..")) {
            return null;
        }
        Path f = dir.resolve(name).normalize();
        return (f.startsWith(dir) && Files.isRegularFile(f)) ? f : null;
    }

    static void reply(OutputStream out, String msg)throws IOException{
        write(out, msg + "\n");
        out.flush();
    }

    static void write(OutputStream out, String s)throws IOException{
        out.write(s.getBytes(StandardCharsets.ISO_8859_1));
    }
}
