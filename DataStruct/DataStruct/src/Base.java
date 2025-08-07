import java.util.Scanner;

public class Base {
     public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Stack stack = new Stack();
            int digit = sc.nextInt();
            while (digit != 0) {
                int b = digit % 2;
                stack.push(b);      //เพิ่มเลขฐานเข้า Stack
                if(stack.isFullStack()){    //เช็คว่าเต็มมั้ย
                    break;      
                }
                else{   //ถ้าไม่เต็ม
                    digit = digit / 2;
                }
            }
            while (!stack.isEmptyStack()) {     //พิมจากหลังมาหน้า
                System.out.print(stack.pop());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        sc.close();
    }
}