# Time Complexity of Dijkstra's Algorithm

We assume that we are working on a graph G = (V, E).

Analysing the algorithm provides us with a few points of interest when analysing the time complexity:

1. Looping through each vertex at least once for the outer for-loop.
2. Implementation of the priority queue (arguably the most important!).
3. Looping through each vertex's neighbours.

## Part a)

For the first part, the algorithm is implemented using an `adjacency matrix` for the graph, and an `array` for the
priority queue.

The outer loop is where we loop through each vertex at least once. This takes `O(V)` time.

On each iteration, we call the priority queue to get the next vertex. Since the queue is implemented using an array, the
operation will take `O(V)` time.

Within this outer loop, we also have an inner loop looping through all the current vertex's neighbours. This
takes `O(V)` time for an adjacency matrix.

As such, each outer loop iteration takes `O(V+V)` time.

Therefore, the total time complexity is `O(V(V+V)) ~= O(V^2)`.

## Part b)

For the second part, the algorithm is implemented using an `adjacency list` for the graph, and a `min-heap` for the
priority queue.

The first thing we notice is that operations on the heap take `O(logn)` time, where `n` is the number of elements in the
heap.

Building the heap itself would take `O(V)` time (but as we will see, this will be dwarfed by the other operations).

Next, we see that like part a), we have to loop through each vertex at least once for the outer loop. This takes `O(V)`
time.

On each iteration, we have to remove a node from the heap, which is a `O(logV)` operation.

Then, we also have to loop through each neighbour of the current vertex.

**What is the time complexity of this inner loop operation?**

Notice that any useful work is done **only on an edge of the graph**. The useful work (which updates the heap with the
new weight), is a `O(logV)` operation.

As such, if we count all the number of times the inner loop runs, we will realise that we only do `O(ElogV)` operations
in total, regardless of the number of times the outer loop has to run.

Therefore, the total time complexity would be `O(VlogV + ElogV + V) ~= O((V + E)logV)`.

# When to use one implementation over the other?

a) `O(V^2)`
b) `O((V + E)logV)`

Imagine that our graph is close to being complete (each vertex is almost a neighbour of every other vertex), then the
complexity for the second implementation would degrade to
`O((V + V^2)logV)`. This is much worse than `O(V^2)` for the first implementation.

However, if our graph is sparse (not many edges), then we can see that the second implementation would win.