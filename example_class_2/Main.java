import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static final int MAX_SIZE = 100;
    private static final int MAX_ITERATION = 1000;

    public static void main(String[] args) {
        /*
        We know that from complexity.md, the timings for the algorithms
        are somewhat impacted by:
        1. V, the number of vertices.
        2. E, the number of edges in the graph.

        Hence, when timing the code, we have to find a way to vary these 2 variables.
        */
        int iteration, size;
        long total = 0;

        // Header of CSV file
        StringBuilder sb = new StringBuilder();
        sb.append("Size,");
        sb.append("Time");
        sb.append('\n');

        try (PrintWriter writer = new PrintWriter("result.csv")) {
            // start from size 2
            for (size = 2; size < MAX_SIZE; size++) {

                // Determine range of weight of each edge
                int min = 0;
                int max = 15;

                // Initialise empty graph
                int[][] randomWeightedGraph = new int[size][size];

                // Create a test graph.
                /*
                 * int[][] graph = { { 0, 4, 0, 0, 0, 0, 0, 8, 0 }, { 4, 0, 8, 0, 0, 0, 0, 11, 0
                 * }, { 0, 8, 0, 7, 0, 4, 0, 0, 2 }, { 0, 0, 7, 0, 9, 14, 0, 0, 0 }, { 0, 0, 0,
                 * 9, 0, 10, 0, 0, 0 }, { 0, 0, 4, 14, 10, 0, 2, 0, 0 }, { 0, 0, 0, 0, 0, 2, 0,
                 * 1, 6 }, { 8, 11, 0, 0, 0, 0, 1, 0, 7 }, { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
                 */

                // Insert random weight for each edge, within the range of min,max
                for (int i = 0; i < randomWeightedGraph.length; i++) {
                    for (int j = 0; j < randomWeightedGraph.length; j++) {
                        int weight = ThreadLocalRandom.current().nextInt(min, max);
                        randomWeightedGraph[i][j] = weight;
                    }

                }

                // Run iteration to get average running time
                for (iteration = 0; iteration < MAX_ITERATION; iteration++) {

                    // Start
                    long start = System.nanoTime();
                    AGraph a_graph = new AGraph(randomWeightedGraph);
                    int[] result = a_graph.performDijkstra(0);

                    // End
                    long end = System.nanoTime();

                    System.out.println("Distance from Source");
                    for (int i = 0; i < randomWeightedGraph.length; ++i) {
                        System.out.printf("%d t %d\n", i, result[i]);
                    }

                    // Add up total running time for MAXITERATIONS in nanaseconds
                    total += (end - start);
                    System.out.println("Time taken: " + (end - start) + " nanoseconds");

                } // for MAXITERATION

                // Append size of matrix to CSV
                sb.append(size);
                sb.append(',');

                // Append average running time for each size
                sb.append(total / MAX_ITERATION);
                sb.append('\n');
                System.out.println("done!");

            } // for maxSize
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
