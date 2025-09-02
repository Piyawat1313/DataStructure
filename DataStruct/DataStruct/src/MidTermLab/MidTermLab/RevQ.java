package Midterm.MidTermLab;
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
        try {
            do {
                data = keyboard.nextInt();
                if (data >= 0)
                    q.enQueue(data);
            } while (data >= 0);
        } catch (Exception e) {
        }
    }

    public static void showQ(Queue q) {
        // TO DO
        try {
            while (!q.isEmptyQueue()) {
                System.out.print(q.front() + " | ");
                q.deQueue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void reverseOddQ(Queue q) {
        // TO DO
        
        //แยกข้อมูล
        Stack oddStack = new Stack();
        Queue even = new Queue();
        try {
            while (!q.isEmptyQueue()) {
                int number = (int) q.front();
                q.deQueue();
                if (number % 2 != 0) {
                    oddStack.push(number);
                    even.enQueue("Odd");   //จำตำแหน่งเดิมของเลขคี่
                } else {
                    even.enQueue(number);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //ใส่เลขคี่ที่สลับในStack
        try {
            while (!even.isEmptyQueue()) {
                Object item = even.front();
                even.deQueue();
                if (item.equals("Odd")) {
                    int score = (int) oddStack.pop();   //ใส่คืนตำแหน่งเดิม
                    q.enQueue(score);
                } else {
                    q.enQueue(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Queue q = new Queue();
        inputQ(q);
        reverseOddQ(q);
        showQ(q);
    }
}
