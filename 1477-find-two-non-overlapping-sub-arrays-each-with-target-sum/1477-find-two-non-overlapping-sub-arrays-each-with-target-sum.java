import java.util.*;

class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = n + 1;

        int[] best = new int[n + 1];
        Arrays.fill(best, INF);

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);

        int prefix = 0;
        int ans = INF;

        for (int i = 1; i <= n; i++) {
            prefix += arr[i - 1];

            best[i] = best[i - 1];

            int needed = prefix - target;

            if (map.containsKey(needed)) {
                int j = map.get(needed);
                int len = i - j;

                if (best[j] != INF) {
                    ans = Math.min(ans, best[j] + len);
                }

                best[i] = Math.min(best[i], len);
            }

            map.put(prefix, i);
        }

        return ans == INF ? -1 : ans;
    }
}