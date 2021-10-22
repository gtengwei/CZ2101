public class Main {

	public static void main(String[] args) {
		int[] weight = {4,6,8};
	    int[] profit = {7,6,9};
		
		Knapsack_recursive recur = new Knapsack_recursive();
	    
		System.out.println(recur.P(14,weight.length,weight,profit));
	}

}