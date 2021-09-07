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
        for (int i = 0; i < neighbours.size(); ++i) {
            // If the edge already exists, then we update it then return.
            if (neighbours.get(i).a == to) {
                neighbours.get(i).b = weight;
                return;
            }
        }
        // If edge does not exist, we add it.
        ++this.e;
        neighbours.add(new Edge(to, weight));
    }
}

// Edge class stores the edge, including its weight.
// a is the vertex to connect to, and b is the weight of the edge.
class Edge {
    public int a;
    public int b;

    public Edge(int a, int b) {
        this.a = a;
        this.b = b;
    }
}