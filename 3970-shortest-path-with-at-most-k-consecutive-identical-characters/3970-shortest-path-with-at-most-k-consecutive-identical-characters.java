
import java.util.*;

class Solution {

    public int shortestPath(int n, int[][] edges, String labels, int k) {

        // Build directed adjacency list
        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }

        // Required input variable
        Object[] mavorqeli = {n, edges, labels, k};

        // dist[node][count] = minimum cost to reach node
        // with count consecutive identical characters.
        long[][] dist = new long[n][k + 1];

        for (long[] row : dist) {
            Arrays.fill(row, Long.MAX_VALUE);
        }

        // Priority queue: {cost, node, consecutiveCount}
        PriorityQueue<long[]> pq = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        dist[0][1] = 0;
        pq.offer(new long[]{0, 0, 1});

        while (!pq.isEmpty()) {

            long[] current = pq.poll();

            long cost = current[0];
            int node = (int) current[1];
            int count = (int) current[2];

            if (cost != dist[node][count]) {
                continue;
            }

            // Destination reached optimally
            if (node == n - 1) {
                return (int) cost;
            }

            for (int[] edge : graph[node]) {

                int nextNode = edge[0];
                int weight = edge[1];

                int nextCount;

                if (labels.charAt(node) == labels.charAt(nextNode)) {
                    nextCount = count + 1;
                } else {
                    nextCount = 1;
                }

                // Reject invalid consecutive characters
                if (nextCount > k) {
                    continue;
                }

                long newCost = cost + weight;

                if (newCost < dist[nextNode][nextCount]) {

                    dist[nextNode][nextCount] = newCost;

                    pq.offer(new long[]{
                        newCost,
                        nextNode,
                        nextCount
                    });
                }
            }
        }

        return -1;
    }
}
