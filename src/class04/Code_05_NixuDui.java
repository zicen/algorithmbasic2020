package class04;

public class Code_05_NixuDui {
    // 剑指 offer 51
    class Solution {
        int res = 0;

        public int reversePairs(int[] nums) {
            if (nums == null || nums.length < 2) {
                return 0;
            }
            mergeSort(nums, 0, nums.length - 1);
            return res;
        }

        public void mergeSort(int[] nums, int left, int right) {
            if (left >= right) return;
            int mid = left + (right - left) / 2;
            mergeSort(nums, left, mid);
            mergeSort(nums, mid + 1, right);
            merge(nums, left, mid, right);
        }

        public void merge(int[] nums, int left, int mid, int right) {
            int[] help = new int[right - left + 1];
            int p1 = left;
            int p2 = mid + 1;
            int helpIndex = 0;
            while (p1 <= mid && p2 <= right) {
                if (nums[p1] > nums[p2]) {
                    //符合逆序对
                    res += (mid - p1 + 1);
                    help[helpIndex++] = nums[p2++];
                } else {
                    help[helpIndex++] = nums[p1++];
                }
            }
            while (p1 <= mid) {
                help[helpIndex++] = nums[p1++];
            }
            while (p2 <= right) {
                help[helpIndex++] = nums[p2++];
            }
            for (int i = 0; i < help.length; i++) {
                nums[i + left] = help[i];
            }
        }
    }
}
