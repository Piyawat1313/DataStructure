import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Stack stack = new Stack();
            int number = sc.nextInt();
            while (number != 0) {
                int b = number % 2;
                stack.push(b);
                if(stack.isFullStack()){
                    break;
                }
                else{
                    number = number / 2;
                }
            }
            while (!stack.isEmptyStack()) {
                System.out.print(stack.pop());
            }
        } catch (Exception e) {
            
        }
        sc.close();
    }
}
