import java.util.ArrayList;
import java.util.Arrays;

public class InsertInterval {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        ArrayList<int[]> results = new ArrayList<>();
        int i = 0;
        int n = intervals.length;
        
        //เพิ่มช่วงก่อนหน้าทั้งหมดที่ไม่ทับซ้อน 
        //ถ้าช่วงเวลาใดสิ้นสุดลงก่อนที่ newInterval จะเริ่ม 
        while (i < n && intervals[i][1] < newInterval[0]) {
            results.add(intervals[i]);
            i++;
        }

        // ยุบรวมช่องที่ทับซ้อนกัน
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }

        // ใส่ newInterval ที่รวมร่าง เสร็จแล้วเข้าไป
        results.add(newInterval);

        // เพิ่มช่วงเวลาที่เหลือหลังจากนั้นทั้งหมดที่อยู่ข้างหลัง
        while (i < n) {
            results.add(intervals[i]);
            i++;
        }

        // แปลง List<int[]> เป็น int[][] 
         return results.toArray(new int[results.size()][]);

    }

    public static void main(String[] args) {
        InsertInterval i = new InsertInterval();

        int[][] r = i.insert(new int[][]{
            {1,3},
            {6,9}
        }, new int[]{2,5});
        
        for (int j = 0; j < r.length; j++) {
            for (int j2 = 0; j2 < r.length; j2++) {
                System.out.print(r[j][j2] + " ");
            }
        }
    }
}
