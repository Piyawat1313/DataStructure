import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class permutationsII {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(0, nums, result); //เรียกฟังก์ชั่นเริ่มแตกกิ่งจากดัชนีที่ 0 
        return result;
    }

    private void backtrack(int start, int[] nums, List<List<Integer>> result){
        // เดินมาจนถึงสุดปลายกิ่ง บันทึกผลลัพธิ์
        if(start == nums.length){
            List<Integer> currentPath = new ArrayList<>();
            for(int num : nums){
                currentPath.add(num);
            }
            result.add(currentPath);
            return;
        }
        // สร้าง Set ไว้ดักจับในระดับปัจจุบัน
        // ดูว่าในตำแหน่ง start เคยมีเลขค่านี้ถูกสลับมาวางหรือยัง
        Set<Integer> appeared = new HashSet<>();

        for(int i = start; i < nums.length; i++){
            // การตัดกิ่งซ้ำ
            // ถ้าเลข num[i] เคยโดนสลับมาอยู่ที่ตำแหน่ง start ในรอบปมนี้แล้ว ให้ข้ามทันที
            if(appeared.contains(nums[i])){
                continue;
            }

            appeared.add(nums[i]);  //บันทึกว่าค่าเลขนี้ถูกใช้งานที่ตำแหน่ง  start แล้วนะ

            swap(nums, start, i);   //สลับค่าโดยใช้หลักการ temp

            backtrack(start + 1, nums, result); // ลงไปทำตำแหน่งถัดไป

            swap(nums, start, i);   // swap คืนค่าตำแหน่งเดิม
        }
    }
    // สลับค่าแบบใช้ temp
    private void swap(int[] nums, int i , int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        permutationsII p2 = new permutationsII();
        System.out.println(p2.permuteUnique(new int[]{1,1,2}));
        System.out.println(p2.permuteUnique(new int[]{1,2,3}));
    }
}
