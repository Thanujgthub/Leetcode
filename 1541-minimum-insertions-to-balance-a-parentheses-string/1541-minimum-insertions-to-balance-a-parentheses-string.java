class Solution {
    public int minInsertions(String s) {
        int insertions = 0;
        int need = 0;

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                // An opening '(' requires two consecutive ')'
                if (need % 2 == 1) {
                    insertions++;
                    need--;
                }

                need += 2;
            } else {
                need--;

                // We have an unmatched ')', so insert '('
                if (need < 0) {
                    insertions++;
                    need = 1;
                }
            }
        }

        // Insert any missing closing parentheses
        return insertions + need;
    }
}