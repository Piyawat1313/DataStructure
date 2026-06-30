import java.util.ArrayList;
import java.util.List;

public class spiralMatrix {

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {
            // เดินจากซ้ายไปขวา บนขอบ top
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            top++;

            // เดินจาก บนลง ล่าง บนขอบ right
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;

            // เดินจากขวาไปซ้าย บนขอบล่าง
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    result.add(matrix[bottom][i]);
                }
                bottom--;
            }

            // เดินจากล่างขึ้นบน บนขอบ left 
            if(left <= right){
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        spiralMatrix m = new spiralMatrix();
        System.out.println(m.spiralOrder(new int[][]{
            {1,2,3},
            {4,5,6},
            {7,8,9}
        }));

        System.out.println(m.spiralOrder(new int[][]{
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12}
        }));
    }
}
