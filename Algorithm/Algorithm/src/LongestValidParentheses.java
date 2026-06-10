import java.util.Stack;

public class LongestValidParentheses {
    public int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); //-1 เป็นจุดความยาวที่เราเอาไว้ใช้คำนวณตำแหน่ง
        int n = s.length();
        int max = 0;
        for (int i = 0; i < n; i++) {
            // เจอวงเล็บเปิดใส่เข้าไปใน stack 
            if(s.charAt(i) == '('){
                stack.push(i);
                System.out.println("/*/*/*/*");
            }
            // เจอวงเล็บปิด
            else{
                // เอาวงเล็บตัวล่าสุดออกมา
                stack.pop();
                System.out.println("::::::::");

                // ถ้า stack ไม่ว่าง แสดงว่าจับคู่สำเร็จ
                if(!stack.isEmpty()){
                    int current = i - stack.get(stack.size() - 1);  //คำนวณความยาวโดยเอา index ปัจจุบัน ลบกับ index บนสุดของ stack
                    max = Math.max(max, current);
                    System.out.println("hdhdhdhdhdh");
                }
                // ถ้า stack ว่างแสดงว่าเป็นวงเล็บส่วนเกิน
                else if(stack.size() == -1 || stack.isEmpty()){
                    stack.push(i);
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        LongestValidParentheses l = new LongestValidParentheses();
        System.out.println(l.longestValidParentheses("(()"));
    }
}
