import java.util.*;

class Solution {
    public int maxSum(List<Integer> nums, int k) {
        int MOD = 1_000_000_007;
        int[] count = new int[31];

        // Count the occurrences of each set bit
        for (int num : nums) {
            for (int bit = 0; bit < 31; bit++) {
                if (((num >> bit) & 1) == 1) {
                    count[bit]++;
                }
            }
        }

        long ans = 0;

        // Construct k numbers with the largest possible values
        for (int i = 0; i < k; i++) {
            int num = 0;

            for (int bit = 0; bit < 31; bit++) {
                if (count[bit] > 0) {
                    num |= (1 << bit);
                    count[bit]--;
                }
            }

            ans = (ans + 1L * num * num) % MOD;
        }

        return (int) ans;
    }
}