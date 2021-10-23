public class Knapsack_dynamic {
		
	public Knapsack_dynamic() {
		
	}
	
	public int knapsack(int C, int n, int[] weight, int[] profit) {
		int[][] dict = new int[C+1][n+1]; // Initialise 2D array for dictionary
		int r,c;
		
		for (r=1;r<=C;r++) { // Initialise first column to be 0
			dict[r][0] = 0;
		}
		
		for (c=0;c<=n;c++) { // Initialise first row to be 0
			dict[0][c] = 0;
		}
		
		
		
		for (r=1;r<=C;r++) { // r represents the capacity available (ie the rows of the 2D array)
			for (c=1;c<=n;c++) {  // c represents the index of the object( ie the columns of the 2D array)
				
				int index = c-1; // index for profit and weight arrays
				dict[r][c] = dict[r][c-1]; // Default weight
				
				if (weight[index] <= r) {  // If the current weight of object is less than the available capacity
					if (dict[r][c] < (dict[r-weight[index]][c-1] + profit[index])) { // if current profit is less than the profit for choosing the next object
						
						// Compare between the profit for choosing the next object and the current object
						dict[r][c] = Math.max(dict[r-weight[index]][c-1] + profit[index] , dict[r-weight[index]][c] + profit[index]);
					}
					
				}
			}
		}
//		for (int i=0;i<=C;i++) { // printing out the dictionary
//			for (int j=1;j<=n;j++) {
//				System.out.print("i = " + i + ' ');
//				System.out.print("j = " + j + ' ');
//				System.out.println(dict[i][j]);
//			}
//		}
		return dict[C][n];
	}
}