# 🎓 Stable Matching — Gale-Shapley Algorithm
### ECE 360C: Algorithms | University of Texas at Austin

Implementation of the **Gale-Shapley stable matching algorithm** adapted for a many-to-one setting: university study abroad placement. Both the university-optimal and student-optimal variants are implemented, along with a stability checker for verifying arbitrary matchings.

---

## 📋 Problem Description

`n` students each rank `m` universities. Each university has a variable number of open seats and its own preference ranking of all students. The goal is to assign students to universities such that the matching is **stable** — no unmatched student and filled university would mutually prefer each other over their current assignment, and no two matched students would both prefer to swap universities.

**Two types of instability**

| Type | Condition |
|---|---|
| Type 1 | Student `i` is matched to university `c`, student `i'` is unmatched, and `c` prefers `i'` over `i` |
| Type 2 | Student `i` is matched to `c`, student `i'` is matched to `c'`, `c` prefers `i'` over `i`, and `i'` prefers `c` over `c'` |

Key constraints that distinguish this from the textbook formulation:
- Universities may have **multiple openings** (capacities vary per university)
- There is a **surplus of students** — all seats fill, but some students go unmatched

---

## 🗂️ Files

| File | Description | Modified? |
|---|---|---|
| `Program1.java` | All algorithm implementations | ✅ Yes |
| `Matching.java` | Core data structure — problem instance + solution | ❌ Provided |
| `AbstractProgram1.java` | Interface defining required methods | ❌ Provided |
| `Driver.java` | CLI driver for running and testing algorithms | ❌ Provided |

**Test cases**

| File | Size | Description |
|---|---|---|
| `1-1-1.in` | 1 university, 1 student, 1 seat | Minimal case |
| `1-12-8.in` | 1 university, 12 students, 8 seats | Single university, surplus students |
| `2-4-3.in` | 2 universities, 4 students | Small multi-university case |
| `3-3-3.in` | 3 universities, 3 students, 1 seat each | Classic textbook size |
| `3-3-3.extended.in` | Same + hardcoded matching | For testing `isStableMatching()` |
| `80-160-160.in` | 80 universities, 160 students | Large-scale stress test |

---

## 🏗️ Implementation

### Part 1 — Stability Checker (`isStableMatching`)

Brute-force O(n² · m) check over all student-university pairs. For each university, builds the set of assigned students, then checks both instability types explicitly using `indexOf` on preference lists for rank comparison.

```
for each university c:
    for each student i assigned to c:
        check Type 1: any unmatched student i' that c prefers over i?
        check Type 2: any student i' matched elsewhere that c prefers over i,
                      AND i' prefers c over their current match?
```

---

### Part 2a — University-Optimal Gale-Shapley

Universities propose to students in order of their preference lists. Students act as "tentative acceptors" — they can be displaced by a university they prefer more. Each university proposes until its seats are filled or its preference list is exhausted.

**Key design choices**
- Rank lookup table `rankS[student][university]` built upfront for O(1) preference comparisons, avoiding repeated `indexOf` calls during the main loop
- `remaining[c]` tracks unfilled seats per university
- `nextProposal[c]` tracks proposal index per university — never backtracks
- `openUniversities` queue drives the loop; displaced universities re-enqueue

**Complexity**: O(n · m) proposals in the worst case, each O(1) with the rank table → O(n · m) overall

**Optimality guarantee**: University-optimal — every university gets the best stable assignment it can achieve. Students get their worst stable match.

---

### Part 2b — Student-Optimal Gale-Shapley

Students propose to universities in preference order. Universities tentatively hold proposals up to capacity, replacing their least-preferred held student when a better one arrives.

**Key design choices**
- Rank lookup table `rank[university][student]` built upfront for O(1) comparisons
- `accepted.get(c)` maintains the current tentative cohort at each university
- Displaced students re-enter the free queue and propose to their next choice
- Students who exhaust their preference list remain unmatched (`match[s] = -1`)

**Worst-student eviction**: Linear scan over `accepted.get(c)` to find the currently least-preferred student — straightforward given the small per-university cohort sizes in typical inputs

**Complexity**: O(n²) proposals worst case → O(n² · cap) with eviction scan, where cap is max university capacity

**Optimality guarantee**: Student-optimal — every student gets the best stable assignment possible. Universities receive their worst stable cohort.

---

## 📊 Input Format

```
m n
[m space-separated seat counts]
[m lines: each university's preference list, most to least preferred]
[n lines: each student's preference list, most to least preferred]
[optional: n-length matching for isStableMatching testing]
```

**Example** (`3-3-3.in`):
```
3 3          ← 3 universities, 3 students
1 1 1        ← each university has 1 seat
2 0 1        ← university 0 prefers: student 2, 0, 1
2 0 1        ← university 1 prefers: student 2, 0, 1
0 1 2        ← university 2 prefers: student 0, 1, 2
0 2 1        ← student 0 prefers: university 0, 2, 1
1 2 0        ← student 1 prefers: university 1, 2, 0
1 2 0        ← student 2 prefers: university 1, 2, 0
```

Expected output (both variants for this input):
```
Student 0 University 0
Student 1 University 2
Student 2 University 1
```

---

## 🚀 Build & Run

```bash
# Compile
javac *.java

# University-optimal
java Driver -u 3-3-3.in

# Student-optimal
java Driver -s 3-3-3.in

# Both
java Driver -u -s 3-3-3.in

# Check stability of a hardcoded matching
java Driver -m 3-3-3.extended.in
```

> Targets **Java 21**. Do not add package statements or extra imports.

---

## 💡 Key Concepts

**Deferred acceptance** — both variants use tentative matching with possible displacement, never making a final decision until the algorithm terminates. This is what guarantees stability.

**Proposer optimality** — whichever side proposes gets the better end of the deal. In the university-optimal variant, universities always get their best stable cohort; in the student-optimal variant, students always get their best stable university.

**Rank table preprocessing** — building `rank[c][s]` or `rank[s][c]` once at the start converts every preference comparison from O(n) or O(m) to O(1), which is critical for the large test case (`80-160-160.in` has 160 students and 80 universities with varying capacities up to 6).

**Many-to-one extension** — the standard Gale-Shapley proof carries over directly: a university with `k` seats can be modeled as `k` identical copies, which is why the optimality and stability guarantees hold unchanged.

---

## 📝 Notes

---

*Part of a broader algorithms repository — see also: Dijkstra's shortest path with custom binary min-heap.*
