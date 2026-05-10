📐 Algorithms — ECE 360C
University of Texas at Austin
A collection of algorithm implementations from ECE 360C: Algorithms. Each subproject is built from scratch in Java — no standard library shortcuts for core data structures or algorithmic logic.

📦 Projects
#ProjectAlgorithmKey Data StructureComplexity1stableMatching-GaleShapley/Gale-Shapley stable matchingPreference rank tables, free queuesO(n · m)2dijkstra-shortestPath/Dijkstra's single-source shortest pathCustom binary min-heapO((V + E) log V)3adversarialDynamicProgramming/Adversarial interval DP2D DP table, prefix sumsO(n³)

🗂️ Repository Structure
.
├── stableMatching-GaleShapley/
│   ├── Program1.java           # University-optimal and student-optimal Gale-Shapley + stability checker
│   ├── Matching.java           # Core data structure (provided)
│   ├── AbstractProgram1.java   # Interface (provided)
│   ├── Driver.java             # CLI driver (provided)
│   └── *.in                    # Test cases (1–80 universities, up to 160 students)
│
├── dijkstra-shortestPath/
│   ├── MinHeap.java            # Binary min-heap backed by ArrayList<KVPair>
│   ├── Dijkstra.java           # Shortest path using custom MinHeap
│   ├── KVPair.java             # Key-value pair struct (provided)
│   ├── Graph.java              # Adjacency list graph (provided)
│   ├── HeapTest.java           # JUnit heap tests (provided)
│   └── DijkstraTest.java       # JUnit Dijkstra tests (provided)
│
└── adversarialDynamicProgramming/
    ├── Program3.java           # Adversarial interval DP solver
    ├── Driver.java             # Input parser and timing harness
    └── testInput.txt           # Sample input

🔬 Project Summaries
1 — 🎓 Stable Matching (stableMatching-GaleShapley/)
Solves a many-to-one stable matching problem: n students are assigned to m universities, each with variable seat capacity and a strict preference ranking over all students. The matching must be stable — no student-university pair would mutually prefer each other over their current assignment.
Both the university-optimal and student-optimal variants of Gale-Shapley are implemented, along with a brute-force stability checker. Rank lookup tables are precomputed for O(1) preference comparisons during the main loop.
→ See stableMatching-GaleShapley/README.md

2 — 📍 Dijkstra's Shortest Path (dijkstra-shortestPath/)
Implements Dijkstra's single-source shortest path algorithm backed by a hand-rolled binary min-heap — no PriorityQueue. The heap uses Floyd's O(n) buildHeap and a lazy deletion strategy to avoid the need for a decrease-key operation: stale heap entries are simply skipped via a fixed[] array on extraction.
→ See dijkstra-shortestPath/README.md

3 — 🪙 Adversarial Dynamic Programming (adversarialDynamicProgramming/)
Solves a two-player coin collection game using interval dynamic programming. A wall is placed between cities to split a row into two groups; the adversary always picks the more valuable half. The recurrence captures the minimax structure of the game: the player maximizes over wall positions, the adversary minimizes over the resulting split. Prefix sums provide O(1) range queries throughout.
→ See adversarialDynamicProgramming/README.md

🔧 Build & Run
Each project compiles independently. From any subproject directory:
bash# Compile all files
javac *.java
See each subproject's README for specific run instructions and input formats.

All projects target Java 21. Do not add package statements or extra imports.


💡 Concepts Covered
Concepts: Deferred acceptance/proposer optimalityStable MatchingRank table preprocessing for O(1) comparisonsStable MatchingBinary min-heap (build, insert, extract-min)DijkstraFloyd's O(n) heap constructionDijkstraLazy deletion in priority-queue-based graph searchDijkstraInterval DP / bottom-up subproblem orderingAdversarial DPMinimax game tree structureAdversarial DPPrefix sums for O(1) range queriesAdversarial DP

ECE 360C: Algorithms — University of Texas at Austin
