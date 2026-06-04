public class MostWater {

    public int maxArea(int[] height) {
        int L = height.length;
        int start = 0;
        int end = L - 1;
        int max = 0;
        while (start < end) {
            int width = end - start;    //คำนวณความกว้าง  
            int currentH = Math.min(height[start], height[end]);    //หาความสูงของน้ำ
            int currenW = width * currentH; //คำนวณน้ำในปัจจุบัน
            max = Math.max(max, currenW);   //อัปเดตค่าสูงสุดถ้าเจอพื้นที่ใหญ่กว่า

            // ขยับตัวชี้ ฝั่งไหนเตี้ยกว่า ให้ขยับฝั่งนั้นเข้าใจ
            if(height[start] < height[end]) {   
                start++;
            }
            else{
                end--;
            }
        }
        return max;
    }
    public static void main(String[] args) {
        MostWater m = new MostWater();
        System.out.println(m.maxArea(new int[]{1,8,6,2,5,4,8,3,7}));
    }
}