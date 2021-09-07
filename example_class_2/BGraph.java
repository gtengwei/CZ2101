import java.util.*;

public class BGraph {
    // This is a graph implementation that is used for Part B.
    // The graph is represented as an array of linked lists.
    // The Dijkstra's Algorithm used in this part is a min-heap.
    private List<LinkedList<Edge>> adjList;
    public int v;
    public int e;

    // Initialise a graph with a given size v.
    public BGraph(int v) {
        adjList = new ArrayList<>();
        for (int i = 0; i < v; ++i) {
            adjList.add(new LinkedList<>());
        }
        this.v = v;
        this.e = 0;
    }

    // NOTE: For testing purposes only!
    // Does not update the E value.
    // Initialise a graph with a given adjacency list.
    public BGraph(List<LinkedList<Edge>> list) {
        adjList = list;
        this.v = list.size();
    }

    // Add an edge to the graph. If it already exists, then override it.
    public void addEdge(int from, int to, int weight) {
        LinkedList<Edge> neighbours = adjList.get(from);
        // Check current neighbours
        Iterator<Edge> iter = neighbours.iterator();
        while (iter.hasNext()) {
            Edge node = iter.next();
            if (node.to == to) {
                node.weight = weight;
                return;
            }
        }
        // If edge does not exist, we add it.
        ++this.e;
        neighbours.add(new Edge(to, weight));
    }

    public int[] performDijkstra(int source) {
        // Create all necessary data structures for this algorithm
        Set<Integer> visited = new HashSet<>(); // Stores all visited node indices.
        int[] weights = new int[v]; // Stores the weights.
        for (int i = 0; i < v; ++i) {
            weights[i] = Integer.MAX_VALUE;
        }

        // Set the source weight to 0
        weights[source] = 0;

        // Create the priority queue. In this part, we are using a min-heap.
        // Thankfully, Java already has a built-in PriorityQueue
        // that is implemented with a MinHeap!
        PriorityQueue<Edge> q = new PriorityQueue<>(v);
        // Add the elements into the queue.
        for (int i = 0; i < v; ++i) {
            q.add(new Edge(i, weights[i]));
        }

        throw new UnsupportedOperationException();
    }
}

// Edge class stores the edge, including its weight.
// a is the vertex to connect to, and b is the weight of the edge.
class Edge implements Comparable<Edge> {
    public int to;
    public int weight;

    public Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge e) {
        // If current edge is more, then it returns +ve value (greater than).
        // If both weight same, returns 0 (equal to).
        // If current edge is less, then it return -ve value (lesser than).
        return weight - e.weight;
    }
}