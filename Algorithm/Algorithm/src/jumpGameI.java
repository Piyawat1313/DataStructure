public class jumpGameI {
    // ใช้แนวคิด Greedy Algorithm
    public boolean canJump(int[] nums) {
        int max = 0;    //จุดที่ไกลที่สุด

        for (int i = 0; i < nums.length; i++) {
            // ถ้าดัชนีปัจจุบันอยู่เกินกว่าระยะทางสูงสุดที่เราจะมาถึงได้ แปลว่าไปต่อไม่ได้
            if(i > max){
                return false;
            }

            max = Math.max(max, i + nums[i]);   //อัปเดตระยะทางที่ไกลที่สุดที่สามารถไปได้

            // ถ้าระยะสูงสุดคลุมไปถึงดัชนีสุดท้ายแล้ว ตอบ true ทันที
            if(max >= nums.length - 1){
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        jumpGameI j = new jumpGameI();
        System.out.println(j.canJump(new int[]{2,3,1,1,4}));
        System.out.println(j.canJump(new int[]{3,2,1,0,4}));
    }
}
