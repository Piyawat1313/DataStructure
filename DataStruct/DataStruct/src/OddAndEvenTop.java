import java.util.Random;
import java.util.Scanner;

public class oddAndEven {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Random random = new Random();
            int n = random.nextInt(11) + 10;    //เลขสุ่มตั้งแต่ 10 - 20 
            System.out.println("--- Random Numbers [" + n + "] ---");
            Stack evenStack = new Stack();    //สร้าง Stack คู่
            Stack Odd = new Stack();  //สร้าง Stack คี่
            for (int i = 0; i < n; i++) {
                int number = random.nextInt(101);  //สุ่มเลข 0 - 100
                System.out.print(number + " ");
                if(number % 2 == 0){    //ถ้าเป็นเลขคู่
                    evenStack.push(number);  //เข้า Stack เลขคู่
                }
                else{    //ถ้าเป็นคี่
                    Odd.push(number);  //เข้า stack เลขคี่
                }
            }
            System.out.println();
            System.out.println("--- Even Stack [" + evenStack.getSize() + "] ---");
            while (!evenStack.isEmptyStack()) {    //ปริ้น Stack เลขคู่
                System.out.print(evenStack.pop() + " ");
            }
            System.out.println();
            System.out.println("--- Odd Stack [" + Odd.getSize() + "] ---");
            while (!Odd.isEmptyStack()) {    //ปริ้น Stack เลขคี่
                System.out.print(Odd.pop() + " ");
            }

            while (!evenStack.isEmptyStack() && !Odd.isEmptyStack()) {  //เช็คข้อมูลบนสุดของทั้งสอง Stack
                int evenTop = (Integer)evenStack.peek();  //ข้อมูลบนสุดของ Even
                int OddTop = (Integer)Odd.peek();    //ข้อมูลบนสุดของ Odd
                if(evenTop > OddTop){  //ถ้า Even win
                   evenStack.pop();    //ลบข้อมูลนั้น ออก Stack
                }
                else if(OddTop > evenTop){  //ถ้า Odd ชนะ
                    Odd.pop();  //ลบข้อมูลออกจาก Stack
                }
            }
            System.out.println();
            if(evenStack.isEmptyStack()){    //ถ้าข้อมูลเลขคู่ว่าง
                System.out.println("===> Even is the Winner!");
            }
            else if(Odd.isEmptyStack()){    //ถ้าข้อมูลเลขคี่ว่าง
                System.out.println("===> Odd is the Winner!");
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}
