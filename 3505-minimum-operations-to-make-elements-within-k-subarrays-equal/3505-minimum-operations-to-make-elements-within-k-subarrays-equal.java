
import java.util.*;

class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] cost = new long[n - x + 1];

        MedianWindow window = new MedianWindow();

        for (int i = 0; i < x; i++) {
            window.add(nums[i]);
        }
        cost[0] = window.getCost();

        for (int start = 1; start <= n - x; start++) {
            window.remove(nums[start - 1]);
            window.add(nums[start + x - 1]);
            cost[start] = window.getCost();
        }

        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];

        for (int j = 1; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= n; i++) {
                // Skip nums[i - 1].
                dp[j][i] = dp[j][i - 1];

                // Select the window ending at i - 1.
                if (i >= x && dp[j - 1][i - x] != INF) {
                    long take = dp[j - 1][i - x] + cost[i - x];
                    dp[j][i] = Math.min(dp[j][i], take);
                }
            }
        }

        return dp[k][n];
    }

    private static class MedianWindow {
        private final TreeMap<Integer, Integer> lower = new TreeMap<>();
        private final TreeMap<Integer, Integer> upper = new TreeMap<>();

        private int lowerSize = 0;
        private int upperSize = 0;
        private long lowerSum = 0;
        private long upperSum = 0;

        void add(int value) {
            if (lower.isEmpty() || value <= lower.lastKey()) {
                addTo(lower, value);
                lowerSize++;
                lowerSum += value;
            } else {
                addTo(upper, value);
                upperSize++;
                upperSum += value;
            }
            rebalance();
        }

        void remove(int value) {
            if (lower.containsKey(value)) {
                removeFrom(lower, value);
                lowerSize--;
                lowerSum -= value;
            } else {
                removeFrom(upper, value);
                upperSize--;
                upperSum -= value;
            }
            rebalance();
        }

        long getCost() {
            long median = lower.lastKey();
            return median * lowerSize - lowerSum
                 + upperSum - median * upperSize;
        }

        private void rebalance() {
            int targetLowerSize = (lowerSize + upperSize + 1) / 2;

            while (lowerSize > targetLowerSize) {
                int value = lower.lastKey();
                removeFrom(lower, value);
                lowerSize--;
                lowerSum -= value;

                addTo(upper, value);
                upperSize++;
                upperSum += value;
            }

            while (lowerSize < targetLowerSize) {
                int value = upper.firstKey();
                removeFrom(upper, value);
                upperSize--;
                upperSum -= value;

                addTo(lower, value);
                lowerSize++;
                lowerSum += value;
            }
        }

        private void addTo(TreeMap<Integer, Integer> map, int value) {
            map.merge(value, 1, Integer::sum);
        }

        private void removeFrom(TreeMap<Integer, Integer> map, int value) {
            int count = map.get(value);
            if (count == 1) {
                map.remove(value);
            } else {
                map.put(value, count - 1);
            }
        }
    }
}
