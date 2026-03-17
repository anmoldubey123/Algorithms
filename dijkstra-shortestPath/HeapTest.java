import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class HeapTest
{
    @Test
    public void BuildHeapTest()
    {
        KVPair[] data = new KVPair[]{new KVPair(1,1), new KVPair(8,2), new KVPair(-5, -5), new KVPair(6,6), new KVPair(2,3), new KVPair(3,3), new KVPair(0,7)};

        MinHeap heap = new MinHeap(data);

        // Does the heap still possess the heap property?
        assertTrue(isHeap(heap.getHeap()));
    }

    @Test
    public void InsertTest()
    {
        // The following already has the heap property
        KVPair[] data = new KVPair[]{new KVPair(-5, -5), new KVPair(2,2), new KVPair(1,1), new KVPair(6,6), new KVPair(2,3), new KVPair(3,3), new KVPair(7,7)}; 

        MinHeap heap = new MinHeap(data);
        heap.insert(new KVPair(-1,4));

        // Does heap still possess the heap property?
        assertTrue(isHeap(heap.getHeap()));

        // Does the heap contain one more key value pair?
        assertTrue(heap.getHeap().size() == 8);

        // Are all the elements in data still in the heap?
        assertTrue(isSubset(data, heap.getHeap()));
       
        // Did the inserted pair actually make it into the heap?
        Boolean result = false;
        for(KVPair pair: heap.getHeap())
        {
            if(pair.equals(new KVPair(-1,4)))
            {
                result = true;
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    public void ExtractMinTest()
    {
        KVPair[] data = new KVPair[]{new KVPair(1,1), new KVPair(2,2), new KVPair(-5, -5), new KVPair(6,6), new KVPair(2,3), new KVPair(3,3), new KVPair(7,7)};

        MinHeap heap = new MinHeap(data);
        KVPair min = heap.extractMin();

        // Was the extracted value correct?
        assertTrue(min.equals(new KVPair(-5,-5)));

        // Does heap still possess the heap property?
        assertTrue(isHeap(heap.getHeap()));

        // Are the rest of the key value pairs still in the heap?
        assertTrue(heap.getHeap().size() == 6);
        assertTrue(isSubset(new KVPair[]{new KVPair(1,1), new KVPair(2,2), new KVPair(6,6), new KVPair(2,3), new KVPair(3,3), new KVPair(7,7)}, heap.getHeap()));
    }
    
    public static Boolean isHeap(ArrayList<KVPair> heap)
    {
        Boolean failed = false;

        for(int i = 1; i < heap.size(); i++)
        {
            if(heap.get((i - 1) / 2).key > heap.get(i).key)
            {
                failed = true;
            }
        }

        return !failed;
    }

    public static Boolean isSubset(KVPair[] data, ArrayList<KVPair> heap)
    {
        Boolean[] failed = new Boolean[data.length];
        for(int i = 0; i < data.length; i++)
        {
            failed[i] = true;
        }

        for(int i = 0; i < data.length; i++)
        {
            for(KVPair pair: heap)
            {
                if(pair.equals(data[i]))
                {
                    failed[i] = false;
                    break;
                }
            }
        }

        Boolean result = true;
        for(Boolean fail: failed)
        {
            result = result && !fail;
        }

        return result;
    }
}