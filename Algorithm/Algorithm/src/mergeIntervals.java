import java.util.*;

public class mergeIntervals {
    public int[][] merge(int[][] intervals) {
        List<int[]> merged = new ArrayList<>();
        // เรียงลำดับตามค่าเริ่มต้นในแต่ละช่วงของอาเรย์
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        int[] curr = intervals[0];
        merged.add(curr);
        // เช็คช่วงที่เหลือภายในอาเรย์
        for(int[] inter : intervals){
            int currentEnd = curr[1];
            int nextStart = inter[0];
            int nextEnd = inter[1];
            //เช็คว่าเกิดการทับซ้านกันหรือไม่
            if(nextStart <= currentEnd){
                // เกิดการทับซ้อน ขยายเวลาสิ้นสุดของช่วงปัจจุบัน
                curr[1] = Math.max(currentEnd, nextEnd);
            }
            else{
                // ไม่ทับซ้อนกัน ย้ายเป้าหมายมาดูช่วงที่ใหม่ แล้วเพิ่มลงในลิสต์เป็นคำตอบ
                curr = inter;
                merged.add(curr);
            }
        }
        // แปลง list กลับไปเป็นอาเรย์สองมิติ
        return merged.toArray(new int[merged.size()][]);
    }


    public int[][] merge2(int[][] intervals){
        int max = 0;
        for(int[] inte : intervals){
            max = Math.max(max, inte[0]);
        }

        int[] map = new int[max + 1];
        for(int[] i : intervals){
            int start = i[0];
            int end = i[1];
            map[start] = Math.max(end + 1, map[start]);
        }

        int mergeCount = 0;
        int start = -1; 
        int end = -1;
        for (int i = 0; i < map.length; i++) {
            if(map[i] != 0){
                if(start == -1){
                    start = i;
                }

                end = Math.max(end, map[i] - 1);
            }
            if(i == end){
                intervals[mergeCount++] = new int[]{start, end};
                start = -1;
                end = -1;
            }
        }
        if(start != -1){
            intervals[mergeCount++] = new int[]{start, end};
        }
        int[][] res = new int[mergeCount][];

        for (int i = 0; i < mergeCount; i++) {
            res[i] = intervals[i];
        }
        return res;
    }

    public static void main(String[] args) {
        mergeIntervals m = new mergeIntervals();
        System.out.println(m.merge(new int[][]{
            {1,3},
            {2,6},
            {8,10},
            {15,18}
        }));

        System.out.println(m.merge(new int[][]{
            {1,4},
            {4,5}
        }));

        System.out.println(m.merge(new int[][]{
            {4,7},
            {1,4}
        }));
    }
}
