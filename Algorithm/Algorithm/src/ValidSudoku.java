import java.util.HashSet;

public class ValidSudoku {
    
    public boolean isValidSudoku(char[][] board) {
        HashSet<String> seen = new HashSet<>(); //ใช้เก็บตัวที่ยังไม่เคยเจอ

        // วนลู)ทุกช่องในตาราง 9 * 9
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char number = board[i][j];

                // ถ้าเป็นช่องว่าง . ให้ข้ามไปเลยไม่ต้องตรวจ
                if(number != '.'){

                    int boxIndex = (i / 3) * 3 + (j / 3);   //คำนวณหาตำแหน่งกล่องย่อย (0 - 8)

                    // ระบุตำแหน่งเลขนั้นๆ
                    String rowKey = number + " in row " + i;
                    String colKey = number + " in col " + j;
                    String boxKey = number + " in box " + boxIndex;

                    // เพิ่มเขาไปใน Set ถ้ามีอันใดอันนึงซ้ำ จะคืนค่า false ทันที
                    if(!seen.add(rowKey) || !seen.add(colKey) || !seen.add(boxKey)){
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public boolean isSodu(char[][] board){
        return checkRows(board) && checkColumns(board) && checkSubBoxes(board);
    }

    private boolean checkRows(char[][] board){
        for (int i = 0; i < board.length; i++) {
            boolean[] numRows = new boolean[9];
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] != '.'){
                    if(numRows[board[i][j] - '1']){
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private boolean checkColumns(char[][] board){
        for (int i = 0; i < board.length; i++) {
            boolean[] numsCol = new boolean[9];
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] != '.'){
                    if(numsCol[board[i][j] - '1']){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // เอาไว้เช็คกล่องย่อยขนาด 3 X 3 ทั้ง 9 กล่อง
    private boolean checkSubBoxes(char[][] board){
        if(checkSubBox(board, 0, 0) 
            && checkSubBox(board, 0, 3) 
            && checkSubBox(board, 0, 6)
            && checkSubBox(board, 3, 0)
            && checkSubBox(board, 3, 3)
            && checkSubBox(board, 3, 6)
            && checkSubBox(board, 6, 0)
            && checkSubBox(board, 6, 3)
            && checkSubBox(board, 6, 6)){
            return true;
        }
        return false;
    }

    // เช็คกล่องย่อยทั้ง 9 กล่อง
    private boolean checkSubBox(char[][] board, int rowStart, int colStart){
        boolean[] numInsubBox = new boolean[9];

        for(int row = rowStart; row < rowStart + 3; row++){
            for(int col = colStart; col < colStart + 3; col++){
                if(board[row][col] != '.'){
                    if(numInsubBox[board[row][col] - '1']){
                        return false;
                    }
                    else{
                        numInsubBox[board[row][col] - '1'] = true;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        
    }
}
