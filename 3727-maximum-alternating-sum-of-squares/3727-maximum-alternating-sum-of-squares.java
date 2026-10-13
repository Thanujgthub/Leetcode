import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long[] squares = new long[n];

        // Calculate the square of every element
        for (int i = 0; i < n; i++) {
            squares[i] = (long) nums[i] * nums[i];
        }

        // Sort squared values in ascending order
        Arrays.sort(squares);

        long ans = 0;
        int mid = n / 2;

        // Subtract the smallest half
        for (int i = 0; i < mid; i++) {
            ans -= squares[i];
        }

        // Add the largest half
        for (int i = mid; i < n; i++) {
            ans += squares[i];
        }

        return ans;
    }
}