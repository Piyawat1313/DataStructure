public class rotateImage {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        // Tranpose metrix
        for (int i = 0; i < n; i++) {
            for (int j =  i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // reverse แต่ละแถว สลับจากหน้าไปหลัง
        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = n - 1;
            while (left < right) {
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;

                left++; //ขยับเข้าหาตรงกลาง
                right--;    //ขยับเข้าหาตรงกลาง
            }
        }
    }

    public static void main(String[] args) {
        rotateImage r = new rotateImage();
        r.rotate(new int[][]{
            {1,2,3},
            {4,5,6},
            {7,8,9}
        });


        r.rotate(new int[][]{
            {5,1,9,11},
            {2,4,8,10},
            {13,3,6,7},
            {15,14,12,16}
        });
    }
}
