public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] weight = {4,6,8};
	    int[] profit = {7,6,9};
		
		Knapsack_recursive recur = new Knapsack_recursive();
	    
		System.out.println(recur.P(14,weight.length,weight,profit));
	}

}