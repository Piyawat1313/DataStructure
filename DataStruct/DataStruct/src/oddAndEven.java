import java.util.Random;
import java.util.Scanner;

public class oddAndEven {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Random random = new Random();
            int n = random.nextInt(11) + 10;    //random number
            System.out.println("--- Random Numbers [" + n + "] ---");   //size
            Stack evenStack = new Stack();      //Stack เลขคู่
            Stack Odd = new Stack();        //Satck เลขคี่
            for (int i = 0; i < n; i++) {
                int number = random.nextInt(101);   //สุ่มเลขตั้งแต่ 1 - 100
                System.out.print(number + " ");     //ปริ้นเลขที่สุ่มได้
                if(number % 2 == 0){
                    evenStack.push(number);
                }
                else{
                    Odd.push(number);
                }
            }
            System.out.println();
            System.out.println("--- Even Stack [" + evenStack.getSize() + "] ---");     //size Even
            while (!evenStack.isEmptyStack()) {     //ปริ้นข้อมูลที่อยู่ใน Stack เลขคู่
                System.out.print(evenStack.pop() + " ");    
            }
            System.out.println();
            System.out.println("--- Odd Stack [" + Odd.getSize() + "] ---");        //size Odd
            while (!Odd.isEmptyStack()) {       //ปริ้นข้อมุลที่อยู่ใน Stack เลขคี่
                System.out.print(Odd.pop() + " ");
            }

            while (!evenStack.isEmptyStack() && !Odd.isEmptyStack()) {      //เช็คค่าบนสุดใน Stack 
                int evenTop = (Integer)evenStack.peek();
                int OddTop = (Integer)Odd.peek();
                if(evenTop > OddTop){
                   evenStack.pop();
                }
                else if(OddTop > evenTop){
                    Odd.pop();
                }
            }
            System.out.println();
            if(evenStack.isEmptyStack()){
                System.out.println("===> Even is the Winner!");
            }
            else if(Odd.isEmptyStack()){
                System.out.println("===> Odd is the Winner!");
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}
