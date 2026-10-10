class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];

        long operations = (long) k1 + k2;
        int maxDiff = 0;
        long totalDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            totalDiff += diff[i];
            maxDiff = Math.max(maxDiff, diff[i]);
        }

        if (totalDiff <= operations) {
            return 0L;
        }

        int low = 0;
        int high = maxDiff;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long needed = 0;

            for (int d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= operations) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int level = low;
        long used = 0;
        long answer = 0;
        long countAtLevel = 0;

        for (int d : diff) {
            if (d > level) {
                used += d - level;
            }

            int capped = Math.min(d, level);
            answer += (long) capped * capped;

            if (d >= level) {
                countAtLevel++;
            }
        }

        long remaining = operations - used;

        answer -= remaining * (2L * level - 1);

        return answer;
    }
}