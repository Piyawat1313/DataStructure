import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class groupAnagram {
    public List<List<String>> groupAnagrams(String[] strs) {

        // สร้าง HashMap โดยให้ key เป็นกลุ่มของคำที่เรียงแล้ว
        HashMap<String, List<String>> anagramMap = new HashMap<>();

        for (String s : strs) {
            // แปลง string เป็น char[] เพื่อทำการเรียงตัวอีกษร
            char[] ch = s.toCharArray();
            Arrays.sort(ch);
            
            // แปลง char Array ที่เรียงแล้วกลับมาเป็น String
            String sortedKey = new String(ch);

            // ถ้ายังไ่มี key นี้ใน map ให้สร้าง ArrayList ใหม่ขึ้นมารอก่อน
            if(!anagramMap.containsKey(sortedKey)){
                anagramMap.put(sortedKey, new ArrayList<>());
            }

            // เพิ่มคำดั้งเดิมลงในกลุ่มของ key นั้นๆ
            anagramMap.get(sortedKey).add(s);
        }

        return new ArrayList<>(anagramMap.values());
    }


    public static void main(String[] args) {
        groupAnagram g = new groupAnagram();
        System.out.println(g.groupAnagrams(new String[]{"eat", "tea","tan","ate","nat","bat"}));
    }
}
