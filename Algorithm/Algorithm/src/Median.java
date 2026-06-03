import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Median {

    // อันนี้เร็วกว่า Time 6 sec
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < nums1.length; i++) {
            arr.add(nums1[i]);
        }
        for (int i = 0; i < nums2.length; i++) {
            arr.add(nums2[i]);
        }
        // median
        Collections.sort(arr);
        int median = 0;
        if (arr.size() % 2 != 0) {
            median = arr.size() / 2;
            return arr.get(median);
        } else {
            int left = arr.size() / 2; // ตัวตรงกลาง
            int right = left - 1; // ตัวที่อยู่คู่ก่อนหน้าตัวกลาง
            return (arr.get(left) + arr.get(right)) / 2.0;
        }
    }

    // Time 8 sec
    public double testMedianSpeed(int[] nums1, int[] nums2) {
        int[] merge = new int[nums1.length + nums2.length];

        System.arraycopy(nums1, 0, merge, 0, nums1.length);
        System.arraycopy(nums2, 0, merge, nums1.length, nums2.length);

        Arrays.sort(merge);
        if (merge.length % 2 != 0) {
            return merge[merge.length / 2];
        } else {
            int left = merge.length / 2;
            return (merge[left] + merge[left - 1]) / 2.0;
        }
    }

    public static void main(String[] args) {
        Median m = new Median();
        System.out.println(m.findMedianSortedArrays(new int[] { 1, 2 }, new int[] { 3, 4 }));
    }
}