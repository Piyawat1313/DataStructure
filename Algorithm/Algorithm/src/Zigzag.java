public class Zigzag {
    public String convert(String s, int numRows) {
        //ถ้ามีตัวเดียวให้ Return ออกไปเลย
        if(numRows == 1 || s.length() == numRows - 1) return s;


        StringBuilder[] str = new StringBuilder[numRows];
        int indexR = 0; //ตัวคอยบอกว่าอยู่ตะกร้าไหน
        boolean toggle = false;     //ตัวคอยกำหนดว่าจะวิ่งขึ้นบนหรือลงล่าง
        
        //สร้างชั้นวางและตะกร้าไส่ไว้ทุกช่อง 
        for (int i = 0; i < numRows; i++) {
            str[i] = new StringBuilder();
        }
        for (int i = 0; i < s.length(); i++) {
            str[indexR].append(s.charAt(i));    //เอาตัวอักษรใส่ตระกร้า

            // ถ้าตะกร้าอยู่บนสุด
            if(indexR == 0){
                toggle = true;  //ให้เดินข้างบน
            }
            else if(indexR == numRows - 1){     //ตะกร้าอยู่ข้างล่างสุด
                toggle = false;     //ให้เดินข้างล่าง
            }

            if(toggle){  
                indexR++;   //ลงข้างล่าง
            }
            else{
                indexR--;   //ขึ้นข้างบน
            }
        }
        // รวมสตริงทั้งหมดเป็นก้อนเดียว
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length; i++) {
            result.append(str[i]);
        }
        return  result.toString();
    }
    public static void main(String[] args) {
        Zigzag z = new Zigzag();
        System.out.println(z.convert("A", 1));
    }
}
