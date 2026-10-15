import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Demo {
    static final String HOST = "localhost";
    static final int PORT = 3000;

    // อ่านทีละ byte จนเจอ \n (ห้ามใช้ BufferedReader เพราะจะกลืน payload)
    static String readLine(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int b;
        while ((b = in.read()) != -1 && b != '\n') {
            sb.append((char) b);
        }
        return sb.toString().trim();
    }

    // ส่งคำสั่ง 1 ครั้ง ต่อ 1 connection
    static Socket send(String cmd) throws IOException {
        Socket s = new Socket(HOST, PORT);
        s.getOutputStream().write((cmd + "\n").getBytes(StandardCharsets.ISO_8859_1));
        s.getOutputStream().flush();
        return s;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java Demo <LIST|INFO|GET|HASH> [filename]");
            return;
        }

        String cmd = args[0].toUpperCase();
        String name = args.length > 1 ? args[1] : null;

        if (!cmd.equals("LIST") && name == null) {
            System.out.println("ต้องระบุชื่อไฟล์ Java Demo " + cmd + " 1g.test");
            return;
        }

        switch (cmd) {
            case "LIST" -> {
                try (Socket s = send("LIST")) {
                    InputStream in = s.getInputStream();
                    String line;
                    while (!(line = readLine(in)).equals("END")) {
                        System.out.println(line);
                    }
                }
            }

            case "INFO" -> {
                try (Socket s = send("INFO " + name)) {
                    System.out.println(readLine(s.getInputStream()));
                }
            }

            case "GET" -> {
                // ขอขนาด
                long size;
                try(Socket s = send("INFO "+ name)){
                    String line = readLine(s.getInputStream());

                    if (!line.startsWith("SIZE ")) {
                        throw new IOException(line);
                    }

                    size = Long.parseLong(line.substring(5).trim());
                }

                // แบ่งช่วง
                int workers = Integer.parseInt(args[2]);
                long chunk = size / workers;

                for (int i = 0; i < workers; i++) {
                    long off = i  * chunk;

                    // ตัวสุดท้ายรับเศษ
                    long len = (i == workers - 1) ? size - off : chunk;
                    System.out.printf("worker %d: offset=%d length=%d%n", i, off, len);
                }
            }

            case "HASH" -> {
                try (Socket s = send("HASH " + name)) {
                    String line = readLine(s.getInputStream());
                    if (line.startsWith("SHA256 ")) {
                        System.out.println(line.substring(7));
                    } else {
                        System.out.println("error: " + line);
                    }
                }
            }

            default -> System.out.println("Unknown command: " + cmd);
        }
    }
}
