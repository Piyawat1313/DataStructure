import java.util.Arrays;

public class FindFirstAndLastPositionOfElementInSortedArray {

    // test 41/88
    public int[] searchRange(int[] nums, int target) {
        int L = nums.length;
        int[] arr = new int[2];
        arr[0] = -1;
        arr[1] = -1;

        if (L == 0) {
            arr[0] = -1;
            arr[1] = -1;
        }

        for (int i = 0; i < L; i++) {
            if (i + 1 < L && nums[i] == target && nums[i + 1] == target) {
                arr[0] = i;
                arr[1] = i + 1;
                System.out.println("+++++++++++");
            } 
            
        }
        return arr;
    }

    public int[] searchRange2(int[] nums, int target) {
        int[] arr = new int[2];

        arr[0] = findPosition(nums, target, true);  //หาตำแหน่งแรก
        arr[1] = findPosition(nums, target, false); //หาตำแหน่งสุดท้าย
        return arr;
    }

    // Binary search
    private int findPosition(int[] nums, int target, boolean isFindingFirst){
        int index = -1;
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // เจอตัวเลขที่ต้องการ
            if(nums[mid] == target){
                index = mid;    //บันทึกตำแหน่งไว้

                // ถ้ามันเป็นตำแหน่งแรก
                if(isFindingFirst){
                    right = mid - 1;    //ให้เดินหน้าค้าหาฝั่งซ้ายต่อ
                }
                else{
                    left = mid + 1; //หาตำแหน่งสุดท้าย ให้เดินหาหน้าฟาฝั่งขวาต่อ
                }
            }
            else if(nums[mid] < target){
                left = mid + 1;
            }
            else{
                right = mid - 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        FindFirstAndLastPositionOfElementInSortedArray f = new FindFirstAndLastPositionOfElementInSortedArray();
        System.out.println(Arrays.toString(f.searchRange2(new int[] { 5, 7, 7, 8, 8, 10 }, 6)));
        System.out.println(Arrays.toString(f.searchRange2(new int[] {1}, 1)));
        System.out.println(Arrays.toString(f.searchRange2(new int[] { 5, 7, 7, 8, 8, 10 }, 8)));
    }
}
