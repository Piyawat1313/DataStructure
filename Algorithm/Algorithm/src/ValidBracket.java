import java.util.Stack;

public class ValidBracket {
    // Test 52/102
    public boolean isValid(String s) {
        int L = s.length();
        boolean isValidbracket1 = false;
        boolean isValidbracket2 = false;
        boolean isValidbracket3 = false;
        for (int i = 0; i < L; i++) {
            if (i + 1 < L && s.charAt(i) == '(' && s.charAt(i + 1) == ')') {
                System.out.println(1);
                isValidbracket1 = true;
                return true;
            }
            else if(i + 1 < L && (s.charAt(i) != '(' || s.charAt(i + 1) != ')')){
                isValidbracket1 = false;
            }
            if (i + 1 < L && s.charAt(i) == '[' && s.charAt(i + 1) == ']') {
                System.out.println(2);
                isValidbracket2 = true;
                return true;
            }
            else if(i + 1 < L && (s.charAt(i) != '[' || s.charAt(i + 1) != ']')){
                isValidbracket2 = false;
            }
            if (i + 1 < L && s.charAt(i) == '{' && s.charAt(i + 1) == '}') {
                System.out.println(3);
                isValidbracket3 = true;
                return true;
            }
            else if(i + 1 < L && (s.charAt(i) != '{' || s.charAt(i + 1) != '}')){
                isValidbracket3 = false;
            }
        }
        if (isValidbracket1 && isValidbracket2) {
            System.out.println(7);
            return true;
        }
        if (isValidbracket1 && isValidbracket3) {
            System.out.println(8);
            return true;
        }
        if (isValidbracket2 && isValidbracket3) {
            System.out.println(9);
            return true;
        }

        return false;
    }

    public boolean isValid2(String s){

        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            // หาตัวเปิดเข้า Stack
            if(c == '(' || c == '[' || c == '{'){
                stack.push(c);
            }
            else{
                // ถ้าเจอวงเล็บปิด แต่ไม่มีวงเล็บเหลืออยู่ใน Stack แปลว่าผิดเงื่อนไข
                if(stack.isEmpty()){
                    return false;
                }
                char top = stack.pop(); //ดึงตัวยอดจาก Stack มาเช็คว่าตรงกับคู่ไหน

                // เจอตัวที่ไม่ถูกต้อง return false ทันที
                if(c == ')' && top != '(') return false;
                if(c == '}' && top != '{') return false;
                if(c == ']' && top != '[') return false;
            }
        }
        // ถ้า Stack จับคู่หมด stack โล่ง  true
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ValidBracket v = new ValidBracket();
        System.out.println(v.isValid("([])"));
    }
}
