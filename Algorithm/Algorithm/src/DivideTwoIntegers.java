public class DivideTwoIntegers {
    public int divide(int dividend, int divisor) {
        // เช็คเลขล้นเกินขอบเขตของ Integer
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        int sum = 0;
        boolean isNegative = (dividend < 0) ^ (divisor < 0);   // เช็คว่าค่าทีี่ส่งเข้ามาสองค่าติดลบหรือไม่
        // แปลงเป็นค่าบวกให้หมดเพื่อการคำนวณที่ง่าย
        long absDividend = Math.abs((long)dividend);
        long absDivisor = Math.abs((long)divisor);

        // หักลบค่าด้วยการเลื่อนบิต
        while (absDividend >= absDivisor) {
            long temp = absDivisor;
            int multiple = 1;
            // เลื่อบิตตัวหารไปเรื่อยๆ (คูณ 2 ไปเรื่อยๆ) ตราบใดที่ยังไม่กเินตัวตั้ง
            while (absDividend >= (temp << 1)) {
                temp <<= 1;
                multiple <<= 1;
            }
            // หักลบตัวที่ขยายแล้วออกจากตัวตั้ง
            absDividend -= temp;
            sum += multiple;
        }
        // ถ้าเป็นค่าลบให้ใส่เครื่องหมายคืน
        if(isNegative){
            sum = -sum;
        }
        return Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, sum));
    }

    public static void main(String[] args) {
        DivideTwoIntegers d = new DivideTwoIntegers();
        System.out.println(d.divide(-1, -1));
    }
}
