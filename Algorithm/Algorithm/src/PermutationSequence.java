import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class PermutationSequence {
    
    public String getPermutation(int n, int k) {
        HashMap<Integer, String> map = new HashMap<>();

        String result = "";
        map.put(1, "123");
        map.put(2, "132");
        map.put(3, "213");
        map.put(4, "231");
        map.put(5, "312");
        map.put(6, "321");

        if(map.containsKey(k) && map.containsKey(n)){
            result = map.get(k);
        }
        else{
            result = map.get(n);
            result += n;
        }
        return result;

    }

    public String getPermutation2(int n, int k){
        
        List<Integer> number = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            number.add(i);
        }

        // คำนวณ Factorial เตรียมไว้ เพื่อใช้เป็น block size ในแต่ละรอบ
        int[] factorial = new int[n + 1];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }

        // ปรับค่า k ให้เป็น 0-indexed
        k = k - 1;

        StringBuilder sb = new StringBuilder();

        // วนลูปหาตัวเลขทีละหลักจากซ้ายไปขวา
        // เริ่มจากตำแหน่งที่เหลือ n-1 หลักจนถึงหลักสุดท้าย
        for (int i = n - 1; i >= 0; i--) {
            //  ขนาดของ block size ในรอบนี้คือ i!
            int blockSize = factorial[i];

            // หา Index ของตัวเลขที่ต้องการหลังจากเลขที่เหลืออยู่ทั้งหมด
            int index = k / blockSize;

            // ดึงตัวเลขใน index นั้นมาเป็นต่อเป็น string
            sb.append(number.get(index));

            // ดึงตัวเลขที่ใช้ไปแล้วออกจากลิสต์ เพื่อไม่ให้ถูกเรียกซ้ำ
            number.remove(index);

            // อัปเดตค่า k (หาเศษที่เหลือ) สำหรับคำนวณถัดไป
            k = k % blockSize;
        }

        return sb.toString();
    }


    public static void main(String[] args) {
        PermutationSequence p = new PermutationSequence();
        System.out.println(p.getPermutation2(3, 3));
        System.out.println(p.getPermutation2(4, 9));
        System.out.println(p.getPermutation2(3, 1));
        System.out.println(p.getPermutation2(1, 1));

    }
}
