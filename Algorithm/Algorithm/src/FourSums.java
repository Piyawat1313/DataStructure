import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSums {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();

        if(nums == null || nums.length < 4) return list;
        Arrays.sort(nums);
        // ลูปแรกเลือกมา 2 ตัว
        for (int i = 0; i < nums.length - 3; i++) {
            // เช็คว่า i เดิน และ เช็คว่าตัวปัจจุบันเหมือนกับตัวก่อนหน้ามั้ยให้ข้ามไปเลย
            if(i > 0 && nums[i] == nums[i - 1]){
                continue;
            }

            // ลูปที่สองเลือกมาอีก 2 ตัว ถึงได้เลขที่บวกกัน 4 ตัว
            for (int j =  i + 1; j < nums.length - 2; j++) {
                // เช็คว่าลูปทำงาน และ เช็คตัวปัจจุบันกับตัวก่อนหน้าเหมือนกันหรือไม่ ให้ข้ามการทำงานไป
                if(j > i + 1 && nums[j] == nums[j - 1]){
                    continue;
                }

                int left = j + 1;
                int right = nums.length - 1;

                // สูตรทำเหมือน 3Sums
                while (left < right) {
                    // ป้องกันการเกินขอบเขตของ int โดยใช้ long
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                    if(sum == target){
                        list.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        while (left < right && nums[left] == nums[left + 1]) left++;

                        while (left < right && nums[right] == nums[right - 1]) right--;

                        left++;
                        right--;
                    }
                    else if(sum < target){
                        left++;
                    }
                    else{
                        right--;
                    }
                }
            }
        }
        return list;    
    }

    public static void main(String[] args) {
        FourSums f = new FourSums();
        System.out.println(f.fourSum(new int[]{1,0,-1,0,-2,2}, 0).toString());
    }
}
