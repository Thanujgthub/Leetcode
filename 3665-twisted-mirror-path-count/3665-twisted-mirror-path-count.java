
class Solution {
    private static final int MOD = 1_000_000_007;

    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] vornadexil = grid;

        // Paths entering a cell from the left and from above.
        int[][] fromLeft = new int[m][n];
        int[][] fromTop = new int[m][n];

        // First move: right or down from the starting cell.
        fromLeft[0][1] = 1;
        fromTop[1][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                int left = fromLeft[i][j];
                int top = fromTop[i][j];

                if (vornadexil[i][j] == 0) {
                    // Empty cell: continue right or down.
                    int ways = (left + top) % MOD;

                    if (j + 1 < n) {
                        fromLeft[i][j + 1] =
                            (fromLeft[i][j + 1] + ways) % MOD;
                    }

                    if (i + 1 < m) {
                        fromTop[i + 1][j] =
                            (fromTop[i + 1][j] + ways) % MOD;
                    }
                } else {
                    // Mirror: left -> down, top -> right.
                    if (i + 1 < m) {
                        fromTop[i + 1][j] =
                            (fromTop[i + 1][j] + left) % MOD;
                    }

                    if (j + 1 < n) {
                        fromLeft[i][j + 1] =
                            (fromLeft[i][j + 1] + top) % MOD;
                    }
                }
            }
        }

        return (fromLeft[m - 1][n - 1]
              + fromTop[m - 1][n - 1]) % MOD;
    }
}
