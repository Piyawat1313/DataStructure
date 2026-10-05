import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.*;

public class Client {

    // ให้ทุก method และทุก worker ใช้ร่วมกันได้ โดยไม่ต้องส่งผ่านพารามิเตอร์
    static String host, name;
    static int port;
    static boolean nio;
    static Path outPath;

    public static void main(String[] args)throws Exception {

        // อ่าน argument: host, port, ชื่อไฟล์, จำนวน worker, โหมด, โฟลเดอร์ปลายทาง
        host = args[0];
        port = Integer.parseInt(args[1]);
        name = args[2];
        int workers = Integer.parseInt(args[3]);
        nio = args[4].equalsIgnoreCase("nio");
        Path outDir = Paths.get(args.length > 5 ? args[5] : "downloads");
        Files.createDirectories(outDir);

        // กำหนดปลายทาง: finalPath คือชื่อจริง กัน path traversal
        Path finalPath = outDir.resolve(Paths.get(name).getFileName());

        // outPath คือไฟล์ .part ที่ใช้เขียนระหว่างโหลด
        outPath = finalPath.resolveSibling(finalPath.getFileName() + ".part");

        // ขอรายชื่อไฟล์ + ขนาดไฟล์
        list();
        long size = getSize();

        if (size <= 0) throw new IOException("File is empty or size unknow");

        // กันกรณีไฟล์เล็กกว่าจำนวน worker
        workers = (int) Math.min(workers, size); 


        // สร้างไฟล์ปลายทางขนาดเต็มไว้ก่อน
        try(var raf = new RandomAccessFile(outPath.toFile(), "rw")){

            // จองไฟล์เต็มขนาดไว้ก่อน เพื่อให้ทุก worker เขียนที่ offset ใดก็ได้
            raf.setLength(size);
        }

        // คำนวณ byte range ของแต่ละ workerไม่ซ้อนกัน
        long chunk = size / workers;
        List<Callable<Long>> tasks = new ArrayList<>();

        // worker ที่ i เริ่มที่ i * chunk และ worker สุดท้ายรับเศษที่เหลือ (size - off) ทำให้ช่วงต่อกันพอดีและไม่ซ้อน
        for (int i = 0; i < workers; i++) {
            long off = i * chunk;

            // worker สุดท้ายรับเศษที่เหลือ
            long len = (i == workers - 1) ? size - off : chunk;

            System.out.printf("worker %d: offset=%d length=%d%n", i, off, len);
            tasks.add(() -> download(off, len));
        }


        // รันทุก worker พร้อมกัน แล้วจับเวลา
        ExecutorService pool = Executors.newFixedThreadPool((workers));
        long t0 = System.nanoTime();
        long total = 0, t1;
        try{
            // สั่งทุก task พร้อมกันและรอจนเสร็จทั้งหมด จับเวลา t0→t1 แล้วรวมผลด้วย f.get()
            for (Future<Long> f : pool.invokeAll(tasks)) {
                total += f.get();
            }
            t1 = System.nanoTime();

        }finally{
            pool.shutdown();
            
        }
        

        // ตรวจ size และ hash
        boolean sizeOk = total == size && Files.size(outPath) == size;
        String hash = sha256(outPath);
        boolean hashOk = hash.equalsIgnoreCase(getRemoteHash());
        double sec = (t1 - t0) / 1e9;
        double mb = size / 1024.0 / 1024.0;
        System.out.println(String.format(Locale.ROOT, "RESULT mode=%s workers=%d  time=%.3fs throughput=%.2fMB/s size_ok=%b  hash_ok=%b sha256=%s", nio ? "NIO" : "Traditional", workers, sec, mb / sec, sizeOk, hashOk, hash));

        if (sizeOk && hashOk) {
            Files.move(outPath, finalPath, StandardCopyOption.REPLACE_EXISTING);
        }
        else{
            System.err.println("Verification failed, kept " + outPath);
            System.exit(1);
        }
    }

    // เปิด connection ชั่วคราว ส่งคำสั่งหนึ่งครั้ง อ่านผลด้วย readLine
    // list วนอ่านและพิมพ์จนเจอ END
    static void list() throws IOException{
        try(Socket s = new Socket(host, port)){
            s.getOutputStream().write("LIST\n".getBytes(StandardCharsets.ISO_8859_1));
            InputStream in = s.getInputStream();
            String line;
            System.out.println("Files on server:");
           
            while (!(line = readLine(in)).equals("END")) {
                System.out.println(" " + line);
            }
        }
    }

    // เปิด connection ชั่วคราว ส่งคำสั่งหนึ่งครั้ง อ่านผลด้วย readLine
    // getSize คาดหวังบรรทัด SIZE <n> ถ้าไม่ใช่โยน IOException พร้อมข้อความจาก server
    static long getSize() throws IOException{
        try(Socket s = new Socket(host, port)){
            s.getOutputStream().write(("INFO "+ name + "\n").getBytes(StandardCharsets.ISO_8859_1));
            String line = readLine(s.getInputStream());

            if(line.startsWith("SIZE ")) return  Long.parseLong(line.substring(5).trim());
            throw new IOException("Server replied: "+ line);
        }
    }

    static String getRemoteHash() throws IOException{
        try(Socket s = new Socket(host, port)){
            s.getOutputStream().write(("HASH " + name + "\n").getBytes(StandardCharsets.ISO_8859_1));

            String line = readLine(s.getInputStream());

            if(line.startsWith("SHA256")) return  line.substring(7).trim();
            throw new IOException("Server replied: " + line);
        }
    }

    // งานของ 1 worker
    static long download(long off, long len)throws IOException{
        // สร้างคำสั่ง
        String req = "GET " + name + " " + off + " " + len + "\n";

        // แต่ละ worker เปิด FileChannel ของตัวเอง และเขียนด้วย positional write
        try(FileChannel fc = FileChannel.open(outPath, StandardOpenOption.WRITE)){
            return nio ? downloadNio(fc, req, off, len) : downloadTraditional(fc, req, off,len);
        }
    }

    /** Traditional: InputStream.read -> FileChannel.write(buffer, position) */
    static long downloadTraditional(FileChannel fc, String req, long off, long len)throws IOException {

        // เปิด Socket ของตัวเอง ส่ง request แล้ว flush อ่านและตรวจ header ด้วย expectOk
        try(Socket s = new Socket(host, port)){
            OutputStream out = s.getOutputStream();
            InputStream in = s.getInputStream();
            out.write(req.getBytes(StandardCharsets.ISO_8859_1));
            out.flush();
            expectOk(readLine(in), len);

            byte[] buf = new byte[64 * 1024];
            long pos = off, remaining = len;
            while (remaining > 0) {
                int n = in.read(buf, 0, (int) Math.min(buf.length,remaining));

                if(n < 0) throw new EOFException("Connection closed early, missing " + remaining + " bytes");
                ByteBuffer bb = ByteBuffer.wrap(buf,0,n);
                while (bb.hasRemaining()) pos += fc.write(bb, pos);
                remaining -= n; 
            }
        }
        return len;
    }

     /** NIO: SocketChannel -> FileChannel.transferFrom */
     static long downloadNio(FileChannel fc, String req, long off, long len)throws IOException{

        // เปิด SocketChannel ส่ง request ผ่าน ch.write อ่าน header ด้วย readLine(ch) แล้ววนเรียก fc.transferFrom(ch, pos, remaining) ให้ย้ายข้อมูลจาก socket ลงไฟล์โดยตรง
        try(SocketChannel ch = SocketChannel.open(new InetSocketAddress(host, port))){
            ByteBuffer rb = ByteBuffer.wrap(req.getBytes(StandardCharsets.ISO_8859_1));

            while (rb.hasRemaining()) ch.write(rb);
            expectOk(readLine(ch), len);

             // transferFrom อาจรับได้น้อยกว่าที่ขอ ต้องวนลูป
            long pos = off, remaining = len;
            while (remaining > 0) {
                long n = fc.transferFrom(ch, pos, remaining);
                if(n <= 0) throw new EOFException("Connection closed early, missing " + remaining + " bytes");
                pos += n;
                remaining -= n;
            }
        }
        return len;
     }

     // ---------- helpers ----------
     static void expectOk(String header, long len)throws IOException{
        // ถ้า header ขึ้นต้นด้วย ERROR โยน exception ถ้ารูปแบบไม่ใช่ OK <len> ที่ len ตรงกับที่ขอก็โยน exception
        if(header.startsWith("ERROR")) throw new IOException("Server error: " + header);

        String[] p = header.split(" ");
        if(p.length != 2 || !p[0].equals("OK") || Long.parseLong(p[1]) != len) throw new IOException("Unexcepted header: " + header);
     }

      /** อ่านทีละ byte จนเจอ \n (ห้ามใช้ BufferedReader เพราะจะกลืน payload ไปด้วย) */
      static String readLine(InputStream in)throws IOException{
        StringBuilder sb = new StringBuilder();
        int b;
        while ((b = in.read()) != -1 && b != '\n') sb.append((char)b);

        if (b == -1 && sb.length() == 0) throw new EOFException("Connection closed while reading header");
       
        return sb.toString().trim();
      }

    //   SocketChannel อ่านทีละ 1 byte
    static String readLine(ReadableByteChannel ch)throws IOException {
        ByteBuffer one = ByteBuffer.allocate(1);
        StringBuilder sb = new StringBuilder();

        while (true) {
            one.clear();
            if(ch.read(one) < 0) throw new EOFException("Connection closed while reading header");

            char c = (char) (one.get(0) & 0xFF);
            if(c == '\n') break;
            sb.append(c);
        }

        return sb.toString().trim();
    }

    // อ่านไฟล์ทีละ 1MB อัปเดต MessageDigest แล้วแปลงเป็น hex
    static String sha256(Path f) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        try(InputStream in = Files.newInputStream(f)){
            byte[] buf = new byte[1 << 20];
            int n;
            while ((n = in.read(buf)) > 0) {
                md.update(buf, 0, n);
            }
        }
        return  HexFormat.of().formatHex(md.digest());
    }

   
}
