public class SpiralMatrixII {
    public int[][] generateMatrix(int n) {
        int top = 0;
        int bottom = n - 1;
        int left = 0;
        int right = n - 1;
        int[][] result = new int[n][n];
        int number = 1;
        while (top <= bottom && left <= right && number <= (n * n)) {
            // เดินแถวบน
            for (int i = left; i <= right; i++) {
                result[top][i] =  number;
                number++;
            }
            top++;

            // เดินจาก บน --> ล่าง วิ่งบน column ขวา
            for (int i = top; i <= bottom; i++) {
                result[i][right] = number;
                number++;
            }
            right--;

            // เดินจากขวา ไป ซ้าย วิ่งบนแถว bottom
            for (int i = right; i >= left; i--) {
                result[bottom][i] = number;
                number++;
            }
            bottom--;

            // เดินจากล่าง ขึ้น บน วิ่งบน column left 
            for (int i = bottom; i >= top; i--) {
                result[i][left] = number;
                number++;
            }
            left++;
        }
        return result;
    }

    public static void main(String[] args) {
        SpiralMatrixII m = new SpiralMatrixII();
        int[][] mm = m.generateMatrix(3);
        int[][] mmm = m.generateMatrix(1);

        for (int i = 0; i < mm.length; i++) {
            for (int j = 0; j < mm.length; j++) {
                System.out.print(mm[i][j] + " ");
            }
            System.out.println();
        }

         for (int i = 0; i < mmm.length; i++) {
            for (int j = 0; j < mmm.length; j++) {
                System.out.print(mmm[i][j] + " ");
            }
            System.out.println();
        }
    }
}
