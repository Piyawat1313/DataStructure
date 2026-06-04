import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Threesums {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);  //เรียงลำดับตัวเลข

        for (int i = 0; i < nums.length - 2; i++) {
            // ป้องกันตัว i ซ้ำ ถ้าตัวปัจจุบันเหมือนตัวก่อนหน้า ให้ข้ามการทำงานนี้ไป
            if(i > 0 && nums[i] == nums[i - 1]){
                continue;
            }

            // ตัวชี้ซ้ายขวาบีบเข้าหากัน
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                // กรณีที่ผลรวมเป็น 0
                if(sum == 0){
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));    //แอดค่าที่ได้ลงอาเรย์ลิสต์

                    // ป้องกันทางซ้ายซ้ำ ขยับข้ามตัวที่หน้าตาเหมือนกัน
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    
                    //ป้องกันตัวทางขวา ซ้ำ ขยับข้ามหน้าตาตัวที่เหมือนกัน 
                    while (left < right && nums[left] == nums[right - 1]) right--;

                    // ขยับเข้ามาพร้อมกันเพื่อหาคู่ถัดไป
                    left++;
                    right--;
                }
                // กรณีที่ผลรวมติดลบ
                else if(sum < 0){
                    left++; //ขยับไปทางว้าย
                }
                else{
                    // ค่ามากเกินไป ขยับทางขวาลดลงมาเพื่อหาคู่ถัดไป
                    right--;
                }

            }
        }
        return result;
    }
    public static void main(String[] args) {
        Threesums t = new Threesums();
        System.out.println(t.threeSum(new int[]{-1,0,1,2,-1,-4}).toString());
    }
}
