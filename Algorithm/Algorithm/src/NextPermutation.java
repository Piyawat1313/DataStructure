public class NextPermutation {
    public void nextPermutation(int[] nums) {
        if(nums == null || nums.length <= 1) return;

        // หาจุดหักเหของอาเรย์
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        // ถ้า i พอบว่าเป็นจุดหักเห(Array เรียงจากน้อยไปมากทั้งหมด)
        if(i >= 0){
            // หาตัวท้ายอาเรย์ที่มากว่า nums[i] เพื่อเอามาสลับ
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            
            swap(nums, i, j);   //ทำการสลับค่า
        }
        // กลับหัวท้ายสมาชิกตั้งแต่ตำแหน่ง i + 1 ไปจนถึงตัวสุดท้าย
        reverse(nums, i+ 1, nums.length - 1);
    }
    // ช่วยสลับค่าในอาเรย์
    private void reverse(int[]nums, int start, int end){
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }
    // ฟังก์ชั่นช่วยสลับหัวท้ายอาเรย์จากหน้าไปหลัง
    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    public static void main(String[] args) {
        
    }
}
