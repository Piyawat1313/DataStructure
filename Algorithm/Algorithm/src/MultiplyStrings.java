public class MultiplyStrings {
    // test 161 / 311
    public String multiply(String num1, String num2) {
        long number1 = Long.parseLong(num1);
        long number2 = Long.parseLong(num2);
        long result = number1 * number2;

        String s = String.valueOf(result);
        return s;
    }


    public String multiply2(String num1, String num2){
        int n1 = num1.length();
        int n2 = num2.length();
        int[] products = new int[n1 + n2];
        // แปลงสตริงให้เป็นตัวเลข แล้วทำการคูณจากขวามาซ้ายแบบทีละตัวเอามาเก็บไว้ในอาเรย์
        for (int i = n1 - 1; i >= 0; i--) {
            for (int j = n2 - 1; j >= 0; j--) {
                int d1 = num1.charAt(i) - '0';  //แปลงเป็นตัวเลข
                int d2 = num2.charAt(j) - '0';  //แปลงเป็นตัวเลข
                products[i + j + 1] += d1 * d2; //คูณเสร็จจับมาบวกสะสมไว้ในอาเรย์
            }
        }

        // ต่อตัวเลขในอาเรย์ทีละตัว
        int carry = 0;
        for(int i = products.length - 1; i >= 0; i--){
            int temp = (products[i] + carry) % 10;  //เอาเลขหลักแรกออกมาก่อน
            carry = (products[i] + carry) / 10; //หาตัวที่เหลือถัดไป
            products[i] = temp;
        }

        StringBuilder sb = new StringBuilder();
        // รวมตัวเลขให้เป็นสตริงเข้าด้วยกัน
        for(int num : products){
            sb.append(num);
        }
        // ถ้าในสตริงบิลเดอร์ไม่เป็นค่าว่างและเจอ 0 
        while (sb.length() != 0 && sb.charAt(0) == '0') {
            sb.deleteCharAt(0); //ลบ 0 ที่เป็นอักขระออก
        }

        return sb.length() == 0 ? "0" : sb.toString();
    }

    public static void main(String[] args) {
        // String num = "2";

        // int number = Integer.parseInt(num);
        // System.out.println(number);

        MultiplyStrings m = new MultiplyStrings();
        System.out.println(m.multiply2("123456789", "987654321"));
        System.out.println(m.multiply2("6913259244", "71103343"));
        System.out.println("Wrong --> " + m.multiply2("498828660196", "840477629533"));
    }
}
