class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > 9 * num) {
            return "";
        }

        StringBuilder ans = new StringBuilder();

        int nines = sum / 9;
        int remainder = sum % 9;

        // Place as many 9s as possible
        for (int i = 0; i < nines; i++) {
            ans.append('9');
        }

        // Place the remaining digit, if any
        if (remainder > 0) {
            ans.append((char) ('0' + remainder));
        }

        // Fill the remaining positions with zeros
        while (ans.length() < num) {
            ans.append('0');
        }

        return ans.toString();
    }
}