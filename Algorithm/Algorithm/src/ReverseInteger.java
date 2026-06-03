public class ReverseInteger {
    public int reverse(int x) {
        int result = 0;
        while (x != 0) {
            // แกะตัวสุดท้าย
            int one = x % 10;
            
            // เช็คว่าเกินขอบเขตบนของ Integer หรือไม่
            if(result >= 214748364 && one > 7){
                return 0;
            }
            // ขอบล่าง Integer
            if(result <= -214748364 && one < -8){
                return 0;
            }
            // เอาตัวสุดท้ายไปต่อหลัง
            result = (result * 10) + one;

            // หั่นเลขท้ายของ X ทิ้งไป เพื่อเตรียมทำหลักถัดไปในรอบหน้า
            x = x / 10;
        }
        return result;
    }

    public void test(){
        System.out.println(reverse(1534236469));
    }

    public static void main(String[] args) {
        ReverseInteger r = new ReverseInteger();
        r.test();
    }
}
