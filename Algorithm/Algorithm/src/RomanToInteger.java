public class RomanToInteger {
    public int romanToInt(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            int current = getVal(s.charAt(i));  //เก็บตัวปัจจุบันไว้

            // เช็คว่าตัวถัดไป น้อยกว่าตัวเลขปัจจุบันหรือไม่
            if(i + 1 < s.length() && current < getVal(s.charAt(i + 1))){
                result -= current;  //หักออกไป
            }
            else{
                result += current;  //บวกเพิ่มเข้ามา
            }
        }
        return result;
    }

    // คอยเช็คว่าอักขระที่เข้ามาตรงกับตัวไหน
    private int getVal(char ch){
        switch (ch) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }
    public static void main(String[] args) {
        RomanToInteger r = new RomanToInteger();
        System.out.println(r.romanToInt("III"));
    }
}
