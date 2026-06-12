public class FirstMissingPositive {
    // Cyclic Sort
    public int firstMissingPositive(int[] nums) {
        int L = nums.length;
        // จัดบ้าน
        for (int i = 0; i < L; i++) {
            // ตราบใดที่ num[i] เป็นเลขบวก ไม่เกินขอบเขตอาเรย์ และยังอยู่ไม่ถูกตำแหน่ง
            while (nums[i] > 0 && nums[i] <= L && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1); //สลับค่าภายในอาเรย์
            }
        }

        // ตรวจบ้านหาเลขที่หายใป
        for (int i = 0; i < L; i++) {

            // ถ้าเจอตำแหน่งที่ผิดปกติ ให้คืนค่าเลขที่หายไป
            if(nums[i] != i + 1){
                return i + 1;
            }
        }

        return L + 1;   //ถ้าในอาเรย์มีครบเรียงกันอย่างสวยงาม คำตอบคือ L + 1
    }

    // สลับค่าภายในอาเรย์โดยตรง
    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;

    }

    public static void main(String[] args) {
        FirstMissingPositive f = new FirstMissingPositive();
        System.out.println(f.firstMissingPositive(new int[]{1,2,0}));
        System.out.println(f.firstMissingPositive(new int[]{3,4,-1,1}));
        System.out.println(f.firstMissingPositive(new int[]{7,8,9,11,12}));
    }
}
