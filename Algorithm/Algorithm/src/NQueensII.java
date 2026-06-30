import java.util.*;

public class NQueensII {
    private int count = 0;  //นับจำนวนวิธีที่หาเจอทั้งหมด
    private Set<Integer> cols = new HashSet<>();
    private Set<Integer> diag1 = new HashSet<>();
    private Set<Integer> diag2 = new HashSet<>();

    // Time: 4 ms
    public int totalNQueens(int n) {
        count = 0;  //รีเซ็ทเป็นค่าเริ่มต้นก่อนรัน
        backtrack(0, n);
        return count;
    }
    private void backtrack(int row, int n){
        // ถ้าวางควีนจนทะลุแถวสุดท้าย แสดงว่าเจอวิธีที่ถูกต้องแล้ว 1 วิธี
        if(row == n){
            count++;
            return;
        }
        // วนลูปเพื่อลองวางควีนในแต่ละ column ของแถวปัจจุบัน
        for(int col = 0; col < n; col++){
            int d1 = row + col;
            int d2 = row - col;
            // ตรวจสอบแนวโจมตี ถ้าไม่ปลอดภัยให้ข้ามช่องนี้
            if(cols.contains(col) || diag1.contains(d1) || diag2.contains(d2)){
                continue;
            }
            // เลือกและบันทึกแนวโจมตีลง Set
            cols.add(col);
            diag1.add(d1);
            diag2.add(d2);
            
            // เดินหน้า ย้ายไปคิดในแถวถัดไป
            backtrack(row + 1, n);
            
            //ย้อนรอย เอาข้อมูลออกจาก set เพื่อเปิดโอกาสฝห้แนวทางอื่น 
            cols.remove(col);
            diag1.remove(d1);
            diag2.remove(d2);
        }
    }

    // วิธีที่ 2 Time: 0 ms
    public int totalNQueens2(int n){
        return solve(n, 0, 0, 0, 0);
    }

    private int solve(int n, int row, int col, int diag1, int diag2){
        if(row == n){
            return 1;
        }

        int count = 0;
        // หาช่องว่างทั้งหมดในแถวปัจจุบันที่ปลอดภัย พร้อมที่จะให้ควีนลงไปยืนได้
        // เป็นการใช้แนวคิดแบบ Bit manipulation การจัดการในระดับบิต
        // (1 << n) - 1 --> สร้างกรดานหมากรุกจำลอง 1 << n หมายความว่าเลื่อนบิตไปทางซ้าย n ครั้ง จะได้เลขฐาน 2 จากนั้นเอาไป - 1 ถ้ามีหมายเลข 1 หมายความว่า column นี้เปิดใช้งานอยู่บนกระดาน
        // ~(col | diag1 | diag2) --> หาช่องที่ยังไม่โดนโจมตี โค้ดลักษณะนี้จะทำการกลับบิต เพื่อเลี่ยงจะไม่โดนโจมตี 
        int available = ((1 << n) - 1) & ~(col | diag1 | diag2);

        while (available != 0) {
            int position = available & -available;

            available -= position;

            count += solve(position, row + 1, col | position, (diag1 | position) << 1, (diag2 | position) << 1);
        }

        return count;
    }
    public static void main(String[] args) {
        NQueensII nn = new NQueensII();
        System.out.println(nn.totalNQueens(9));
    }
}
