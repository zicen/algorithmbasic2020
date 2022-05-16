package class02;

public class OnceNumbser {
    public static void main(String[] args) {
        int[] arr = {2, 2, 3, 2, 2, 3, 3, 3, 5};
        int res = singleNumber(arr);
        System.out.println(res);
    }

    // 只出现一次的数字 https://leetcode-cn.com/problems/single-number-ii/
    public static int singleNumber(int[] nums) {
        int[] counts = new int[32];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            for (int j = 0; j < 32; j++) {
                counts[j] = num & 1;
                num >>= 1;
            }
        }

        int res = 0, m = 4;
        for (int i = 0; i < counts.length; i++) {
            res <<= 1;
            res |= counts[31 - i] % m;
        }
        return res;
    }
}
