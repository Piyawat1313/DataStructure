import java.util.ArrayList;
import java.util.Collections;
public class RemoveDuplicatesFromSortedArray {
    public int removeDuplicates(int[] nums) {
        ArrayList<Integer>list = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            list.add(nums[i]);
        }
        Collections.sort(list);
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                if(list.get(i).equals(list.get(j))){
                    list.remove(list.get(j));
                }
            }
        }
        System.out.println(list.toString());
        return list.size();
    }

    public int removeDuplicates2(int[] nums) {
        if(nums.length == 0){
            return 0;
        }
        int insertIndex = 1;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] != nums[i - 1]){
                nums[insertIndex] = nums[i];
                insertIndex++;
            }
        }
        return insertIndex;
    }
    public static void main(String[] args) {
        RemoveDuplicatesFromSortedArray r = new RemoveDuplicatesFromSortedArray();
        // System.out.println(r.removeDuplicates(new int[]{0,0,1,1,1,2,2,3,3,4}));
        System.out.println(r.removeDuplicates2(new int[]{1,1,2}));
    }
}
