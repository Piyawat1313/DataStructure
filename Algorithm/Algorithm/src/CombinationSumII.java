import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSumII {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> list = new ArrayList<>();

        Arrays.sort(candidates);
        backtrack2(candidates, target, 0, new ArrayList<>(), list);
        return list;
    }

    private void backtrack2(int[] candidates, int remain, int start, List<Integer> current, List<List<Integer>> result){

        if(remain < 0) return;

        if(remain == 0){
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // ถ้า i > start แปลว่าตัวนี้ไม่ใช่ตัวแรกในรอบการตัดสินใจนี้
            // ถ้าค่าของมันไปซ้ำกับตัวก่อนหน้า ให้ข้ามไปเลย
            if(i > start && candidates[i] == candidates[i - 1]){
                continue;
            }

            // ลองใส่เลขในอาเรย์เข้าไป
            current.add(candidates[i]);

            // remain เอาค่าที่เอามาใส่ลบกับ remain เพื่อหาส่วนถัดไป
            // i + 1 ไม่ให้พิกัดซ้ำตัวเดิม
            backtrack2(candidates, remain - candidates[i], i + 1, current, result);

            // เอาตัวเลขล่าสุดออกเพื่อกิ่งอื่น
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        CombinationSumII c = new CombinationSumII();
        System.out.println(c.combinationSum2(new int[]{10,1,2,7,6,1,5}, 8));
    }
}
