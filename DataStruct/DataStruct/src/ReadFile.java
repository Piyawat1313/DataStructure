import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Challeng {
    public static void main(String[] args) {
        try {
            File ob = new File("C:\\Users\\Guest-Student\\Desktop\\0136\\0136\\src\\regdoc.txt");  //path download File
            Scanner Myred = new Scanner(ob);    //สร้าง object Scanner และยัดFileลงObjectนั้น
            while (Myred.hasNextLine()) {    //อ่านค่าที่มีใน Object นั้น
                String data = Myred.nextLine();    //สร้าง String อ่านค่า File
                System.out.println(data);    //print out put in file
            }
            
        } catch (Exception e) {    //ดัก Exeption
            System.out.println(e.getMessage());
        }
    }
}
