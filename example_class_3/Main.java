public class Main {
    public static void main(String[] args) {
        int[] weight1 = {4, 6, 8};
        int[] profit = {7, 6, 9};

        int[] weight2 = {5, 6, 8};

        KnapsackRecursive recur = new KnapsackRecursive();
        KnapsackDynamic dyna = new KnapsackDynamic();

        System.out.print("The weights for the objects are: ");
        for (int i = 0; i < profit.length; i++) {
            System.out.print(weight1[i] + " ");
        }
        System.out.println();

        System.out.print("The profits for the objects are: ");
        for (int j : profit) {
            System.out.print(j + " ");
        }
        System.out.println();

        System.out.println(
                "The maximum profit for recursive programming is: " + recur.P(14, weight1.length, weight1, profit));
        System.out.println(
                "The maximum profit for dynamic programming is: " + dyna.knapsack(14, weight1.length, weight1, profit));
        System.out.println();

        System.out.print("The weights for the objects are: ");
        for (int i = 0; i < profit.length; i++) {
            System.out.print(weight2[i] + " ");
        }
        System.out.println();

        System.out.print("The profits for the objects are: ");
        for (int j : profit) {
            System.out.print(j + " ");
        }
        System.out.println();

        System.out.println(
                "The maximum profit for recursive programming is: " + recur.P(14, weight2.length, weight2, profit));
        System.out.println(
                "The maximum profit for dynamic programming is: " + dyna.knapsack(14, weight2.length, weight2, profit));
    }
}