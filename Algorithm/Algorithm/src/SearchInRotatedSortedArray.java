public class SearchInRotatedSortedArray {
    public int search(int[] nums, int target) {
        if(nums.length == 0 ) return -1;

        int L = nums.length;
        int result = -1;
        for (int i = 0; i < L; i++) {
            if(nums[i] == target){
                result = i;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        SearchInRotatedSortedArray s = new SearchInRotatedSortedArray();
        System.out.println(s.search(new int[]{4,5,6,7,0,1,2}, 3));
        System.out.println(s.search(new int[]{1}, 0));
    }
}
