
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
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HexFormat;

public class Server {
    public static Path dir;
    public static void main(String[] args)throws Exception {
        int port = Integer.parseInt(args[0]);
        boolean nio = args[1].equalsIgnoreCase("nio");
        dir = Paths.get(args.length > 2 ? args[2] : "files").toAbsolutePath().normalize();
        
        System.out.println("Serving " + dir + " on port " + port + " mode=" + (nio ? "NIO" : "Traditional"));

        // Virtual thread ต่อ 1 connection ทำให้รองรับหลาย client พร้อมกัน และเขียนโค้ด blocking ตรง ๆ ได้
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
                        case "HASH" -> hash(p, out);
                        default -> reply(out, "ERROR 400 Unknown command");
                    }
                }
        }catch(Exception e){
            // client ตัดการเชื่อมต่อกลางทาง
            System.err.println("handle() error: " + e);
        }
    }

    static void list(OutputStream out)throws IOException{

        // Files.list(dir) กรองเฉพาะไฟล์ปกติ
        try(Stream<Path> s = Files.list(dir)){

            // เขียน FILE <ชื่อ> <ขนาด> ทีละบรรทัด
            for (Path f : (Iterable<Path>) s.filter(Files::isRegularFile)::iterator) 
                write(out, "FILE " + f.getFileName() + " " + Files.size(f) + "\n"); 
        }
        // ปิดด้วย END เพื่อให้ client รู้ว่าจบ
        reply(out, "END");
    }

    static void info(String[] p, OutputStream out)throws IOException{
        // เช็กว่ามี argument ครบ 2 ตัว
        if (p.length != 2) {
            reply(out, "ERROR 400 Usage: INFO <filename>");
            return;
        }
        // resolve() หาไฟล์
        Path f = resolve(p[1]);

        // ถ้าไม่เจอตอบ ERROR 404
        if (f == null) {
            reply(out, "ERROR 404 File not found"); return;
        }

        // ถ้าเจอตอบ SIZE <bytes>
        reply(out, "SIZE " + Files.size(f));
    }

    static void hash(String[]p, OutputStream out) throws IOException {
        if (p.length != 2) {
            reply(out, "ERROR 400 Usage: HASH <filename>");
            return;
        }

        Path f = resolve(p[1]);
        if (f == null) {
            reply(out, "ERROR 404 File not found");
            return;
        }

        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            try(InputStream in = Files.newInputStream(f)){
                byte[] buf = new byte[1 << 20];
                int n;
                while ((n = in.read(buf)) > 0) {
                    md.update(buf, 0, n);
                }
            }
            reply(out, "SHA256 " + HexFormat.of().formatHex(md.digest()));
        }catch(java.security.NoSuchAlgorithmException e){
            reply(out, "ERROR 500 Hash unavailable");
        }
    }

    static void get(String[] p, OutputStream out, SocketChannel ch)throws IOException{
        
        // เช็กว่ามี argument ครบ 4 ตัว
        if (p.length != 4) {
            reply(out, "ERROR 400 Usage: GET <filename> <offset> <length>"); 
            return;
        }

        // resolve ชื่อไฟล์
        Path f = resolve(p[1]);

        // ถ้าไม่เจอ 404 
        if (f == null) {
            reply(out, "ERROR 404 File not Found");
            return;
        }
        
        long offset, length;

        // แปลง offset/length เป็น long
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
        // ส่ง header OK <length> และ flush ทันที
        reply(out, "OK " + length);

        // ส่ง payload ตามโหมด
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

        // เปิด RandomAccessFile โหมดอ่าน seek(offset)
        try(var raf = new RandomAccessFile(f.toFile(), "r")){
            raf.seek(offset);
            byte[] buf = new byte[64 * 1024];
            long remaining = length;

            // วนอ่านเข้า buffer 64KB เขียนลง out จนครบ length
            while (remaining > 0) {
                int n = raf.read(buf, 0, (int) Math.min(buf.length, remaining));

                // ถ้า read คืน -1 ก่อนครบแปลว่าไฟล์หดระหว่างส่ง จึงโยน EOFException
                if (n < 0) throw new EOFException("File shrank during transfer");

                // เส้นทางข้อมูล: disk → page cache → buffer ใน user space → buffer ของ socket
                out.write(buf, 0, n);
                remaining -= n;
            }
        }
    }

    /** NIO: FileChannel.transferTo -> SocketChannel*/
    static void sendNio(Path f, long offset, long length, SocketChannel ch)throws IOException{

        // เปิด FileChannel แล้วเรียก transferTo(pos, remaining, ch) ให้ kernel ส่งตรงจาก page cache ไป socket
        try(FileChannel fc = FileChannel.open(f, StandardOpenOption.READ)){
            long pos = offset, remaining = length;
            
            // transferTo อาจส่งได้น้อยกว่าที่ขอ ต้องวนลูป
            while (remaining > 0) {
                long n = fc.transferTo(pos, remaining, ch);

                // ถ้า n <= 0 ถือว่าไม่คืบหน้าให้โยน error กันลูปไม่รู้จบ
                if (n <= 0) throw new IOException("transferTo made no progress");
                pos += n;
                remaining -= n;
            }
        }
    }

    // ---------- helpers ----------
    /** คืน Path ของไฟล์ถ้าปลอดภัยและมีอยู่จริง ไม่งั้นคืน null (กัน path traversal) */
    static  Path resolve(String name){

        // ปฏิเสธชื่อที่มี /, \, ..
        if (name.contains("/") || name.contains("\\") || name.contains("..")) {
            return null;
        }

        // dir.resolve(name).normalize() และเช็ก startsWith(dir) อีกชั้น รวมถึงต้องเป็นไฟล์ปกติ ถ้าไม่ผ่านคืน null
        Path f = dir.resolve(name).normalize();
        return (f.startsWith(dir) && Files.isRegularFile(f)) ? f : null;
    }

    // reply เขียนข้อความ + \n แล้ว flush ทันที
    static void reply(OutputStream out, String msg)throws IOException{
        write(out, msg + "\n");
        out.flush();
    }

    // write แปลง String เป็น byte ด้วย ISO_8859_1 (1 char = 1 byte)
    static void write(OutputStream out, String s)throws IOException{
        out.write(s.getBytes(StandardCharsets.ISO_8859_1));
    }
}
