import java.util.*;
class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> numsMap = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            int complement = target - nums[i];
            if(numsMap.containsKey(complement)){
                return new int[] {numsMap.get(complement), i};
            }
            numsMap.put(nums[i], i);
        }
        return new int[] {};
    }
    public static void test(){
        Solution s = new Solution();
        int arr1[] = {2,5,5,11};
        int target = 10;
        for (int num : s.twoSum(arr1, target)) {
            System.out.println(num);
        }
    }
}
public class Twosum {
    public static void main(String[] args) {
        Solution.test();
    }   
}
