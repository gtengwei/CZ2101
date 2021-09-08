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

    // This method performs Dijkstra's Algorithm on the graph,
    // and returns an array of weights from the source to all nodes.
    // The algorithm uses an adjacency list and a min-heap for the priority queue.
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
        PriorityQueue<PathWeight> q = new PriorityQueue<>(v);
        // Add the source into the queue.
        q.add(new PathWeight(0, 0));

        // While there are still unvisited nodes.
        while (visited.size() != v) {
            // Get the next node to be checked.
            PathWeight e = q.poll();

            // If returned node is null, means no more item in the queue.
            if (e == null) {
                break;
            }

            if (visited.contains(e.v)) {
                continue;
            }
            visited.add(e.v);
            // Loop through the neighbours for this node.
            Iterator<Edge> iter = adjList.get(e.v).iterator();
            while (iter.hasNext()) {
                Edge neigh = iter.next();
                // Calculate the new weight
                int new_weight = weights[e.v] + neigh.weight;

                // If the node has not been visited before, and has a new weight that is
                // smaller than the current weight to travel to that node
                if (!visited.contains(neigh.to) && new_weight < weights[neigh.to]) {
                    // We update the weight
                    weights[neigh.to] = new_weight;
                    // Then we add the new weight into the queue.
                    q.add(new PathWeight(neigh.to, new_weight));
                }
            }
        }
        return weights;
    }
}


// PathWeight stores the vertex, and the weight of travelling there.
// This is used to store the current weights to a particular vertex
// for the priority queue.
class PathWeight implements Comparable<PathWeight> {
    public int v;
    public int weight;

    public PathWeight(int v, int weight) {
        this.v = v;
        this.weight = weight;
    }

    @Override
    public int compareTo(PathWeight e) {
        // If current weight is more, then it returns +ve value (greater than).
        // If both weight same, returns 0 (equal to).
        // If current weight is less, then it return -ve value (lesser than).
        return Integer.compare(weight, e.weight);
    }
}


// Edge class stores the edge, including its weight.
// a is the vertex to connect to, and b is the weight of the edge.
// This is used for the adjacency list.
class Edge {
    public int to;
    public int weight;

    public Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}