import java.util.ArrayList;
import java.util.List;

public class CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> arr = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), arr);
        return arr;
    }

    private void backtrack(int[] candidates, int remain, int start, List<Integer>current, List<List<Integer>> result){
        // ถ้าค่าที่เหลือติดลบ
        if(remain < 0) return;

        // ถ้าค่าที่เหลือเป็น 0 พอดี แปลว่าผลรวมเท่ากับ target แล้ว
        if(remain == 0){
            //  new ArrayList ใหม่เพื่อก็อบปี้ค่าปัจจุบันเก็บไว้ ไม่เช่นนั้นจะถูกแก้ไขตอนถอยกลับ
            result.add(new ArrayList<>(current));
            return;
        }

        // วนลูปเพื่อเช็คตัวเลขตั้งพิกัด start ขึ้นไป
        for (int i = start; i < candidates.length; i++) {
            // แอดค่าลงใน array
            current.add(candidates[i]);

            //ลุยต่อลึกเข้าไป ส่ง i ซ้ำไปได้เพราะโจทย์อนุญาติให้หยิบตัวเลขซ้ำได้
            // หักลบกับเป้าหมายที่เหลือลงตามค่าที่หยิบ 
            backtrack(candidates, remain - candidates[i], i, current, result);

            // ย้อนรอย เอาตัวเลขล่าสุดออก เพิ่มถอยกลับไปลองเลือกตัวอื่น
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        CombinationSum c = new CombinationSum();
        System.out.println(c.combinationSum(new int[]{10,1,2,7,6,1,5}, 8));
    }
}
