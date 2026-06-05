import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LetterCombinationOfaPhoneNumber {

    // map เก็บคู่ตัวเลขกับตัวอักษรตามภาพ
    private static final Map<Character, String> PhoneNumber = Map.of(
        '2', "abc",
        '3', "def",
        '4', "ghi",
        '5', "jkl",
        '6', "mno",
        '7', "pqrs",
        '8', "tuv",
        '9', "wxyz"
    );

    public List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        
        // เช็คว่ามีค่าว่างหรือไม่
        if(digits == null || digits.isEmpty()) return list;

        backtrack(list, digits, 0, new StringBuilder());
        return list;

    }

    private void backtrack(List<String> list, String digits, int index, StringBuilder current){
        // เดินสุดความยาวของตัวเลขให้บันทึกผลด้วย
        if(index == digits.length()){
            list.add(current.toString());
            return;
        }
        // ดึงตัวเลขและอักษรของปุ่มนั้นออกมา
        char currentDigits = digits.charAt(index);
        String letter = PhoneNumber.get(currentDigits);

        if(letter != null){
            // เลือกอักษรทีละตัว
            for (int i = 0; i < letter.length(); i++) {
                char l = letter.charAt(i);

                current.append(l);  //เลือกตัวอักษรนี้
                backtrack(list, digits, index + 1, current);    //เดินหน้าไปตัวถัดไป
                current.deleteCharAt(current.length() - 1); //ย้อนกลับ
            }
        }
    }

    public static void main(String[] args) {
        LetterCombinationOfaPhoneNumber l = new LetterCombinationOfaPhoneNumber();
        System.out.println(l.letterCombinations("2"));
    }
}
