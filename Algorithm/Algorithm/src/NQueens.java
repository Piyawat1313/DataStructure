import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NQueens {
    private List<List<String>> result = new ArrayList<>();
    private Set<Integer> cols = new HashSet<>();
    private Set<Integer> diag1 = new HashSet<>(); //เฉียงขวาบนลงซ้ายล่าง สูตร Row + col
    private Set<Integer> diag2 = new HashSet<>();   //เฉียงว้ายบนมาขวาล่าง สูตร Row - col

    // ใช้แนวคิด backtracking
    public List<List<String>> solveNQueens(int n) {

        // สร้างกระดานโดยที่กระดานเป็น .
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }    
        // เริ่มต้นวางควีนจากแถวที่ 0 
        backtrack(0, n, board);
        return result;
    }
    // ฟังก์ชั่นย้อนรอย
    private void backtrack(int row, int n, char[][] board){
        // base case ถ้าว่างได้ครบทุกตัวแล้ว
        if(row == n){
            result.add(construct(board));
            return;
        }
        // ลองวางใหม่ในแต่ละคอลัมน์ของปัจจุบัน
        for(int col = 0; col < n; col++){
            int d1 = row + col;
            int d2 = row - col;
            // ถ้าช่องนี้ไม่ปลอดภัย ให้ข้ามไป
            if(cols.contains(col) || diag1.contains(d1) || diag2.contains(d2)){
                continue;
            }
            // เลือกวางควีนตรงนี้
            board[row][col] = 'Q';
            cols.add(col);
            diag1.add(d1);
            diag2.add(d2);
            // เดินหน้าไปวางแถวถัดไป
            backtrack(row + 1, n, board);

            // ย้อนรอย ดึงควีนออกเพื่อลองตำแหน่งอื่นในลูปถัดไป
            board[row][col] = '.';
            cols.remove(col);
            diag1.remove(d1);
            diag2.remove(d2);
        }
    }
    // ฟังก์ชั่นช่วยแปลง char[][] บนกระดานให้เป็น List<String>ตามที่โจทย์ต้องส่งคืน
    private List<String> construct(char[][] board){
        List<String> res = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            res.add(new String(board[i]));
        }

        return res;
    }

    public static void main(String[] args) {
        NQueens n = new NQueens();
        System.out.println(n.solveNQueens(1));
    }
}
