import java.util.HashSet;
import java.util.Scanner;

class Length {

    public int lengthOfLongestSubstring(String s) {
        HashSet<Character> map = new HashSet<>();   //เก็บตัวอักษรที่อยู่ในหน้าปัจจุบัน
        int left = 0;
        int max = 0;    //ตัวชี้ฝั่งซ้ายของหน้าต่าง

        // ชี้ไปทางขวาเรื่อยๆ จนหมด String
        for(int right = 0; right < s.length(); right++){
            char current = s.charAt(right);     //เก็บตัวปัจจุบันไว้

            // ถ้ามีตัวซ้ำให้หดทางซ้ายเข้ามาจนกว่าจะไม่มีตัวซ้ำ
            while (map.contains(current)) {
                map.remove(s.charAt(left));
                left++;
            }

            map.add(current);   //ใส่ตัวอักษรปัจจุบัน

            max = Math.max(max, right - left + 1);  //หาค่าที่มากที่สุด
        }
        return max;
    }

    public void test() {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        System.out.println("Length: " + lengthOfLongestSubstring(s));
        sc.close();
    }
}

public class LengthOfLongeststring {
    public static void main(String[] args) {
        Length l = new Length();
        l.test();
    }
}
