import java.util.Random;
public class Algorithm {
    //Big-O(N^3)
    public static int maxSubsequenceSum(int A[], int n) {
        int thisSum;
        int MaxThissum;
        MaxThissum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                thisSum = 0;
                for (int j2 = i; j2 <= j; j2++) {
                    thisSum += A[j2];
                    if (thisSum > MaxThissum) {
                        MaxThissum = thisSum;
                    }
                }
            }
        }
        return MaxThissum;
    }
    //Big-O(N^2)
    public static int maxSubsequenceSum2(int A[], int n) {
        int thisSum;
        int MaxThissum;
        MaxThissum = 0;
        for (int i = 0; i < n; i++) {
            thisSum = 0;
            for (int j = i; j < n; j++) {
                thisSum += A[j];
                if (thisSum > MaxThissum) {
                    MaxThissum = thisSum;
                }
            }
        }
        return MaxThissum;
    }
    //Big-O(N log N)
    public static int Divide_Conquer(int A[], int left, int right) {
        int maxLeftSum, maxRightSum;
        int maxLeftBorderSum, maxRightBorderSum;
        int LeftBoder, RightBorder;
        int center;
        if (left == right) {
            if (A[left] > 0) {
                return A[left];
            } else {
                return 0;
            }
        }
        center = (left + right) / 2;
        maxLeftSum = Divide_Conquer(A, left, center);
        maxRightSum = Divide_Conquer(A, center + 1, right);
        maxLeftBorderSum = 0;
        LeftBoder = 0;
        for (int i = center; i >= left; i--) {
            LeftBoder += A[i];
            if (LeftBoder > maxLeftBorderSum) {
                maxLeftBorderSum = LeftBoder;
            }
        }
        maxRightBorderSum = 0;
        RightBorder = 0;
        for (int i = center + 1; i <= right; i++) {
            RightBorder += A[i];
            if (RightBorder > maxRightBorderSum) {
                maxRightBorderSum = RightBorder;
            }
        }
        return maxLeftBorderSum + maxRightBorderSum;
    }
    //Big-O(N)
    public static int algorithm4(int A[], int n) {
        int thisSum, maxsum;
        thisSum = maxsum = 0;
        for (int i = 0; i < n; i++) {
            thisSum += A[i];
            if (thisSum > maxsum) {
                maxsum = thisSum;
            }
            else if(thisSum < 0){
                thisSum = 0;
            }
        }
        return maxsum;
    }

    public static void TimeDivide_Conquer(int number[], int n) {
        //วิธีการแปลงค่าเป็น มิลลิวินาที
        long divide = System.nanoTime();
        Divide_Conquer(number, 0, n - 1);
        long endTime = System.nanoTime();
        long result_Divide = (endTime - divide);
        System.out.println("conquer = " + Divide_Conquer(number, 0, n - 1) + " : "
                + String.format("%f", result_Divide / 1_000_000.0));
    }

    public static void TimeMaxSub_Sequence_Sum1(int number[], int n) {
        //วิธีการแปลงค่าเป็น มิลลิวินาที
        long divide = System.nanoTime();
        Divide_Conquer(number, 0, n - 1);
        long endTime = System.nanoTime();
        long result_Divide = (endTime - divide);
        System.out.println(
                "maxsum1 = " + maxSubsequenceSum(number, n) + " : " + String.format("%f", result_Divide / 1_000_000.0));
    }

    public static void TimeMaxSub_Sequence_Sum2(int number[], int n) {
        //วิธีการแปลงค่าเป็น มิลลิวินาที
        long divide = System.nanoTime();
        maxSubsequenceSum2(number, n);
        long endTime = System.nanoTime();
        long result_Divide = (endTime - divide);
        System.out.println("maxsum2 = " + maxSubsequenceSum2(number, n) + " : "
                + String.format("%f", result_Divide / 1_000_000.0));
    }

    public static void Time_Algorithm4(int number[], int n) {
        //วิธีการแปลงค่าเป็น มิลลิวินาที
        long divide = System.nanoTime();
        algorithm4(number, n);
        long endTime = System.nanoTime();
        long result_Divide = (endTime - divide);
        System.out.println(
                "Algorithm4 = " + algorithm4(number, n) + " : " + String.format("%f", result_Divide / 1_000_000.0));
    }

    public static void main(String[] args) {
        Random random = new Random();
        int n = 100000;
        int number[] = new int[n];
        for (int i = 0; i < n; i++) {
            number[i] = random.nextInt(5000 - (-5000) + 1) + (-5000);
        }
        if(n >= 10 && n <= 1000){
            TimeMaxSub_Sequence_Sum1(number, n);
        }
        else{
            System.out.println("NA");
        }
        TimeMaxSub_Sequence_Sum2(number, n);
        TimeDivide_Conquer(number, n);
        Time_Algorithm4(number, n);
    }
}
