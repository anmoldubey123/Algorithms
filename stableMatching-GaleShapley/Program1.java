/*
 * Name: Anmol Dubey
 * EID: ad56328
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 *
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution. However, do not add extra import statements to this file.
 */
public class Program1 extends AbstractProgram1 {

    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem)
    {

        int n = problem.getStudentCount();
        int m = problem.getUniversityCount();

        ArrayList<Integer> studentMatching = problem.getStudentMatching();
        ArrayList<ArrayList<Integer>> studentPrefs = problem.getStudentPreference();
        ArrayList<ArrayList<Integer>> universityPrefs = problem.getUniversityPreference();

        if (studentMatching == null || studentMatching.size() != n) {
            return false;
        }

        for (int c = 0; c < m; c++) {

            ArrayList<Integer> assignedStudents = new ArrayList<>();
            for (int s = 0; s < n; s++) {
                if (studentMatching.get(s) == c) {
                    assignedStudents.add(s);
                }
            }

            // Instability 1 Check
            for (int idx = 0; idx < assignedStudents.size(); idx++) {
                int i = assignedStudents.get(idx);

                for (int j = 0; j < n; j++) {
                    if (studentMatching.get(j) == -1) {
                        if (universityPrefs.get(c).indexOf(j) < universityPrefs.get(c).indexOf(i)) {
                            return false;
                        }
                    }
                }
            }

            // Instability 2 Check
            for (int idx = 0; idx < assignedStudents.size(); idx++) {
                int i = assignedStudents.get(idx);

                for (int j = 0; j < n; j++) {
                    int cPrime = studentMatching.get(j);

                    if (cPrime != -1 && cPrime != c) {

                        boolean cPrefersJOverI =
                                universityPrefs.get(c).indexOf(j) < universityPrefs.get(c).indexOf(i);

                        boolean jPrefersCOverCPrime =
                                studentPrefs.get(j).indexOf(c) < studentPrefs.get(j).indexOf(cPrime);

                        if (cPrefersJOverI && jPrefersCOverCPrime) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }


    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_universityoptimal(Matching problem) {

        int n = problem.getStudentCount();
        int m = problem.getUniversityCount();

        ArrayList<ArrayList<Integer>> studentPrefs = problem.getStudentPreference();
        ArrayList<ArrayList<Integer>> universityPrefs = problem.getUniversityPreference();
        ArrayList<Integer> universityPositions = problem.getUniversityPositions();


        int[][] rankS = new int[n][m];
        for (int s = 0; s < n; s++) {
            ArrayList<Integer> prefList = studentPrefs.get(s);
            for (int pos = 0; pos < prefList.size(); pos++) {
                int uniId = prefList.get(pos);
                rankS[s][uniId] = pos;
            }
        }


        int[] match = new int[n];
        Arrays.fill(match, -1);


        int[] remaining = new int[m];
        for (int c = 0; c < m; c++) remaining[c] = universityPositions.get(c);


        int[] nextProposal = new int[m];
        Arrays.fill(nextProposal, 0);


        LinkedList<Integer> openUniversities = new LinkedList<>();
        for (int c = 0; c < m; c++) {
            if (remaining[c] > 0 && nextProposal[c] < universityPrefs.get(c).size()) {
                openUniversities.add(c);
            }
        }

        while (!openUniversities.isEmpty()) {
            int c = openUniversities.removeFirst();


            while (remaining[c] > 0 && nextProposal[c] < universityPrefs.get(c).size()) {

                int s = universityPrefs.get(c).get(nextProposal[c]);
                nextProposal[c]++;

                if (match[s] == -1) {

                    match[s] = c;
                    remaining[c]--;
                } else {
                    int current = match[s];

                    if (rankS[s][c] < rankS[s][current]) {
                        match[s] = c;
                        remaining[c]--;
                        remaining[current]++;


                        if (remaining[current] > 0 && nextProposal[current] < universityPrefs.get(current).size()) {
                            openUniversities.add(current);
                        }
                    }
                }
            }


            if (remaining[c] > 0 && nextProposal[c] < universityPrefs.get(c).size()) {
                openUniversities.add(c);
            }
        }


        ArrayList<Integer> studentMatching = new ArrayList<>(n);
        for (int s = 0; s < n; s++) {
            studentMatching.add(match[s]);
        }

        return new Matching(problem, studentMatching);
    }


    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_studentoptimal(Matching problem) {

        int n = problem.getStudentCount();
        int m = problem.getUniversityCount();

        ArrayList<ArrayList<Integer>> studentPrefs = problem.getStudentPreference();
        ArrayList<ArrayList<Integer>> universityPrefs = problem.getUniversityPreference();
        ArrayList<Integer> universityPositions = problem.getUniversityPositions();


        int[][] rank = new int[m][n];
        for (int c = 0; c < m; c++) {
            ArrayList<Integer> prefList = universityPrefs.get(c);
            for (int pos = 0; pos < prefList.size(); pos++) {
                int studentId = prefList.get(pos);
                rank[c][studentId] = pos;
            }
        }


        int[] match = new int[n];
        Arrays.fill(match, -1);


        int[] nextProposal = new int[n];
        Arrays.fill(nextProposal, 0);


        ArrayList<ArrayList<Integer>> accepted = new ArrayList<>(m);
        for (int c = 0; c < m; c++) {
            accepted.add(new ArrayList<>());
        }


        LinkedList<Integer> freeStudents = new LinkedList<>();
        for (int s = 0; s < n; s++) {
            freeStudents.add(s);
        }

        while (!freeStudents.isEmpty()) {
            int s = freeStudents.removeFirst();


            if (nextProposal[s] >= studentPrefs.get(s).size()) {
                continue;
            }


            int c = studentPrefs.get(s).get(nextProposal[s]);
            nextProposal[s]++;

            ArrayList<Integer> acceptedAtC = accepted.get(c);
            int cap = universityPositions.get(c);

            if (acceptedAtC.size() < cap) {
                acceptedAtC.add(s);
                match[s] = c;
            } else {
                int worstStudent = acceptedAtC.get(0);
                for (int idx = 1; idx < acceptedAtC.size(); idx++) {
                    int t = acceptedAtC.get(idx);
                    if (rank[c][t] > rank[c][worstStudent]) {
                        worstStudent = t;
                    }
                }

                if (rank[c][s] < rank[c][worstStudent]) {
                    acceptedAtC.remove((Integer) worstStudent);
                    match[worstStudent] = -1;
                    freeStudents.add(worstStudent);

                    acceptedAtC.add(s);
                    match[s] = c;
                } else {
                    freeStudents.add(s);
                }
            }
        }

        ArrayList<Integer> studentMatching = new ArrayList<>(n);
        for (int s = 0; s < n; s++) {
            studentMatching.add(match[s]);
        }

        return new Matching(problem, studentMatching);
    }

}