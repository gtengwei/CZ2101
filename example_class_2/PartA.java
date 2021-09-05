import java.util.*;

class AGraph {
    // This is a graph implementation that is used for Part A
    // The graph is represented as an array of arrays.
    // The Dijsktra's Algorithm used in this part is an array.
    private final int[][] matrix;
    private final int size;

    // Initialise a graph with a given size.
    public AGraph(int size) {
        matrix = new int[size][size];
        this.size = size;
    }

    // Initialise a graph with a given graph.
    // This method assumes that the given graph is a valid square matrix.
    public AGraph(int[][] graph) {
        matrix = graph;
        this.size = graph.length;
    }

    // Add an edge to the graph. If it already exists, then override it.
    public void addEdge(int from, int to, int weight) {
        matrix[from][to] = weight;
    }

    // This function returns the smallest node from the array priority queue.
    public int getMinimum(int[] weights, Set<Integer> visited) {
        int min_index = 0;
        int min_value = Integer.MAX_VALUE;
        for (int i = 0; i < size; ++i) {
            if (!visited.contains(i) && weights[i] < min_value) {
                min_index = i;
                min_value = weights[i];
            }
        }
        return min_index;
    }

    // This method performs Dijkstra's Algorithm on the graph,
    // and returns an array of weights from the source to all nodes.
    public int[] performDijkstra(int source) {
        // Create all necessary data structures to perform this algorithm.
        Set<Integer> visited = new HashSet<>(); // This set stores all visited node indices.
        int[] weights = new int[size]; // Stores the weights.
        for (int i = 0; i < size; ++i) {
            weights[i] = Integer.MAX_VALUE;
        }

        // Set source weight to 0.
        weights[source] = 0;

        // In this part, the array is used as the priority queue.
        while (visited.size() != size) {
            // Get the next minimum node
            int node = this.getMinimum(weights, visited);
            visited.add(node);
            // Loop through the neighbours for this node
            for (int n_index = 0; n_index < size; ++n_index) {
                // Calculate the new weight
                int new_weight = weights[node] + matrix[node][n_index];
                // If the node is a neighbour,
                // has not been visited before,
                // and as a new weight that is smaller than the current one
                if (matrix[node][n_index] != 0 && !visited.contains(n_index) && new_weight < weights[n_index]) {
                    // Update the new weight.
                    weights[n_index] = new_weight;
                }
            }
        }
        return weights;
    }
}