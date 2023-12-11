package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;

public class Day11CosmicExpansion {

    private record Point(int r, int c) {}
    private record Interval(int begin, int end) {}

    public long solve1(List<String> input) {
        return solve2(input, 2);
    }

    public long solve2(List<String> input, int expansionFactor) {
        List<List<String>> space = parseInput(input);

        Set<Integer> expandedRows = getExpandedRows(space);
        Set<Integer> expandedCols = getExpandedCols(space);

        List<Point> galaxies = getGalaxiesCoordinates(space);

        long sumDistance = 0;
        for(int g1 = 0; g1 < galaxies.size() - 1; g1++) {
            for(int g2 = g1+1; g2 < galaxies.size(); g2++) {
                Point galaxy1 = galaxies.get(g1);
                Point galaxy2 = galaxies.get(g2);

                Interval rowInterval = new Interval(Math.min(galaxy1.r, galaxy2.r), Math.max(galaxy1.r, galaxy2.r));
                Interval colInterval = new Interval(Math.min(galaxy1.c, galaxy2.c), Math.max(galaxy1.c, galaxy2.c));

                long rows = rowInterval.end - rowInterval.begin;
                long cols = colInterval.end - colInterval.begin;

                long scaledRows = expandedItemsInInterval(rowInterval, expandedRows);
                long normalRows = rows - scaledRows;

                long scaledCols = expandedItemsInInterval(colInterval, expandedCols);
                long normalCols = cols - scaledCols;

                long distance = normalRows + normalCols + (scaledRows + scaledCols) * expansionFactor;

                sumDistance += distance;
            }
        }

        return sumDistance;
    }

    private long expandedItemsInInterval(Interval interval, Set<Integer> expandedItems) {
        return expandedItems.stream().filter(item -> item >= interval.begin && item <= interval.end).count();
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

    private Set<Integer> getExpandedCols(List<List<String>> space) {
        Set<Integer> expandedCols = new HashSet<>();

        for (int c = 0; c < space.get(0).size(); c++) {
            boolean toExpand = true;
            for (List<String> strings : space) {
                if (!strings.get(c).equals(".")) {
                    toExpand = false;
                    break;
                }
            }

            if (toExpand) {
                expandedCols.add(c);
            }
        }

        return expandedCols;
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
