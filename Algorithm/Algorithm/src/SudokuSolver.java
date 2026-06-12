
public class SudokuSolver {
    public void solveSudoku(char[][] board) {
        solve(board);
    }

    private boolean solve(char[][] board){
        // เช็คกล่องขนาด 9 X 9
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // เจอช่องว่างแทนด้วย .
                if(board[i][j] == '.'){

                    // ลองเดาเลขตั้งแต่ 1 - 9
                    for(char num = '1'; num <= '9'; num++ ){
                        // ถ้ายังไม่มีตัวเลขซ้ำกันเลยให้ลองใส่เลขเขาไป
                        if(isValid(board, i, j, num)){
                            board[i][j] = num;

                            // ลุยต่อในช่องถัดไป ถ้าสำเร็จจนจบตารางให้ส่งกลับต่า True
                            if(solve(board)){
                                return true;
                            }
                            board[i][j] = '.';  //ถ้าไปต่อไม่ได้ให้เป็น . 
                        }
                    }
                    return false;   //ถ้าลองครบ 1 - 9 แล้วยังไม่ได้แสดงว่าทางที่เดินมาผิดชัว
                }
            }
        }
        return true;    //เติมตัวเลขให้ครบทุกช่อง
    }

    private boolean isValid(char[][] board, int row, int col, char num){
        // คำนวณกล่องย่อย 3 * 3
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;

        for(int i = 0; i < 9; i++){
            // ตรวจสอบแนวนอน
            if(board[row][i] == num){
                return false;
            }

            // ตรวจสอบแนวตั้ง
            if(board[i][col] == num){
                return false;
            }

            // ตรวจสอบกล่องย่อยขนาด 3 * 3
            if(board[boxRowStart + (i / 3)][boxColStart + (i % 3)] == num){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SudokuSolver s = new SudokuSolver();
        s.solveSudoku(new char[][]{{}});
    }
}
