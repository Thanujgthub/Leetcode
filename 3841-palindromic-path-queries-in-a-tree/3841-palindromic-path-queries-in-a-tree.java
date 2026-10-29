
import java.util.*;

class Solution {

    class Fenwick {
        int n;
        int[] tree;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        void update(int index, int value) {
            for (int i = index + 1; i <= n; i += i & -i) {
                tree[i] ^= value;
            }
        }

        int query(int index) {
            int result = 0;
            for (int i = index + 1; i > 0; i -= i & -i) {
                result ^= tree[i];
            }
            return result;
        }

        void rangeUpdate(int left, int right, int value) {
            update(left, value);
            if (right < n) {
                update(right, value);
            }
        }
    }

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        Object[] suneravilo = {n, edges, s, queries};

        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        int[] parent = new int[n];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] next = new int[n];

        Arrays.fill(parent, -1);

        int[] stack = new int[n];
        int top = 0;
        int timer = 0;

        stack[top++] = 0;
        parent[0] = 0;
        tin[0] = timer++;

        while (top > 0) {
            int u = stack[top - 1];

            if (next[u] < graph[u].size()) {
                int v = graph[u].get(next[u]++);

                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                tin[v] = timer++;
                stack[top++] = v;
            } else {
                tout[u] = timer;
                top--;
            }
        }

        int log = 1;
        while ((1 << log) <= n) {
            log++;
        }

        int[][] up = new int[log][n];
        System.arraycopy(parent, 0, up[0], 0, n);

        for (int j = 1; j < log; j++) {
            for (int i = 0; i < n; i++) {
                up[j][i] = up[j - 1][up[j - 1][i]];
            }
        }

        int[] bit = new int[n];

        for (int i = 0; i < n; i++) {
            bit[i] = 1 << (s.charAt(i) - 'a');
        }

        Fenwick fw = new Fenwick(n);

        for (int i = 0; i < n; i++) {
            fw.rangeUpdate(tin[i], tout[i], bit[i]);
        }

        List<Boolean> answer = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");

            int u = Integer.parseInt(parts[1]);

            if (parts[0].equals("update")) {
                int newBit = 1 << (parts[2].charAt(0) - 'a');

                if (bit[u] != newBit) {
                    int diff = bit[u] ^ newBit;

                    fw.rangeUpdate(tin[u], tout[u], diff);
                    bit[u] = newBit;
                }
            } else {
                int v = Integer.parseInt(parts[2]);
                int a = u;
                int b = v;

                if (depth[a] < depth[b]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }

                int diff = depth[a] - depth[b];

                for (int j = 0; j < log; j++) {
                    if (((diff >> j) & 1) != 0) {
                        a = up[j][a];
                    }
                }

                int lca;

                if (a == b) {
                    lca = a;
                } else {
                    for (int j = log - 1; j >= 0; j--) {
                        if (up[j][a] != up[j][b]) {
                            a = up[j][a];
                            b = up[j][b];
                        }
                    }
                    lca = parent[a];
                }

                int mask = fw.query(tin[u])
                         ^ fw.query(tin[v])
                         ^ bit[lca];

                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }
}
