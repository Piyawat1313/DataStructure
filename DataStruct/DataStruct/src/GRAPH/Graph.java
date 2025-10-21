import java.util.Scanner;

public class Graph {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int table[][] = {
                { 0, 1, 0, 1 },
                { 0, 0, 0, 1 },
                { 1, 1, 0, 0 },
                { 0, 0, 1, 0 }
        };
        int path[][] = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    path[i][j] += table[i][k] * table[k][j];
                }
                
            }
        }
        System.out.println("result");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(path[i][j] + " ");
            }
            System.out.println();
        }
        System.out.print("Enter Source Vertex: ");
        char s1 = sc.next().charAt(0);
        System.out.print("Enter Target Vertex: ");
        char target = sc.next().charAt(0);
        System.out.println("From " + s1 + " to " + target);
        System.out.println("Path Length = 2: " + path[s1 - 'A'][target - 'A']);
        sc.close();
    }
}
