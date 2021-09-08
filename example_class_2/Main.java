import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class Main {
    private static final int MAX_SIZE = 70;
    private static final int MAX_ITERATION = 10;
    private static final int MAX_WEIGHT = 1000;

    // https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
    private static final int[][] testGraph = { { 0, 4, 0, 0, 0, 0, 0, 8, 0 }, { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
            { 0, 8, 0, 7, 0, 4, 0, 0, 2 }, { 0, 0, 7, 0, 9, 14, 0, 0, 0 }, { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
            { 0, 0, 4, 14, 10, 0, 2, 0, 0 }, { 0, 0, 0, 0, 0, 2, 0, 1, 6 }, { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
            { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };

    public static void main(String[] args) {
        generateTimings();
    }

    public static void generateTimings() {
        /*
         * We know that from complexity.md, the timings for the algorithms are somewhat
         * impacted by: 1. V, the number of vertices. 2. E, the number of edges in the
         * graph.
         * 
         * Hence, when timing the code, we have to find a way to vary these 2 variables.
         */

        // We iterate through different sizes for the graph.

        // Write CSV results.
        StringBuilder builder = new StringBuilder();
        // Columns.
        builder.append("type,v,e,time\n");

        for (int v_size = 2; v_size < MAX_SIZE; ++v_size) {
            System.out.printf("Testing size %d\n", v_size);

            // Create the graphs of the appropriate size.
            AGraph aGraph = new AGraph(v_size);
            BGraph bGraph = new BGraph(v_size);
            

            // Store a list of all possible edges in the graph.
            List<Pair> edges = new ArrayList<>();
            for (int i = 0; i < v_size; ++i) {
                for (int j = 0; j < v_size; ++j) {
                    // Do not add an edge to oneself.
                    if (i == j) {
                        continue;
                    }
                    edges.add(new Pair(i, j));
                }
            }
            // Shuffle the array to poll random edges from the graph.
            Collections.shuffle(edges);

            // For each pair, add it to the graph, then time Dijkstra's Algorithm.
            for (Pair e: edges) {
                // Get a random weight for the edge.
                int weight = ThreadLocalRandom.current().nextInt(1, MAX_WEIGHT);
                aGraph.addEdge(e.from, e.to, weight);
                bGraph.addEdge(e.from, e.to, weight);

                // Then time for each graph type.
                long[] timings = new long[4];
                for (int iter = 0; iter < MAX_ITERATION; ++iter) {
                    timings[0] += System.nanoTime();
                    int[] resA = aGraph.performDijkstra(0);
                    timings[1] += System.nanoTime();

                    timings[2] += System.nanoTime();
                    int[] resB = bGraph.performDijkstra(0);
                    timings[3] += System.nanoTime();
                    for (int i = 0; i < v_size; ++i) {  
                        assert(resA[i] == resB[i]);
                    }
                }
                builder.append(String.format("%s,%d,%d,%f\n", "matrix", aGraph.v, aGraph.e, (double)(timings[1]-timings[0])/MAX_ITERATION));
                builder.append(String.format("%s,%d,%d,%f\n", "list", bGraph.v, bGraph.e, (double)(timings[3]-timings[2])/MAX_ITERATION));
            }
        }

        try (PrintWriter writer = new PrintWriter("results.csv")) {
            writer.write(builder.toString());
        } catch (IOException e) {
            System.out.printf("Error writing file: %s", e.toString());
        }
    }

    public static void generateTimings_old() {
        /*
         * We know that from complexity.md, the timings for the algorithms are somewhat
         * impacted by: 1. V, the number of vertices. 2. E, the number of edges in the
         * graph.
         * 
         * Hence, when timing the code, we have to find a way to vary these 2 variables.
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

    // Tests the implementation of the AGraph.
    public static void testAGraph() {
        AGraph a = new AGraph(testGraph);
        int[] res = a.performDijkstra(0);
        
        System.out.println("Distance from Source");
        for(int i = 0; i < testGraph.length; ++i) {
            System.out.printf("%d t %d\n", i, res[i]);
        }
    }

    public static void testBGraph() {
        // Create the adjacency list for the B graph.
        List<LinkedList<Edge>> adjList = new ArrayList<>();
        for (int i = 0; i < testGraph.length; ++i) {
            adjList.add(new LinkedList<>());
        }

        for (int row = 0; row < testGraph.length; ++row) {
            for (int col = 0; col < testGraph[row].length; ++col) {
                if (testGraph[row][col] == 0) {
                    continue;
                }
                adjList.get(row).add(new Edge(col, testGraph[row][col]));
            }
        }

        BGraph b = new BGraph(adjList);
        int[] res = b.performDijkstra(0);

        System.out.println("Distance from Source");
        for (int i = 0; i < testGraph.length; ++i) {
            System.out.printf("%d t %d\n", i, res[i]);
        }
    }
}

// This is used to store a possible edge between 2 vertices.
class Pair {
    public int from;
    public int to;

    public Pair(int from, int to) {
        this.from = from;
        this.to = to;
    }
}
