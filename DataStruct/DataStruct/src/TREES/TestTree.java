package TREES;

import java.util.Scanner;

public class TestTree {
        public static void printPreOrder(Tree tree){
        if(tree == null){
            System.out.print("-");
            return;
        }
        System.out.print(tree.getInfo());
        System.out.print("[");
        printPreOrder(tree.getLeft());
        printPreOrder(tree.getRight());
        System.out.print("]");
    }
    public static void printPostOrder(Tree tree){
        if(tree == null){
            System.out.print("-"); 
            return;
        } 
        System.out.print("[");
        printPostOrder(tree.getLeft());
        printPostOrder(tree.getRight());
        System.out.print("]");
        System.out.print(tree.getInfo());
    }
    public static void printInorder(Tree tree){
        if(tree == null) return;
        System.out.print("[");
        printInorder(tree.getLeft());
        System.out.print(tree.getInfo());
        printInorder(tree.getRight());
        System.out.print("]");
    }
    public static int countNode(Tree tree){
        if(tree == null) return 0;
        int count = 1;
        count += countNode(tree.getLeft());
        count += countNode(tree.getRight());
        return count;
    }
    public static int totalInfo(Tree tree){
        if(tree == null) return 0;
        Integer val_info = (Integer)tree.getInfo();
        return val_info + totalInfo(tree.getLeft()) + totalInfo(tree.getRight());
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Tree tree = null, left = null, right = null;
        int[] number = new int[n];
        for (int i = 0; i < n; i++) {   
            number[i] = sc.nextInt();
        }
        for (int i = 0; i < number.length; i++) {
            int front = number[0];
            tree = new Tree(front);
            if(number[i] < front){
                left = new Tree(number[i]);
                tree.setLeft(left);
            }
            else {
                right = new Tree(number[i]);
                tree.setRight(right);
            }
            printInorder(tree.getLeft());
        }
        sc.close();
    }
    /*
    Input
    8 2 1 6 8 4 7 7

    8
    11 5 4 9 7 10 15 25
    
    Output
    root = 8
    2 1 6 8 4 7 7 

    root = 8 2 
    l = 2

    root 8 1
    l = 2 1

    6 8 4 7 7
    root = 8 6
    l = 2 1 6

    8 4 7 7 
    root = 8 8
      l = 2 1 6 
      r = 8

    4 7 7 
    root = 8 4
    l = 2 1 6 4
    r = 8


    7 7 
    root = 8 7
    l = 2 1 6 4 7
    r = 8 


    7 
    root = 8  7
    l = 2 1 6 4 7 7 
    r = 8
    */
}
