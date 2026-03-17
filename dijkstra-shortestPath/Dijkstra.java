import java.util.*;

public class Dijkstra 
{
    /*
     * Implement Dijkstra's shortest path algorithm for undirected graphs
     * using your min-heap implementation. Follow the psuedo-code given
     * in the Ch. 5, page 30, handout on canvas so that you don't have to
     * update keys of elements in your heap.
     * 
     * This method takes a weighted graph specified in an adjacency list
     * (see Graph.java) and an integer corresponding to the source vertex.
     * We assume that each vertex is labelled by an integer from 0 to n-1,
     * when there are n vertices in the graph. 
     * 
     * This method returns an array of the shortest path distance from the 
     * source vertex to every other vertex in the graph. This is to say, 
     * the i^th entry in your returned array is the shortest path distance 
     * from the source vertex to vertex i. This means that if your return an 
     * array named "result," the entry result[source] should always equal 0.
     * Finally, if there exists no path between the source vertex and
     * vertex i, then the shortest path distance between these two vertices
     * is infinity. In this case, the returned array "result" should have
     * result[i] = Integer.MAX_VALUE
     */
    public static int[] shortestPath(Graph graph, int source)
    {
        int[] result = new int[graph.numVertices()];
        boolean[] fixed = new boolean[graph.numVertices()];

        Arrays.fill(result, Integer.MAX_VALUE);
        result[source] = 0;

        MinHeap H = new MinHeap(new KVPair[0]);
        H.insert(new KVPair(0, source));

        while (H.getHeap().size() > 0)
        {
            KVPair current = H.extractMin();
            int j = current.value;
            int d = current.key;

            if (fixed[j])
            {
                continue;
            }

            fixed[j] = true;

            for (Neighbor neighbor : graph.getNeighbors(j))
            {
                int k = neighbor.vertex;
                int weight = neighbor.edgeWeight;

                if (!fixed[k] && result[j] != Integer.MAX_VALUE &&
                        result[k] > result[j] + weight)
                {
                    result[k] = result[j] + weight;
                    H.insert(new KVPair(result[k], k));
                }
            }
        }

        return result;

    }
}