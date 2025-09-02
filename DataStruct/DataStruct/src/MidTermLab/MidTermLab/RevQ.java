/*
NO: xx
ID: xxxxxxxxx
GROUP: x
CODE: xxxx
SEAT: xx
IP: N.N.N.N
*/

import java.util.Scanner;

public class RevQ {

    public static void inputQ(Queue q) {
        Scanner keyboard = new Scanner(System.in);
        int data;
        try{
            do {
                data = keyboard.nextInt();
                if(data>=0)
                    q.enQueue(data);
            } while(data>=0);
        } catch(Exception e) {}
    } 

    public static void showQ(Queue q) {
		// TO DO
    }

    public static void reverseOddQ(Queue q) {
		// TO DO
        if(data )
    }
    
    public static void main(String[] args) {
        Queue q=new Queue();
        inputQ(q);
        reverseOddQ(q);
        showQ(q);
    }
}
