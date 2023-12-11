package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;

public class Day11CosmicExpansion {

    private record Point(int r, int c) {}

    public int solve1(List<String> input) {
        List<List<String>> space = parseInput(input);

        space = expand(space);
        space = transpose(space);
        space = expand(space);
        space = transpose(space);

        List<Point> galaxies = getGalaxiesCoordinates(space);

        int sumDistance = 0;
        for(int g1 = 0; g1 < galaxies.size() - 1; g1++) {
            for(int g2 = g1+1; g2 < galaxies.size(); g2++) {
                Point galaxy1 = galaxies.get(g1);
                Point galaxy2 = galaxies.get(g2);

                int distance = Math.abs(galaxy1.r - galaxy2.r) + Math.abs(galaxy1.c - galaxy2.c);
                sumDistance += distance;
            }
        }

        return sumDistance;
    }

    public long solve2(List<String> input, int expansionFactor) {
        List<List<String>> space = parseInput(input);

        Set<Integer> expandedRows = getExpandedRows(space);
        space = transpose(space);
        Set<Integer> expandedCols = getExpandedRows(space);
        space = transpose(space);

        List<Point> galaxies = getGalaxiesCoordinates(space);

        long sumDistance = 0;
        for(int g1 = 0; g1 < galaxies.size() - 1; g1++) {
            for(int g2 = g1+1; g2 < galaxies.size(); g2++) {
                Point galaxy1 = galaxies.get(g1);
                Point galaxy2 = galaxies.get(g2);

                Set<Integer> rowsInterval = generateInterval(galaxy1.r, galaxy2.r);
                Set<Integer> colsInterval = generateInterval(galaxy1.c, galaxy2.c);

                long rows = rowsInterval.size();
                long cols = colsInterval.size();

                rowsInterval.retainAll(expandedRows);
                colsInterval.retainAll(expandedCols);

                long normalRows = rows - rowsInterval.size();
                long scaledRows = rowsInterval.size();
                long normalCols = cols - colsInterval.size();
                long scaledCols = colsInterval.size();

                long distance = normalRows + normalCols + (scaledRows + scaledCols) * expansionFactor;

                sumDistance += distance;
            }
        }

        return sumDistance;
    }

    private Set<Integer> generateInterval(int x1, int x2) {
        Set<Integer> interval = new HashSet<>();
        int start = Math.min(x1, x2);
        int end = Math.max(x1, x2);

        for (int v = start + 1; v <= end; v++) {
            interval.add(v);
        }

        return interval;
    }

    private Set<Integer> getExpandedRows(List<List<String>> space) {
        Set<Integer> expandedRows = new HashSet<>();
        for (int r = 0; r < space.size(); r++) {
            List<String> row = space.get(r);
            if (row.stream().allMatch(v -> v.equals("."))) {
                expandedRows.add(r);
            }
        }

        return expandedRows;
    }

    private List<List<String>> parseInput(List<String> input) {
        List<List<String>> matrix = new ArrayList<>();
        for(String v: input) {
            String[] data = v.split("");
            List<String> line = new ArrayList<>(Arrays.stream(data).toList());
            matrix.add(line);
        }

        return matrix;
    }

    private List<List<String>> expand(List<List<String>> matrix) {
        List<List<String>> expanded = new ArrayList<>();

        for (int r = 0; r < matrix.size(); r++) {
            List<String> row = matrix.get(r);
            boolean shouldExpand = row.stream().allMatch(v -> v.equals("."));

            expanded.add(row);

            if (shouldExpand) {
                List<String> copiedLine = new ArrayList<>(row);
                expanded.add(copiedLine);
            }
        }
        return expanded;
    }

    private List<List<String>> transpose(List<List<String>> matrix) {
        List<List<String>> transposed = new ArrayList<>();
        for (int c = 0; c < matrix.get(0).size(); c++) {
            List<String> row = new ArrayList<>();
            for (List<String> strings : matrix) {
                row.add(strings.get(c));
            }
            transposed.add(row);
        }

        return transposed;
    }

    private List<Point> getGalaxiesCoordinates(List<List<String>> space) {
        List<Point> galaxies = new ArrayList<>();
        for (int r = 0; r < space.size(); r++) {
            for (int c = 0; c < space.get(0).size(); c++) {
                if (space.get(r).get(c).equals("#")) {
                    galaxies.add(new Point(r, c));
                }
            }
        }

        return galaxies;
    }
}
