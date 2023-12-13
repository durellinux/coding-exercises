package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;

public class Day13PointOfIncidence {

    public long solve(List<String> input, boolean allowBumps) {

        List<List<String>> valleys = parseInput(input);

        long result = 0;
        for (List<String> valley: valleys) {
            List<String> valleyTransposed = transpose(valley);

            Set<Integer> mirroredRows = computeMirroredRows(valley, allowBumps);
            Set<Integer> mirroredCols = computeMirroredRows(valleyTransposed, allowBumps);

            result += mirroredCols.stream().reduce(Integer::sum).orElse(0);
            result += mirroredRows.stream().map(v -> v * 100).reduce(Integer::sum).orElse(0);
        }

        return result;

    }

    private List<List<String>> parseInput(List<String> input) {
        List<List<String>> result = new ArrayList<>();
        List<String> current = new ArrayList<>();
        for (String value: input) {
            if (value.strip().isEmpty()) {
                result.add(current);
                current = new ArrayList<>();
            } else {
                current.add(value);
            }
        }

        result.add(current);

        return result;
    }

    private Set<Integer> computeMirroredRows(List<String> valley, boolean allowBumps) {
        Set<Integer> mirrored = new HashSet<>();
        for (int r1 = 1; r1 < valley.size(); r1++) {
            boolean isMirrored = true;
            boolean bumpFound = false;
            for (int r2 = r1 - 1; r2 >= 0 && 2*r1 - 1 - r2 < valley.size() && isMirrored; r2--) {
                String v1 = valley.get(r2);
                String v2 = valley.get(2*r1 - 1 - r2);

                int distance = computeDistance(v1, v2);

                if (allowBumps) {
                    if (distance < 0 || distance > 1) {
                        isMirrored = false;
                    } else if (distance == 1) {
                        if (bumpFound) {
                            isMirrored = false;
                        } else {
                            bumpFound = true;
                        }
                    }
                } else {
                    if (distance != 0) {
                        isMirrored = false;
                    }
                }
            }
            if (isMirrored && (!allowBumps || bumpFound)) {
                return Set.of(r1);
            }
        }

        return mirrored;
    }

    private int computeDistance(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return -1;
        }

        int differences = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                differences++;
            }
        }

        return differences;
    }

    public List<String> transpose(List<String> input) {
        List<String> transposed = new ArrayList<>();
        for (int c = 0; c < input.get(0).length(); c++) {
            StringBuilder line = new StringBuilder();
            for (String s : input) {
                line.append(s.charAt(c));
            }
            transposed.add(line.toString());
        }

        return transposed;
    }
}
