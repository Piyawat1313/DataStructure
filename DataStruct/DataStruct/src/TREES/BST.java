package TREES;

import java.util.Random;

class Node {
    int number;
    Node left;
    Node right;

    public Node(int data) {
        this.number = data;
        this.left = null;
        this.right = null;
    }
}

class NodeRoot {
    Node root;

    public NodeRoot() {
        this.root = null;
    }

    public void insertNode(int number) {    //วิธีการgetค่าออกไป
        root = insert(root, number);
    }

    private Node insert(Node root, int data) {
        //break case 
        if (root == null) {     //หา root 
            return new Node(data);  //เอาเข้า root
        }
        if (data <= root.number) {      //หาทางซ้าย
            root.left = insert(root.left, data);
        } else {        //หาทางขวา
            root.right = insert(root.right, data);
        }
        return root;
    }

    public void printPreOrderTree() {
        printPreOrder(root);
    }

    public void printPreOrder(Node tree) {
        //break case
        if (tree == null) {
            System.out.print("-");
            return;
        }
        System.out.print(tree.number);  //root
        System.out.print("[");
        printPreOrder(tree.left);   
        printPreOrder(tree.right);
        System.out.print("]");
    }

    public int getHeightTree() {
        return getHeight(root);
    }

    public int getHeight(Node newNode) {
        if (newNode == null) {  //ถ้าไม่มีราก
            return 0;
        }
        int count = 1;
        count += getHeight(newNode.left);   //นับข้อมูลทางซ้าย
        count += getHeight(newNode.right);     //นับข้อมูลทางขวา
        return count;
    }

    public boolean search(int data) {
        StringBuilder path = new StringBuilder();   //เอาไว้เก็บเส้นทางว่าไปทางซ้ายหรือขวา
        boolean found = searchData(root, data, path);   //เก็บค่าที่ return ออกมาใน method
        if(found){
            System.out.println(data + ": (" + path.toString() + ") Found!");
        }
        else{
            System.out.println(data + ": (" + path.toString() + ") Not Found!");
        }
        return found;
    }
    private boolean searchData(Node root, int value, StringBuilder path){
        if(root == null){   //ถ้าไม่มีราก 
            return false;
        }
        if(value == root.number){   //ถ้าค่าที่สุ่มมาเท่ากับราก
            return true;
        }
        // if สองตัวบนคือ break case
        if(value < root.number){    //ถ้าเลขที่สุ่มมาน้อยกว่าราก
            path.append("L");   //เส้นทางซ้าย
            return searchData(root.left, value, path);  //return ค่าตัวเอง
        }
        else{
            path.append("R");   //เส้นทางขวา
            return searchData(root.right, value, path); //return ค่าตัวเอง
        }
    } 
}

public class BST {

    public static void main(String[] args) {
        Random random = new Random();
        NodeRoot nrt = new NodeRoot();
        int n = random.nextInt(11) + 10;
        System.out.println("--- Random[" + n + "] ---");
        int number[] = new int[n];
        for (int i = 0; i < number.length; i++) {
            number[i] = random.nextInt(51);
            System.out.print(number[i] + " ");
            nrt.insertNode(number[i]);
        }
        System.out.println();
        System.out.println("--- Tree (Height=" + nrt.getHeightTree() + ") ---");
        nrt.printPreOrderTree();
        System.out.println();
        System.out.println("--- Search ---");
        boolean found = false;
        //วิธีการ search
        while (!found) {
            int value = random.nextInt(51);
            found = nrt.search(value);
        }
    }
}
