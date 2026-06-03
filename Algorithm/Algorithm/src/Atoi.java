public class Atoi {
    public int myAtoi(String s) {
        // เช็คเว้นวรรค
        int start = 0;
        int sign = 1;
        while (s.charAt(start) == ' ') {
            start++;
        }

        // เช็คเครื่องหมาย
        if(start < s.length()){
            // เช็คเครื่องมาก + -
            if(s.charAt(start) == '-'){
                sign = -1;
                start++;
            }
            else if(s.charAt(start) == '+'){
                sign = 1;
                start++;
            }
        }
        

        // แปลงค่าตัวอักษรให้เป็นตัวเลข
        long result = 0;
        while (start < s.length() && s.charAt(start) >= '0' && s.charAt(start) <= '9') {
            
            // แปลง char เป็น int
            int digit = s.charAt(start) - '0';

            result = (result * 10) + digit; //สะสมตัวเลขดันขึ้นถัดไป

            if(result < -2147483648){  //ขอบบน
                return -2147483648;
            }
            
            else if(result > 2147483647){    //ขอบล่าง
                return 2147483647;
            }

            start++;    //ไปตัวถัดไป
        }
        return (int) (result * sign);
    }
    public static void main(String[] args) {
        
        Atoi a = new Atoi();
        System.out.println(a.myAtoi("0-1"));
    }
}
