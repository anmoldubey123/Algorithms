import java.util.ArrayList;
import java.util.Arrays;

public class MinHeap 
{
    /*
     * Please use this field as your heap. Please zero index your heap.
     * Do not remove/change the name of this field.
     */
    ArrayList<KVPair> heap;

    /*
     * A helper function to use for heapify up. Takes the index of the key-value
     * pair in the this.heap ArrayList to begin the operation at. This method
     * will not be tested. Your implementation should be O(log n) time.
     */
    private static void heapifyUp(int index, ArrayList<KVPair> heap)
    {
        if(index>0)
        {
            int parent = (index-1)/2;
            if(heap.get(index).key < heap.get(parent).key)
            {
                KVPair temp = heap.get(index);
                heap.set(index, heap.get(parent));
                heap.set(parent, temp);

                heapifyUp(parent, heap);
            }
        }
    }

    /*
     * A helper function to use for heapify down. Takes the index of the key-value
     * pair in the this.heap ArrayList to begin the operation at. This method
     * will not be tested. Your implementation should be O(log n) time.
     */
    private static void heapifyDown(int index, ArrayList<KVPair> heap)
    {
        int n = heap.size();
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int minIndex = index;

        if (left < n && heap.get(left).key < heap.get(minIndex).key)
        {
            minIndex = left;
        }

        if (right < n && heap.get(right).key < heap.get(minIndex).key)
        {
            minIndex = right;
        }

        if (minIndex != index)
        {
            KVPair temp = heap.get(index);
            heap.set(index, heap.get(minIndex));
            heap.set(minIndex, temp);

            heapifyDown(minIndex, heap);
        }
    }

    /*
     * This method builds a binary min-heap from an array of key-value, and returns the
     * resulting structure as an ArrayList. This method is called in the constructor
     * of this class. Your implementation should be O(n) time.
     */
    private static ArrayList<KVPair> buildHeap(KVPair[] data)
    {
        ArrayList<KVPair> heap = new ArrayList<>(Arrays.asList(data));
        int n = heap.size();

        for (int i = (n / 2) - 1; i >= 0; i--)
        {
            heapifyDown(i, heap);
        }

        return heap;
    }

    /*
     * This method deletes the element of minimum key from the heap, and returns
     * its key and value. Your implementation should be O(log n) time.
     */
    public KVPair extractMin()
    {
        if (heap.size() == 0)
        {
            return null;
        }

        KVPair min = heap.get(0);
        int lastIndex = heap.size() - 1;

        heap.set(0, heap.get(lastIndex));
        heap.remove(lastIndex);

        if (heap.size() > 0)
        {
            heapifyDown(0, heap);
        }

        return min;
    }

    /*
     * This method inserts a key-value pair into the heap. Your implementation
     * should be O(log n) time.
     */
    public void insert(KVPair pair)
    {
        heap.add(pair);
        int index = heap.size() - 1;
        heapifyUp(index, heap);
    }
    
    /*
     * METHODS BEYOND THIS POINT ARE ALREADY IMPLEMENTED. PLEASE REVIEW THEIR USAGE 
     * BUT DO NOT MODIFY
     */

    /*
     * This constructor takes an array of key-value pairs and builds a min-heap from it, 
     * which is then saved into the heap field. This is already implemented for you.
     */
    public MinHeap(KVPair[] data)
    {
        this.heap = MinHeap.buildHeap(data);
    }

    /*
     * This method returns a reference to the arraylist storing the heap
     */
    public ArrayList<KVPair> getHeap()
    {
        return this.heap;
    }
}
