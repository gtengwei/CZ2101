class Knapsack_recursive {
    public int row;
    public int col;
    public int C;

    public Knapsack_recursive() {
    }

    public int P(int C, int n, int[] weight, int[] profit) { // j = number of objects
        int index = n - 1;
        if (C == 0 || n == 0) {
            return 0;
        }

        if (weight[index] > C) {
            return P(C, index, weight, profit);
        } else {
            return Math.max(Math.max(P(C, index, weight, profit), profit[index] + P(C - weight[index], index, weight, profit)),
                    Math.max(profit[index] + P(C - weight[index], index, weight, profit), profit[index] + P(C - weight[index], index + 1, weight, profit)));
        }
    }
}


