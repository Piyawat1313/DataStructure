public class maximumSubArrays {
    public int maxSubArray(int[] nums) {
        // เก็บค่าเลขตัวแรกในอาเรย์
        int currentSum = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);   //เลือกทางที่ดีที่สุด เริ่มต้นใหม่ที่ตัวเองหรือว่า ไปต่อกับกลุ่มเก่า
            maxSum = Math.max(maxSum, currentSum);  //อัปเดตสถิติผลรวมมากที่สุดเท่าที่เคยเจอมา
        }
        return maxSum;
    }

    public int maxSubArray2(int[] nums){
        int max = Integer.MIN_VALUE;
        int curr_sum = Integer.MIN_VALUE;
        for(int i = 0; i < nums.length; i++){
            if(curr_sum <= 0){
                curr_sum = nums[i];
            }
            else{
                curr_sum += nums[i];
            }
            max = max < curr_sum ? curr_sum : max;
        }
        return max;
    }
    public static void main(String[] args) {
        maximumSubArrays m = new maximumSubArrays();
        System.out.println(m.maxSubArray2(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
    }
}
