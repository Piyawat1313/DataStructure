public class TrappingRainWater {
    public int trap(int[] height) {
        int left = 0;   //กำแพงฝั่งซ้าย
        int right = height.length - 1;  //กำแพงฝั่งขวา
        int leftMax = 0;    //กำแพงฝั่งซ้ายที่สูงที่สุด
        int rightMax = 0;   //กำแพงฝั่งขวาที่สูงที่สุด
        int total = 0;  //จำนวนน้ำที่ขังได้

        while (left < right) {
            // ถ้าปริมาณน้ำฝั่งซ้ายเตี้ยกว่าฝั่งขวา
            if(height[left] < height[right]){

                // หาค่าที่มากที่สุดของฝั่งซ้าย
                if(height[left] >= leftMax){
                    leftMax = height[left];
                }
                else{
                    // ถ้าน้อยกว่า leftMax แปลว่ากักเก็บน้ำได้
                    total += leftMax - height[left];
                }

                left++; //ขยับไปทางขวา
            }
            else{
                // หาปริมาณน้ำฝั่งขวาที่มากที่สุด
                if(height[right] >= rightMax){
                    rightMax = height[right];
                }
                else{
                    total += rightMax - height[right];
                }
                right--;    //ขยับไปทางซ้าย
            }
        }

        return total;
    }

    public static void main(String[] args) {
        TrappingRainWater t = new TrappingRainWater();
        System.out.println(t.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
    }
}
