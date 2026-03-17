# 📍 Dijkstra's Shortest Path — Custom Binary Min-Heap
### ECE 360C: Algorithms | University of Texas at Austin

Implementation of **Dijkstra's single-source shortest path algorithm** backed by a hand-rolled **binary min-heap** — no `PriorityQueue`, no standard library shortcuts. Both the heap and the graph traversal are built from scratch in Java.

---

## 📋 Overview

Two independent components, each worth half the assignment:

| Component | File | Complexity |
|---|---|---|
| Binary min-heap | `MinHeap.java` | Build: O(n), Insert/ExtractMin: O(log n) |
| Dijkstra's algorithm | `Dijkstra.java` | O((V + E) log V) |

---

## 🗂️ Files

| File | Description | Modified? |
|---|---|---|
| `MinHeap.java` | Binary min-heap backed by `ArrayList<KVPair>` | ✅ Yes |
| `Dijkstra.java` | Shortest path implementation using `MinHeap` | ✅ Yes |
| `KVPair.java` | Key-value pair struct (key = distance, value = vertex) | ❌ Provided |
| `Graph.java` | Adjacency list graph representation | ❌ Provided |
| `HeapTest.java` | JUnit tests for heap correctness | ❌ Provided |
| `DijkstraTest.java` | JUnit tests for Dijkstra correctness | ❌ Provided |

---

## 🏗️ Implementation

### Part 1 — Binary Min-Heap (`MinHeap.java`)

Zero-indexed min-heap stored in an `ArrayList<KVPair>`. Comparisons are made on the `key` field of each `KVPair` (representing distance). The `value` field stores the vertex label.

**Parent/child index relationships (0-indexed)**
```
parent(i)     = (i - 1) / 2
leftChild(i)  = 2i + 1
rightChild(i) = 2i + 2
```

**Methods**

`buildHeap(KVPair[] data)` — O(n)
Converts an arbitrary array into a valid min-heap using Floyd's algorithm: starts at the last internal node `(n/2 - 1)` and calls `heapifyDown` toward the root. Avoids the O(n log n) cost of repeated insertion.

`insert(KVPair pair)` — O(log n)
Appends to the end of the ArrayList, then bubbles up via `heapifyUp` until the heap property is restored.

`extractMin()` — O(log n)
Swaps root with last element, removes last, then calls `heapifyDown` from the root. Returns the original minimum `KVPair`.

`heapifyUp(int index, ArrayList<KVPair> heap)` — O(log n)
Recursively swaps a node with its parent while its key is smaller than its parent's key.

`heapifyDown(int index, ArrayList<KVPair> heap)` — O(log n)
Recursively swaps a node with its smallest child while smaller than either child.

---

### Part 2 — Dijkstra's Algorithm (`Dijkstra.java`)

Uses a **lazy deletion** approach — instead of updating keys already in the heap (which would require a decrease-key operation), stale entries are simply skipped when extracted. This avoids needing a more complex indexed heap.

**Algorithm walkthrough**

```
result[source] = 0, all others = Integer.MAX_VALUE
fixed[v]       = false for all v
Insert (0, source) into heap

while heap is not empty:
    (d, j) = extractMin()
    if fixed[j]: skip          ← lazy deletion of stale entries
    fixed[j] = true
    for each neighbor (k, weight) of j:
        if not fixed[k] and result[j] + weight < result[k]:
            result[k] = result[j] + weight
            insert (result[k], k) into heap

return result
```

**Unreachable vertices** return `Integer.MAX_VALUE` in the result array, consistent with the convention that ∞ distance means no path exists.

---

## 🧪 Testing

JUnit tests are provided for both components.

**Heap tests** (`HeapTest.java`)
- `BuildHeapTest` — verifies heap property holds after `buildHeap`
- `InsertTest` — verifies heap property, size, and element presence after insertion
- `ExtractMinTest` — verifies correct minimum returned, heap property maintained, remaining elements intact

**Dijkstra test** (`DijkstraTest.java`)
- `testDijkstra1` — runs shortest path from vertex 1 on a 9-vertex weighted graph

Expected result from vertex 1:
```
Vertex:    0         1   2   3   4   5   6         7         8
Distance:  MAX_VALUE 0   7   9   20  20  11        MAX_VALUE MAX_VALUE
```
Vertices 0, 7, and 8 are unreachable from vertex 1.

**Run tests**
```bash
javac *.java
java -cp .:junit.jar org.junit.runner.JUnitCore HeapTest DijkstraTest
```

---

## 🔧 Build & Run

```bash
# Compile
javac *.java

# Run Dijkstra on a graph (via test harness or custom main)
java -cp . DijkstraTest
```

> Targets **Java 21**. Do not add package statements or extra imports.

---

## 💡 Key Concepts

**Floyd's buildHeap** — O(n) heap construction by heapifying down from the last internal node, exploiting the fact that most work happens near the leaves where subtrees are small.

**Lazy deletion in Dijkstra** — rather than maintaining a decrease-key operation (which requires an indexed/addressable heap), duplicate entries are inserted for updated distances and stale ones are discarded on extraction via the `fixed[]` array. Simpler to implement, correct in behavior, slightly higher memory usage.

**Adjacency list representation** — `Graph` stores neighbors as `ArrayList<LinkedList<Neighbor>>`, where each `Neighbor` holds a vertex label and edge weight. Efficient for sparse graphs typical in shortest-path problems.

---



---

*Part of a broader algorithms repository covering sorting, graph traversal, dynamic programming, and optimization problems.*
