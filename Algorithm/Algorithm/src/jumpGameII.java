public class jumpGameII {
    // ใช้แนวคิด Greedy Algorithm
    public int jump(int[] nums) {
        int jump = 0;
        int farthest = 0;
        int currentEnd = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]); //หาค่าที่กระโดดไกลที่สุด

            // เดินมาถึงขอบเขตของการกระโดดรอบก่อนหน้า
            if(i == currentEnd){
                jump++; //กระโดดเพิ่มอีก 1 ครั้ง
                currentEnd = farthest;  //อัปเดตค่าขอบเขตใหม่ด้วยระยะที่ไกลที่สุด

                // ถ้าเกินขอบเขตของอาเรย์ ให้หยุดการทำงานทันที
                if(currentEnd >= nums.length - 1){
                    break;
                }
            }
        }
        return jump;
    }

    public static void main(String[] args) {
        jumpGameII j = new jumpGameII();
        System.out.println(j.jump(new int[]{2,3,1,1,4}));
        System.out.println(j.jump(new int[]{2,3,0,1,4}));
        System.out.println(j.jump(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}));
    }
}
