package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class Day21StepCounter {

    private record Point(int x, int y) {}

    public record Step(Point p, int step) {}

    public record Rectangle(Point bottomLeft, Point bottomRight, Point topRight, Point topLeft) {}

    public record WalkStatsWithFrontiers(List<Long> frontiers, WalkStats stats) {
        public long visitedPlotsAtStep(int step) {
            if (step > frontiers.size()) {
                return -1;
            }

            long result = 0;
            for (int i = step; i >= 0; i -= 2) {
                result += frontiers.get(i);
            }

            return result;
        }
    }

    public record WalkStats(long even, long odd, long minStep, long maxStep) {}

    public long solve(List<String> input, int maxSteps) {
        char[][] garden = parseInput(input);
        Point start = getInitialPoint(garden);
        garden[start.x][start.y] = '.';
        WalkStatsWithFrontiers stats = makeSteps(garden, start, maxSteps, this::nextPlots, s -> s.step == maxSteps, false);
        System.out.println(stats);
        return stats.visitedPlotsAtStep(maxSteps);
    }

    public long solve2(List<String> input, int maxSteps) {
        char[][] garden = parseInput(input);
        Point start = getInitialPoint(garden);
        garden[start.x][start.y] = '.';

        int R = garden.length;
        int C = garden[0].length;

        WalkStatsWithFrontiers centerStats = makeSteps(garden, start, 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0, false);
//        WalkStatsWithFrontiers solution = makeSteps(garden, start, maxSteps, this::nextPlotsWithOverFlow, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0, false);

//        System.out.println("Garden: " + R + " x " + C);
//        System.out.println("Start: " + start.x + " x " + start.y);
//        System.out.println("Frontiers size: " + centerStats.frontiers.size());
//        System.out.println(centerStats.stats);

        int fullGardens = maxSteps / 131;
        if (fullGardens > 0) {
            fullGardens--;
        }
        int remainingHorizontal = maxSteps - 65 - fullGardens * 131;

        int fullDiagonals = (maxSteps - 130) / 262;
        int remainingDiagonal = maxSteps - 130 - fullDiagonals * 130;

        WalkStatsWithFrontiers leftStats = makeSteps(garden, new Point((R - 1) / 2, 0), 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0, false);
        WalkStatsWithFrontiers rightStats = makeSteps(garden, new Point((R - 1) / 2, C - 1), 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0, false);
        WalkStatsWithFrontiers upStats = makeSteps(garden, new Point(R - 1, (C - 1) / 2), 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0, false);
        WalkStatsWithFrontiers downStats = makeSteps(garden, new Point(0, (C - 1) / 2), 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0, false);
        WalkStatsWithFrontiers upRightStats = makeSteps(garden, new Point(R - 1, 0), 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0, false);
        WalkStatsWithFrontiers upLeftStats = makeSteps(garden, new Point(R - 1, C - 1), 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0, false);
        WalkStatsWithFrontiers downLeftStats = makeSteps(garden, new Point(0, C - 1), 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0, false);
        WalkStatsWithFrontiers downRightStats = makeSteps(garden, new Point(0, 0), 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0, false);

//        System.out.println(leftStats.stats);
//        System.out.println(rightStats.stats);
//        System.out.println(upStats.stats);
//        System.out.println(downStats.stats);
//        System.out.println(upRightStats.stats);
//        System.out.println(upLeftStats.stats);
//        System.out.println(downRightStats.stats);
//        System.out.println(downLeftStats.stats);
//        System.out.println("Max steps:" + maxSteps);
//        System.out.println("Full Gardens:" + fullGardens);
//        System.out.println("Remaining Steps:" + remainingHorizontal);
//        System.out.println("Full Diagonals:" + fullDiagonals);
//        System.out.println("Remaining Diagonal:" + remainingDiagonal);


//        int horizontalRemainingMoreThanBoundary = remainingHorizontal % 130;
//
//        long value = centerStats.stats.even;
//        value += fullGardens * (leftStats.stats.even + rightStats.stats.even + upStats.stats.even + downStats.stats.even);
//        value += (long) (fullDiagonals) * (fullGardens + 1) / 2 * (upLeftStats.stats.even + upRightStats.stats.even + downRightStats.stats.even + downLeftStats.stats.even);
//        value += leftStats.visitedPlotsAtStep(remainingHorizontal - 1) + rightStats.visitedPlotsAtStep(remainingHorizontal - 1) + upStats.visitedPlotsAtStep(remainingHorizontal - 1) + downStats.visitedPlotsAtStep(remainingHorizontal - 1);
//        value += remainingHorizontal <= 130 ? 0 : leftStats.visitedPlotsAtStep(horizontalRemainingMoreThanBoundary - 2) + rightStats.visitedPlotsAtStep(horizontalRemainingMoreThanBoundary - 2) + upStats.visitedPlotsAtStep(horizontalRemainingMoreThanBoundary - 2) + downStats.visitedPlotsAtStep(horizontalRemainingMoreThanBoundary - 2);

        long count195 = 0;
        long valueNew = centerStats.stats.odd;
        int oddEvenOffset = 0;
        for (int i = 66; i <= maxSteps; i += 131) {
            int remaining = maxSteps - i;
            int steps = Math.min(remaining, leftStats.frontiers.size() - 1 - oddEvenOffset);
            System.out.println("Adding horizontal/vertical for " + steps + " steps");
            valueNew += leftStats.visitedPlotsAtStep(steps) + rightStats.visitedPlotsAtStep(steps) + upStats.visitedPlotsAtStep(steps) + downStats.visitedPlotsAtStep(steps);
            oddEvenOffset = oddEvenOffset == 0 ? 1 : 0;
            if (steps == 195) {
                count195++;
            }
        }

        long count260 = 0;
        int count = 1;
        oddEvenOffset = 0;
        for (int i = 132; i <= maxSteps; i += 131) {
            int remaining = maxSteps - i;
            int steps = Math.min(remaining, upLeftStats.frontiers.size() - 1  - oddEvenOffset);
            System.out.println("Adding diagonal for " + steps + " steps");
            long diagonalValue = upRightStats.visitedPlotsAtStep(steps) + upLeftStats.visitedPlotsAtStep(steps) + downLeftStats.visitedPlotsAtStep(steps) + downRightStats.visitedPlotsAtStep(steps);
            valueNew += (diagonalValue * count);
            oddEvenOffset = oddEvenOffset == 0 ? 1 : 0;
            count++;
            if (steps == 260) {
                count260++;
            }
        }

        long offset = 0;
        if (count195 == 0 || count260 == 0) {
            offset = 0;
        } else if (count195 == count260){
            offset = -count260 * 124;
        } else {
            offset = count260 * 124;
        }

        System.out.println(count195 + ", " + count260);
        System.out.println(valueNew);
//        System.out.println(solution.visitedPlotsAtStep(maxSteps));
        return valueNew + offset;
    }

    public long solveBruteForce(List<String> input, int maxSteps) {
        char[][] garden = parseInput(input);
        int R = garden.length;
        int C = garden[0].length;
        Point start = getInitialPoint(garden);
        garden[start.x][start.y] = '.';

        WalkStatsWithFrontiers solution = makeSteps(garden, start, maxSteps, this::nextPlotsWithOverFlow, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0, false);
        return solution.visitedPlotsAtStep(maxSteps);
    }


    public List<Long> plot2(List<String> input, int maxSteps) {
        char[][] garden = parseInput(input);
        Point start = getInitialPoint(garden);
        garden[start.x][start.y] = '.';

        int R = garden.length;
        int C = garden[0].length;

        WalkStatsWithFrontiers stats = makeSteps(garden, start, maxSteps, this::nextPlotsWithOverFlow, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0, false);

//        System.out.println(stats.stats);
        return stats.frontiers;
    }

    private WalkStatsWithFrontiers makeSteps(char[][] garden, Point start, int maxSteps, BiFunction<Point, char[][], List<Point>> computeNextPlots, Predicate<Step> predicate, boolean printMatrix) {
        Set<Step> visitedPlots = new HashSet<>();
        List<Long> frontiers = new ArrayList<>();

        Set<Step> allSteps = new HashSet<>();
        allSteps.add(new Step(start, 0));

        Queue<Step> toVisit = new LinkedList<>();
        Set<Point> visited = new HashSet<>();

        toVisit.add(new Step(start, 0));
        visited.add(start);
        frontiers.add(1L);

        while (!toVisit.isEmpty()) {
            Step current = toVisit.poll();

            if (current.step > maxSteps) {
                break;
            }

            if (predicate.test(current)) {
                visitedPlots.add(current);
            }

            List<Point> nextPositions = computeNextPlots.apply(current.p, garden);
            for (Point p: nextPositions) {
                if (!visited.contains(p)) {
                    Step next = new Step(p, current.step + 1);
                    visited.add(p);
                    toVisit.add(next);

                    if (frontiers.size() < current.step + 2) {
                        frontiers.add(0L);
                    }

                    long old = frontiers.get(current.step + 1);
                    frontiers.set(current.step + 1, old + 1);

                    if (maxSteps % 2 == (current.step + 1) % 2) {
                        visitedPlots.add(next);
                    }

                    allSteps.add(next);
                }
            }
        }

        if (printMatrix) {
            printMatrix(garden, visitedPlots);
        }

        long even = 0;
        long odd = 0;
        for (int f = 0; f < frontiers.size(); f++) {
            if ( f % 2 == 0) {
                even += frontiers.get(f);
            } else {
                odd += frontiers.get(f);
            }
        }


        int minStep = visitedPlots.stream().map(s -> s.step).min(Comparator.comparingInt(a -> a)).orElse(0);
        int maxStep = visitedPlots.stream().map(s -> s.step).max(Comparator.comparingInt(a -> a)).orElse(0);
        return new WalkStatsWithFrontiers(frontiers, new WalkStats(even, odd, minStep, maxStep));
    }

    private List<Point> nextPlots(Point start, char[][] garden) {
        List<Point> points = new ArrayList<>();

        int[] drs = new int[]{0, 1, 0, -1};
        int[] dcs = new int[]{1, 0, -1, 0};

        for (int d = 0; d < 4; d++) {
            int dr = drs[d];
            int dc = dcs[d];
            int r = start.x + dr;
            int c = start.y + dc;
            Optional<Character> value = getValue(r, c, garden);
            if (value.isPresent() && value.get().equals('.')) {
                points.add(new Point(r, c));
            }
        }

        return points;
    }

    private List<Point> nextPlotsWithOverFlow(Point start, char[][] garden) {
        int R = garden.length;
        int C = garden[0].length;
        List<Point> points = new ArrayList<>();

        int[] drs = new int[]{0, 1, 0, -1};
        int[] dcs = new int[]{1, 0, -1, 0};

        for (int d = 0; d < 4; d++) {
            int dr = drs[d];
            int dc = dcs[d];
            int r = (start.x + dr);
            int c = (start.y + dc);
            Point pointInRange = pointInRange(r, c, R, C);
            Optional<Character> value = getValue(pointInRange.x, pointInRange.y, garden);
            if (value.isPresent() && value.get().equals('.')) {
                points.add(new Point(r, c));
            }
        }

        return points;
    }

    private Point pointInRange(int r, int c, int R, int C) {
        while (r < 0 || c < 0) {
            r += R;
            c += C;
        }

        return new Point(r % R, c % C);
    }

    private Optional<Character> getValue(int r, int c, char[][] garden) {
        int R = garden.length;
        int C = garden[0].length;

        if (r < 0 || r >= R || c < 0 || c >= C) {
            return Optional.empty();
        }

        return Optional.of(garden[r][c]);
    }

    private Point getInitialPoint(char[][] garden) {
        int R = garden.length;
        int C = garden[0].length;

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (garden[r][c] == 'S') {
                    return new Point(r, c);
                }
            }
        }

        throw new RuntimeException("No start found");
    }

    private char[][] parseInput(List<String> input) {
        int R = input.size();
        int C = input.get(0).length();
        char[][] matrix = new char[R][C];

        for (int r = 0; r < R; r++) {
            char[] line = input.get(r).toCharArray();
            for (int c = 0; c < C; c++) {
                matrix[r][c] = line[c];
            }
        }

        return matrix;
    }

    void printMatrix(char[][] garden, Set<Step> steps) {
        Map<Point, Integer> stepForPoint = new HashMap<>();
        steps.forEach(s -> stepForPoint.put(s.p, s.step));

        int R = garden.length;
        int C = garden[0].length;

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                Point p = new Point(r, c);
                if (stepForPoint.containsKey(p)) {
                    System.out.print(stepForPoint.get(p) + " ");
                } else {
                    System.out.print(garden[r][c] + " ");
                }
            }
            System.out.println();
        }
    }
}
