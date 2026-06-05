import java.util.ArrayList;
import java.util.List;

public class GenerateParentheses {
    public List<String> generateParenthesis(int n) {
        List<String> str = new ArrayList<>();
        backtrack(str, new StringBuilder(), 0, 0, n);   //เริ่มต้นด้วยการส่งสตริงเปล่า ตัวเปิด = 0 ตัวปิด = 0
        return str;
    }

    private void backtrack(List<String> result, StringBuilder curr, int open, int close, int max){
        // ถ้าความยาวสตริงยาวเท่ากับ max * 2 แปลว่าใช้วงเล็บครบทุกตัวแล้ว
        if(curr.length() == max * 2){
            result.add(curr.toString());    //บันทึกคำตอบ
            return;
        }
        // ถ้าจำนวนวงเล็บเปิดยังไม่ครบจำนวนสูงสุด ให้ลองใส่วงเล็บเปิด
        if(open < max){
            curr.append("(");   //ลองใส่เปิด
            backtrack(result, curr, open + 1, close, max);  //ลุยต่อในชั้นถัดไป
            curr.deleteCharAt(curr.length() - 1);   //ย้อนกลับ
        }
        // ถ้าจำนวนวงเล็บปิดยังน้อยกว่าวงเล็บเปิด ให้ลองใส่วงเล็บปิด
        if(close < open){
            curr.append(")");   //ใส่วงเล็บปิด
            backtrack(result, curr, open, close + 1, max);  //ลุยต่อในชั้นถัดไป
            curr.deleteCharAt(curr.length() - 1);   //ย้อนกลับ
        }
    }
    public static void main(String[] args) {
        GenerateParentheses g = new GenerateParentheses();
        System.out.println(g.generateParenthesis(3));
    }
}
