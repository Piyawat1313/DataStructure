public class IntegerToRoman {
    public String intToRoman(int num) {
        int[] val = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < val.length; i++) {
            // ถ้าจำนวนตัวเลขสามารถหักลบด้วยค่าปัจจุบันได้
            while(num >= val[i]){
                str.append(symbols[i]); //ใส่สัญลักษณ์
                num -= val[i];  //หักลบเลขออก
            }
        }
        return str.toString();
    }

    public static void main(String[] args) {
        IntegerToRoman r = new IntegerToRoman();
        System.out.println(r.intToRoman(3749));
    }
}
