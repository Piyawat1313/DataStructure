
public class SearchInsertPosition {
    // Binary Search
    public int searchInsert(int[] nums, int target) {
        int L = 0;
        int R = nums.length - 1;
        while (L <= R) {
            int mid = L + (R - L) / 2;  //หาจุดกึ่งกลาง

            // ถ้าเจอเลขนั้นพอดี ให้คืนค่า index นั้นเลย
            if(nums[mid] == target){
                return mid;
            }
            // target มีค่ามากกว่าค่าตรงกลาง ให้ขยับฝั่งซ้าย
            else if(nums[mid] < target){
                L = mid + 1;
            }
            // target น้อยกว่าค่าตรงกลาง ให้ขยับฝั่งขวามาทางว้าย
            else{
                R = mid - 1;
            }
        }
        // ถ้าไม่เจอ target left จะกลายเป็นตำแหน่งที่เหมาะสมในการแทรกทันที
        return L;
    }

    public static void main(String[] args) {
        SearchInsertPosition s = new SearchInsertPosition();
        System.out.println(s.searchInsert(new int[]{1,3,5,6}, 5));
    }
}
