package com.gianlucadurelli.coding.google.codejamio.a2021;

import java.util.List;

public class InconsistentOrdering {

    public static String solve(List<Integer> blocks) {
        StringBuilder solution = new StringBuilder("A");

        boolean asc = true;

        for(int b = 0; b < blocks.size(); b++) {
            int block = blocks.get(b);

            if (asc) {
                for(int i = 0; i < block - 1; i++) {
                    char toAdd = (char) (solution.charAt(solution.length()-1) + 1);
                    solution.append(toAdd);
                }

                if (b == blocks.size() - 1) {
                    char toAdd = (char) (solution.charAt(solution.length()-1) + 1);
                    solution.append(toAdd);
                } else {
                    int nextBlockSize = blocks.get(b + 1);
                    char minValue = (char) ('A' + nextBlockSize);
                    char nextChar = (char) (solution.charAt(solution.length()-1) + 1);
                    char toAdd = (char) Math.max(minValue, nextChar);
                    solution.append(toAdd);
                }

            } else {
                for(int i = 0; i < block; i++) {
                    char toAdd = (char) ('A' + block - i - 1);
                    solution.append(toAdd);
                }
            }

            asc = !asc;
        }

        return solution.toString();
    }
}
