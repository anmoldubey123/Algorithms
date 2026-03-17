import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class DijkstraTest
{   
    @Test
    public void testDijkstra1() {
        int source = 1;
        int[] result = Dijkstra.shortestPath(DijkstraTest.getTestGraph(), source);
        assertArrayEquals(result, SOURCE_EQ_1_SHORTEST_PATH_DISTANCES);
    }

    /*
     * Returns the adjacency list of the graph used in testDijkstra1
     */
    public static Graph getTestGraph()
    {
        ArrayList<LinkedList<Neighbor>> result = new ArrayList<LinkedList<Neighbor>>();

        result.add(new LinkedList<>(Arrays.asList(new Neighbor[]{new Neighbor(7, 1)}))); // vertex 0
        result.add(new LinkedList<>(Arrays.asList(new Neighbor[]{new Neighbor(2, 7), new Neighbor(3, 9), new Neighbor(6, 14)}))); // vertex 1
        result.add(new LinkedList<>(Arrays.asList(new Neighbor[]{new Neighbor(1, 7), new Neighbor(3, 10), new Neighbor(4, 15)}))); // vertex 2
        result.add(new LinkedList<>(Arrays.asList(new Neighbor[]{new Neighbor(1, 9), new Neighbor(2, 10), new Neighbor(4, 11), new Neighbor(6, 2)}))); // vertex 3
        result.add(new LinkedList<>(Arrays.asList(new Neighbor[]{new Neighbor(2, 15), new Neighbor(3, 11), new Neighbor(5,6)}))); // vertex 4
        result.add(new LinkedList<>(Arrays.asList(new Neighbor[]{new Neighbor(4, 6), new Neighbor(6, 9)}))); // vertex 5
        result.add(new LinkedList<>(Arrays.asList(new Neighbor[]{new Neighbor(1, 14), new Neighbor(3, 2), new Neighbor(5, 9)}))); // vertex 6
        result.add(new LinkedList<>(Arrays.asList(new Neighbor[]{new Neighbor(0,1), new Neighbor(8, 7)}))); // vertex 7
        result.add(new LinkedList<>(Arrays.asList(new Neighbor[]{new Neighbor(7, 7)})));
   
        return new Graph(result);
    }

    /*
     * Correct shortest path solution from vertex 1 on the graph given by getTestGraph()
     */
    final int[] SOURCE_EQ_1_SHORTEST_PATH_DISTANCES = {Integer.MAX_VALUE, 0, 7, 9, 20, 20, 11, Integer.MAX_VALUE, Integer.MAX_VALUE};
}