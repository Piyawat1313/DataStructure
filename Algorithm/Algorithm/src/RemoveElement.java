public class RemoveElement {
    public int removeElement(int[] nums, int val) {
        if(nums.length == 0) return 0;
        int removeVal = 0;
        for (int i = 0; i < nums.length; i++) {
            // เช็คว่าไม่ซ้ำเพื่อทำการเขียนทับ
            if(nums[i] != val){
                nums[removeVal++] = nums[i];
            }
        }
        return removeVal;
    }
    public static void main(String[] args) {
        RemoveElement r = new RemoveElement();
        System.out.println(r.removeElement(new int[]{0,1,2,2,3,0,4,2}, 2));
        System.out.println(r.removeElement(new int[]{3,2,2,3}, 3));
    }
}
