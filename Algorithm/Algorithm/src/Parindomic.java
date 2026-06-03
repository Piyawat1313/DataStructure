public class Parindomic {
    
    public String longestPalindrome(String s) {
        char[] ch = s.toCharArray();
        int start = 0;
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
           int round1 = expandFromCenter(ch, i, i); //ส่งไปกางปีกกรณีที่เป็นเลขคี่
           int round2 = expandFromCenter(ch, i, i + 1); //ส่งไปการปีกกรณีที่เป็นเลขคู่

           int len = Math.max(round1, round2);  //หาว่ารอบนี้แบบคี่หรือแบบคู่กางได้ยาวกว่ากัน

            //ถ้าความความยาวรอบนี้ ไปไกลกว่าเดิมที่เคยบันดึกไว้    
           if(len > max){   
                max = len;  //อัปเดตสถิติใหม่
                start = i - (len - 1) / 2;  //หาจุดเริ่มต้นของคำในรอบนี้ 
           }

        }
        return s.substring(start, start + max);
    }

    // method กางปีก ซ้ายขวา
    private int expandFromCenter(char[] s, int left, int right){
        // ทางซ้ายห้ามหลุดขอบ
        //ทางขวาห้ามหลุดขอบ
        // ตัวอักษรฝั่งซ้ายและฝั่งขาวหน้าตาต้องเหมือนกัน
        while (left >= 0 && right < s.length && s[left] == s[right]) {
            left--; //ทางซ้ายถอยไปทางซ้าย
            right++;    //ทางขวาถอยไปทางขวา
        }
        
        return right - left - 1;    //สูตรคำนวณความยาวเมื่อหลุด loop ขวา - ซ้าย - 1
    }
    public static void main(String[] args) {
        Parindomic p = new Parindomic();
        System.out.println(p.longestPalindrome("babad"));
    }
}
