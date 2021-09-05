public class Main {
    public static void main(String[] args) {
        // Create a test graph.
        int[][] graph = { { 0, 4, 0, 0, 0, 0, 0, 8, 0 }, { 4, 0, 8, 0, 0, 0, 0, 11, 0 }, { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
                { 0, 0, 7, 0, 9, 14, 0, 0, 0 }, { 0, 0, 0, 9, 0, 10, 0, 0, 0 }, { 0, 0, 4, 14, 10, 0, 2, 0, 0 },
                { 0, 0, 0, 0, 0, 2, 0, 1, 6 }, { 8, 11, 0, 0, 0, 0, 1, 0, 7 }, { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
        AGraph a_graph = new AGraph(graph.length, graph);

        int[] result = a_graph.performDijkstra(0);
        
        System.out.println("Distance from Source");
        for(int i = 0; i < graph.length; ++i) {
            System.out.printf("%d t %d\n", i, result[i]);
        }
    }
}
