import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class Main {
    private static final int MAX_SIZE = 80;
    private static final int MAX_ITERATION = 90;
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
                    aGraph.performDijkstra(0);
                    timings[1] += System.nanoTime();

                    timings[2] += System.nanoTime();
                    bGraph.performDijkstra(0);
                    timings[3] += System.nanoTime();
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
