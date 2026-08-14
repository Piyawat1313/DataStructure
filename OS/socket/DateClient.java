package OS.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DateClient {
    public static void main(String[] args) {
        try(Socket socket = new Socket("127.0.0.1", 6013); //เชื่อมต่อ server ที่เครื่องตัวเองตามด้วย port ของ server

        // รับ byte stream จากนั้น BufferReader แปลง stream ทีละบรรทัด
            BufferedReader in = new BufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())))){
            
                String line;

                // รอข้อมูลหรือ EOF
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
