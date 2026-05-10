# 🪙 Adversarial Dynamic Programming — Coin Collection Game
### ECE 360C: Algorithms | University of Texas at Austin

Implementation of an **adversarial interval dynamic programming** solution to a two-player coin collection game. One player places a wall to split a row of cities; the opponent picks which half to keep. The goal is to find the wall placement strategy that **maximizes your guaranteed earnings** against a perfectly adversarial opponent.

---

## 📋 Overview

| Component | File | Complexity |
|---|---|---|
| Adversarial DP solver | `Program3.java` | Time: O(n³), Space: O(n²) |
| Input parser & runner | `Driver.java` | — |

---

## 🗂️ Files

| File | Description | Modified? |
|---|---|---|
| `Program3.java` | Core interval DP algorithm | ✅ Yes |
| `Driver.java` | Entry point; parses input file and times the run | ✅ Yes |
| `testInput.txt` | Sample input: `8 6 2 4 2` | — |

---

## 🏗️ Implementation

### Adversarial DP (`Program3.java`)

`dp[i][j]` stores the maximum coins the wall-placing player can **guarantee** from the subarray `cities[i..j]`. Prefix sums enable O(1) range queries.

**Game rules**
```
1. Player 1 places a wall between cities k and k+1, splitting cities[i..j]
   into a left group [i..k] and a right group [k+1..j].
2. Player 2 (adversary) keeps whichever group has the higher total — worst
   case for Player 1.
3. Player 1 collects the remaining group plus their guaranteed earnings
   from any future splits within it.
4. Repeat recursively until no split is possible.
```

**Recurrence**
```
dp[i][j] = max over all k in [i, j-1] of:
               min(
                 sum(i..k)   + dp[i][k],      ← adversary keeps right
                 sum(k+1..j) + dp[k+1][j]     ← adversary keeps left
               )

base case: dp[i][i] = 0  (single city, no wall possible)
```

**Algorithm walkthrough**
```
Build prefix sums for O(1) range queries

for length = 2 to n:
    for each subarray [i, j] of that length:
        best = 0
        for each wall position k in [i, j-1]:
            leftSum  = sum(i..k)
            rightSum = sum(k+1..j)
            keepLeft  = leftSum  + dp[i][k]
            keepRight = rightSum + dp[k+1][j]
            worstCase = min(keepLeft, keepRight)   ← adversary chooses
            best = max(best, worstCase)            ← player chooses
        dp[i][j] = best

return dp[0][n-1]
```

---

## 🔧 Build & Run

```bash
# Compile
javac Driver.java Program3.java

# Run
java Driver <input_file>
```

The input file must contain a **single line** of space-separated integers representing coin values, e.g.:

```
8 6 2 4 2
```

**Example output**
```
Optimal amount of food: <value>
Completed in <N> milliseconds
```

---

## 💡 Key Concepts

**Interval DP** — builds solutions for small subarrays first and uses them to solve larger ones. The outer loop iterates over subarray *length*, ensuring `dp[i][k]` and `dp[k+1][j]` are always computed before `dp[i][j]`.

**Minimax structure** — the inner `min/max` reflects the adversarial nature of the game: the adversary minimizes your gain (picks the better half), while you maximize against that worst case (pick the best wall position). This is the same principle underlying minimax game trees.

**Prefix sums** — a precomputed prefix array reduces every range sum query from O(n) to O(1), keeping the overall complexity at O(n³) rather than O(n⁴).

---

*Part of a broader algorithms repository covering sorting, graph traversal, dynamic programming, and optimization problems.*
