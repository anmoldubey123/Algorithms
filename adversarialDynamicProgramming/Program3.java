public class Program3 {

    public int maxCoinValue(int[] cities) {
        int n = cities.length;

        if (n <= 1) {
            return 0;
        }

        // Prefix sums
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + cities[i];
        }

        // dp[i][j] = maximum coins player can guarantee from cities i...j
        int[][] dp = new int[n][n];


        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                int best = 0;

                // Try every possible wall position between k and k+1
                for (int k = i; k < j; k++) {
                    int leftSum = getSum(prefix, i, k);
                    int rightSum = getSum(prefix, k + 1, j);

                    int keepLeft = leftSum + dp[i][k];
                    int keepRight = rightSum + dp[k + 1][j];

                    // Enemy chooses the worst outcome for the player
                    int worstCase = Math.min(keepLeft, keepRight);

                    // Player chooses the best wall placement
                    best = Math.max(best, worstCase);
                }

                dp[i][j] = best;
            }
        }

        return dp[0][n - 1];
    }

    private int getSum(int[] prefix, int left, int right) {
        return prefix[right + 1] - prefix[left];
    }
}