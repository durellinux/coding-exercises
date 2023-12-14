package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;

public class Day14ParabolicReflectorDish {
    private record Point(int row, int col, char value) {}

    private record Positions(Map<Integer, Set<Point>> rows, Map<Integer, Set<Point>> cols) {}

    public long solve(List<String> input) {
        Map<Integer, Integer> firstAvailableRowForCol = new HashMap<>();
        List<Long> rockRows = new ArrayList<>();

        for (int c = 0; c < input.get(0).length(); c++) {
            firstAvailableRowForCol.put(c, 0);
        }

        for (int r = 0; r < input.size(); r++) {
            String string = input.get(r);
            char[] chars = string.toCharArray();
            for (int c = 0; c < chars.length; c++) {
                char value = chars[c];

                if (value == '#') {
                    firstAvailableRowForCol.put(c, r + 1);
                }

                if (value == 'O') {
                    int position = firstAvailableRowForCol.get(c);
                    firstAvailableRowForCol.put(c, position + 1);
                    rockRows.add((long) position);
                }
            }
        }

        int totalRows = input.size();

        return rockRows.stream().map(v -> totalRows - v).reduce(Long::sum).orElse(0L);
    }

    public long solve2(List<String> input) {
        Positions position = new Positions(new HashMap<>(), new HashMap<>());

        for (int r = 0; r < input.size(); r++) {
            String string = input.get(r);
            char[] chars = string.toCharArray();
            for (int c = 0; c < chars.length; c++) {
                if (chars[c] != '.') {
                    Point rock = new Point(r, c, chars[c]);
                    addRockToPositions(rock, position);
                }
            }
        }

        Map<Set<Point>, Integer> iterationCache = new HashMap<>();
        Map<Integer, Set<Point>> iterationCacheReversed = new HashMap<>();
        iterationCache.put(extractRollingRocksPosition(position.rows), 0);
        iterationCacheReversed.put(0, extractRollingRocksPosition(position.rows));

        int totalRows = input.size();
        int totalCols = input.get(0).length();

        int iteration = 0;
        int iterationLoop = -1;
        while (iterationLoop < 0 && iteration < 1000000000L) {
            if (iteration % 100 == 0) {
                System.out.println("Solving iteration: " + iteration);
            }
            position = doCycle(position, totalRows, totalCols);
            iteration++;
            Set<Point> newPositions = extractRollingRocksPosition(position.rows);
            if (iterationCache.containsKey(newPositions)) {
                iterationLoop = iterationCache.get(newPositions);
            } else {
                iterationCache.put(newPositions, iteration);
                iterationCacheReversed.put(iteration, newPositions);
            }
        }

        System.out.println("Final iteration: " + iteration + " - " + iterationLoop);

        int loopLength = iteration - iterationLoop;
        int remainingIterations = 1000000000 - iteration;
        int iterationsOutOfLoop = remainingIterations % loopLength;

        if (iterationsOutOfLoop == 0) {
            return computeSolution(extractRollingRocksPosition(position.rows), totalRows);
        }

        int targetIteration = iterationLoop + iterationsOutOfLoop;
        return computeSolution(iterationCacheReversed.get(targetIteration), totalRows);
    }

    private long computeSolution(Set<Point> rocks, int totalRows) {
        return rocks.stream().map(v -> totalRows - v.row).reduce(Integer::sum).orElse(0);

    }

    private Positions doCycle(Positions positions, int R, int C) {
        Positions current = positions;
        current = rollNorth(current, R, C);
        current = rollWest(current, R, C);
        current = rollSouth(current, R, C);
        current = rollEast(current, R, C);

        return current;
    }

    private Positions rollEast(Positions current, int totalRows, int totalCols) {
        Map<Integer, Integer> firstAvailableColForRow = new HashMap<>();
        for (int r = 0; r < totalRows; r++) {
            firstAvailableColForRow.put(r, totalCols - 1);
        }

        Positions newPositions = new Positions(new HashMap<>(), new HashMap<>());

        List<Integer> colsWithRocks = new ArrayList<>(current.cols.keySet().stream().sorted().toList());
        Collections.reverse(colsWithRocks);
        for (int c: colsWithRocks) {
            Set<Point> rocks = current.cols.get(c);
            for (Point rock: rocks) {
                char value = rock.value;
                int r = rock.row;

                if (value == '#') {
                    firstAvailableColForRow.put(r, c - 1);
                    addRockToPositions(rock, newPositions);
                }

                if (value == 'O') {
                    int position = firstAvailableColForRow.get(r);
                    Point updatedRock = new Point(r, position, value);
                    firstAvailableColForRow.put(r, position - 1);
                    addRockToPositions(updatedRock, newPositions);
                }
            }
        }

        return newPositions;
    }

    private Positions rollSouth(Positions current, int totalRows, int totalCols) {
        Map<Integer, Integer> firstAvailableRowForCol = new HashMap<>();
        for (int c = 0; c < totalCols; c++) {
            firstAvailableRowForCol.put(c, totalRows - 1);
        }

        Positions newPositions = new Positions(new HashMap<>(), new HashMap<>());

        List<Integer> rowsWithRocks = new ArrayList<>(current.rows.keySet().stream().sorted().toList());
        Collections.reverse(rowsWithRocks);
        for (int r: rowsWithRocks) {
            Set<Point> rocks = current.rows.get(r);
            for (Point rock: rocks) {
                char value = rock.value;
                int c = rock.col;

                if (value == '#') {
                    firstAvailableRowForCol.put(c, r - 1);
                    addRockToPositions(rock, newPositions);
                }

                if (value == 'O') {
                    int position = firstAvailableRowForCol.get(c);
                    Point updatedRock = new Point(position, c, value);
                    firstAvailableRowForCol.put(c, position - 1);
                    addRockToPositions(updatedRock, newPositions);
                }
            }
        }

        return newPositions;
    }

    private Positions rollWest(Positions current, int totalRows, int totalCols) {
        Map<Integer, Integer> firstAvailableColForRow = new HashMap<>();
        for (int r = 0; r < totalRows; r++) {
            firstAvailableColForRow.put(r, 0);
        }

        Positions newPositions = new Positions(new HashMap<>(), new HashMap<>());

        List<Integer> colsWithRocks = current.cols.keySet().stream().sorted().toList();
        for (int c: colsWithRocks) {
            Set<Point> rocks = current.cols.get(c);
            for (Point rock: rocks) {
                char value = rock.value;
                int r = rock.row;

                if (value == '#') {
                    firstAvailableColForRow.put(r, c + 1);
                    addRockToPositions(rock, newPositions);
                }

                if (value == 'O') {
                    int position = firstAvailableColForRow.get(r);
                    Point updatedRock = new Point(r, position, value);
                    firstAvailableColForRow.put(r, position + 1);
                    addRockToPositions(updatedRock, newPositions);
                }
            }
        }

        return newPositions;
    }

    private Positions rollNorth(Positions current, int R, int totalCols) {
        Map<Integer, Integer> firstAvailableRowForCol = new HashMap<>();
        for (int c = 0; c < totalCols; c++) {
            firstAvailableRowForCol.put(c, 0);
        }

        Positions newPositions = new Positions(new HashMap<>(), new HashMap<>());

        List<Integer> rowsWithRocks = current.rows.keySet().stream().sorted().toList();
        for (int r: rowsWithRocks) {
            Set<Point> rocks = current.rows.get(r);
            for (Point rock: rocks) {
                char value = rock.value;
                int c = rock.col;

                if (value == '#') {
                    firstAvailableRowForCol.put(c, r + 1);
                    addRockToPositions(rock, newPositions);
                }

                if (value == 'O') {
                    int position = firstAvailableRowForCol.get(c);
                    Point updatedRock = new Point(position, c, value);
                    firstAvailableRowForCol.put(c, position + 1);
                    addRockToPositions(updatedRock, newPositions);
                }
            }
        }

        return newPositions;
    }

    private void addRockToPositions(Point rock, Positions position) {
        if (rock.col < 0 || rock.row < 0) {
            System.out.println("Invalid position");
        }
        Set<Point> rocksRow = position.rows.getOrDefault(rock.row, new HashSet<>());
        rocksRow.add(rock);
        position.rows.put(rock.row, rocksRow);

        Set<Point> rocksCol = position.cols.getOrDefault(rock.col, new HashSet<>());
        rocksCol.add(rock);
        position.cols.put(rock.col, rocksCol);
    }


    private Set<Point> extractRollingRocksPosition(Map<Integer, Set<Point>> rocks) {
        Set<Point> rollingRocks = new HashSet<>();
        for (Set<Point> rocksPoint: rocks.values()) {
            rollingRocks.addAll(rocksPoint.stream().filter(r -> r.value == 'O').toList());
        }

        return rollingRocks;
    }
}
