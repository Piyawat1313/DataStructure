import java.util.Arrays;

public class ThreeSumCloset {
    // Test 45 / 107
    public int threeSumClosest(int[] nums, int target) {
        int n = nums.length;
        int result = 0;
        Arrays.sort(nums);
        for (int i = 0; i < n; i++) {
            if(i + 1 < n && i + 2 < n && nums[i] != nums[i + 1] && nums[i + 1] != nums[i + 2]){
                result = nums[i] + nums[i + 1] + nums[i + 2];
                System.out.println(nums[i] + " " + nums[i + 1] + nums[i + 2] + " ");
            }
            else if(i + 1 < n && i + 2 < n && nums[i] == nums[i + 1] && nums[i + 1] == nums[i + 2]){
                result = nums[i] + nums[i + 1] + nums[i + 2];
                System.out.println(nums[i] + " " + nums[i + 1] + nums[i + 2] + " ");
            }
        }
        if(result == target){
            return result;        
        }
        return result;
    }
    // Test ได้เต็ม
    public int threeSum(int[] nums, int target){
        int n = nums.length;
        Arrays.sort(nums);

        int closet = nums[0] + nums[1] + nums[2];   //ตั้งค่าผลรวมเบื้องต้นของอาเรย์ 3 ตัวแรก

        //วิ่งลูปทีละ 3  ตัว
        for (int i = 0; i < n - 2; i++) {
            int left = i + 1;
            int right = n - 1;

            // ลูปบีบตัวชี้สองตัวเพื่อหาคู่
            while (left < right) {
                int curr = nums[i] + nums[left] + nums[right];  //บวกจำนวนที่หาได้ในอาเรย์ 3 ตัว

                // กรณีที่ผลรวมเท่ากับตัวที่รับเข้ามา
                if(curr == target){ 
                    return curr;
                }

                // เช็คว่าผลรวมรอบนี้ เข้าใกล้ target มากกว่าค่าเดิมที่เคยจำได้หรือไม่
                //วัดความใกล้ด้วย Math.abs
                if(Math.abs(curr - target) < Math.abs(closet - target)){
                    closet = curr;
                }

                // เงื่อนไขขยับตัวชี้
                if(curr < target){
                    left++; //ค่าน้อยเกินไป เลื่อนเข้ามา
                }
                else{
                    right--;    //ถ้าค่ามากเกินไป เลื่อนขวาลงมาเพื่อลดค่ารวม
                }
            }
        }
        return closet;
    }
    public static void main(String[] args) {
        ThreeSumCloset t = new ThreeSumCloset();
        System.out.println(t.threeSumClosest(new int[]{1,1,-1}, 0));
    }
}