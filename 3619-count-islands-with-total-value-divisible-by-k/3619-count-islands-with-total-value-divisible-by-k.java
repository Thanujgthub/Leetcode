
class Solution {
    public int countIslands(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // Each cell is pushed at most once.
        int[] stack = new int[m * n];
        int answer = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 0) {
                    continue;
                }

                int top = 0;
                long sum = 0;

                stack[top++] = r * n + c;
                sum += grid[r][c];
                grid[r][c] = 0; // Mark visited immediately.

                while (top > 0) {
                    int cell = stack[--top];
                    int row = cell / n;
                    int col = cell % n;

                    for (int d = 0; d < 4; d++) {
                        int nr = row + dr[d];
                        int nc = col + dc[d];

                        if (nr >= 0 && nr < m &&
                            nc >= 0 && nc < n &&
                            grid[nr][nc] > 0) {

                            sum += grid[nr][nc];
                            grid[nr][nc] = 0;

                            stack[top++] = nr * n + nc;
                        }
                    }
                }

                if (sum % k == 0) {
                    answer++;
                }
            }
        }

        return answer;
    }
}
