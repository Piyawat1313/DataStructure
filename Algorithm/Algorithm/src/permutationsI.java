import java.util.ArrayList;
import java.util.List;

public class permutationsI {

    // Time: 0 ms
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(0, nums, result);
        return result;
    }


    private void backtrack(int start, int[] nums, List<List<Integer>> result){

        // ถ้าพอยเตอร์เดินมาถึงตำแหน่งสุดท้าย แปลว่าเราสลับได้ครบทุกตัวแล้ว
        if(start == nums.length){
            List<Integer> currentPattern = new ArrayList<>();
            for(int num: nums){
                currentPattern.add(num);
            }
            result.add(currentPattern);     //บันทึกผลลงกล่องใหญ่
            return;
        }
        // วนลูปเพื่อลองสลับเลขตั้งแต่ตำแหน่ง start ไปจนถึงตัวสุดท้าย
        for(int i = start; i < nums.length; i++){
            swap(nums, start, i);   //สลับเลขช่อง i มาไว้ที่ตำแหน่ง start 
            
            backtrack(start + 1, nums, result); //เดินหน้าสลับตัวถัดไป ใช้หลักการ recursive

            swap(nums, start, i);   // สลับคืนที่เดิม เพื่อพร้อมสำหรับรอบถัดไป
        }
    }

    // สลับค่าภายในอาเรย์
    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    // วิธีที่ 2 ใช้แบบ Tree ไม่ใช้การสลับค่า 
    // Time: 1 ms
    public List<List<Integer>> permute2(int[] nums){
        List<List<Integer>> result = new ArrayList<>();

        // ถุงเก็บตัวเลขที่พร้อมให้กิ่งลูกเดินต่อ
        List<Integer> availab = new ArrayList<>();
        for(int num : nums){
            availab.add(num);
        }

        // เริ่มจาก Root node
        generateTree(availab, new ArrayList<>(), result);
        return result;
    }

    private void generateTree(List<Integer> availab, List<Integer> currentPath, List<List<Integer>> result){
        // ถ้าไม่มีเลขให้เหลือเลือกแล้ว แปลว่าเดินทาถึง leaf Node 
        if(availab.isEmpty()){
            result.add(new ArrayList<>(currentPath));   //บันทึกเส้นทางที่เดินมาทั้งหมด
            return;
        }

        // วนลูปตามจำนวนลเขที่เหลือที่อยู่ใน  Node ปัจจุบัน เพื่อแตกกิ่งก้าน
        for (int i = 0; i < availab.size(); i++) {
            int chosenNum = availab.get(i);
            // เตรียมข้อมูลสำหรับกิ่งลูก
            List<Integer> next = new ArrayList<>(availab);
            next.remove(i); //ดึงเลขที่ถูกเลือกออกจากถุงของกิ่งลูก

            currentPath.add(chosenNum); //เดินเหยียบเข้ามาในกิ่งนี้

            generateTree(next, currentPath, result);    //เดินหน้าลึกลงไปหากิ่งลูก


            currentPath.remove(currentPath.size() - 1); //ถอยหลังกับขึ้นมา
            //ลบเลขตัวล่าสุดออก เพื่อให้ลูปรอบถัดไปเปลี่ยนไปเลือกกิ่งอื่นในระดับเดียวกันได้
        }
    }


    public static void main(String[] args) {
        permutationsI p = new permutationsI();
        System.out.println(p.permute(new int[]{1,2,3}));
        System.out.println(p.permute(new int[]{0,1}));
        System.out.println(p.permute(new int[]{1}));
        System.out.println("==========TREE NODE ============");
        permutationsI tree = new permutationsI();
        System.out.println(tree.permute2(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}));
    }
}
