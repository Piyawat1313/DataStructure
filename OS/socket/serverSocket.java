package OS.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class serverSocket {
    public static void main(String[] args) {
        // server open port 6013
        try (ServerSocket server = new ServerSocket(6013)){
            while (true) {
                Socket client = server.accept();    //รอ client เชื่อมต่อ
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);  //ส่งข้อความผ่าน output stream
                out.println(new java.util.Date());
                client.close(); //ปิด client รอการเชื่อมต่อถัดไป
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
