import java.util.*;

/*
 * This file defines the Graph and Neighbor classes which we will use for 
 * our adjacency list encoding the input to your shortest path algorithm
 * implementation. See DijkstraTest.java for how to construct a Graph
 * object.
 * 
 * The grader will overwrite this file, so any modifications you make will
 * be lost. DO NOT MODIFY THIS FILE.
 */
public class Graph
{
    ArrayList<LinkedList<Neighbor>> adjacencyList;

    public Graph(ArrayList<LinkedList<Neighbor>> adjacencyList)
    {
         this.adjacencyList = adjacencyList;
    }

    /*
     * Accepts a vertex label, and returns a LinkedList of the neighbors of this
     * vertex label.
     */
    public LinkedList<Neighbor> getNeighbors(int vertex)
    {
        try
        {
            return adjacencyList.get(vertex);
        }
        catch(ArrayIndexOutOfBoundsException exception)
        {
            throw new ArrayIndexOutOfBoundsException(String.format("Vertex %d not found.", vertex));
        }
    }

    /*
     * Returns the number of vertices in the graph represented by this
     * adjacency list.
     */
    public int numVertices()
    {
        return adjacencyList.size();
    }
}

/*
 * This class is a struct storing a vertex label and an edge weight. It is
 * the datatype used in our specification of an adjacency list.
 */
class Neighbor
{
    int vertex;
    int edgeWeight;

    public Neighbor(int vertex, int edgeWeight)
    {
        this.vertex = vertex;
        this.edgeWeight = edgeWeight;
    }
}