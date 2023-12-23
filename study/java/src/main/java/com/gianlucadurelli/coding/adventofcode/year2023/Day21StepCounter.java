package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class Day21StepCounter {

    private record Point(int x, int y) {}

    private record Fraction(int numerator, int denominator, boolean positive) {
        public static Fraction from(Point p) {
            // TODO: Fix
            return new Fraction(p.y, p.x, true);
        }
    }

    private record Segment(Point p1, Point p2) {
        public boolean belongsToSegment(Point p, int R, int C) {
            int xMin = Math.min(p1.x, p2.x);
            int xMax = Math.max(p1.x, p2.x);

            int yMin = Math.min(p1.y, p2.y);
            int yMax = Math.max(p1.y, p2.y);

//            boolean xInRange = xMin <= (p.x + kr * R) && (p.x + kr * R) <= xMax;
//            boolean yInRange = xMin <= (p.x + kc * C) && (p.x + kc * C) <= xMax;
//            boolean sameSlope = (p2.x - px + kr * R) && (p.y *)

            return false;
        }
    }

    public record Step(Point p, int step) {}

    public record WalkStatsWithFrontiers(List<Long> frontiers, Set<Step> selectedSteps, WalkStats stats) {
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

        public Step minLeft(int C) {
            List<Step> steps = selectedSteps.stream().filter(s -> s.p.y == 0).sorted(Comparator.comparingInt(a -> a.step)).map(s -> new Step(new Point(s.p.x, C - 1), s.step + 1)).toList();
            return steps.get(0);
        }

        public Step minRight(int C) {
            List<Step> steps = selectedSteps.stream().filter(s -> s.p.y == C - 1).sorted(Comparator.comparingInt(a -> a.step)).map(s -> new Step(new Point(s.p.x, 0), s.step + 1)).toList();
            return steps.get(0);
        }

        public Step minUp(int R) {
            List<Step> steps = selectedSteps.stream().filter(s -> s.p.y == 0).sorted(Comparator.comparingInt(a -> a.step)).map(s -> new Step(new Point(R - 1, s.p.y), s.step  +1)).toList();
            return steps.get(0);
        }

        public Step minDown(int R) {
            List<Step> steps = selectedSteps.stream().filter(s -> s.p.y == R -1).sorted(Comparator.comparingInt(a -> a.step)).map(s -> new Step(new Point(0, s.p.y), s.step + 1)).toList();
            return steps.get(0);
        }
    }

    public record WalkStats(long even, long odd, long minStep, long maxStep) {}

    public long solve(List<String> input, int maxSteps) {
        char[][] garden = parseInput(input);
        Point start = getInitialPoint(garden);
        garden[start.x][start.y] = '.';
        WalkStatsWithFrontiers stats = makeSteps(garden, start, maxSteps, this::nextPlots, s -> s.step == maxSteps);
        System.out.println(stats);
        return stats.visitedPlotsAtStep(maxSteps);
    }

    public long solve2(List<String> input, int maxSteps) {
        char[][] garden = parseInput(input);
        Point start = getInitialPoint(garden);
        garden[start.x][start.y] = '.';

        int R = garden.length;
        int C = garden[0].length;

        WalkStatsWithFrontiers stats = makeSteps(garden, start, 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0);

        System.out.println("Garden: " + R + " x " + C);
        System.out.println("Start: " + start.x + " x " + start.y);
        System.out.println("Frontiers size: " + stats.frontiers.size());
        System.out.println(stats.stats);

        if (maxSteps <= stats.stats.minStep) {
            return stats.visitedPlotsAtStep(maxSteps);
        }

        long fullGardens = maxSteps / stats.stats.maxStep;

        Step firstUp = stats.minUp(R);
        Step firstDown = stats.minDown(R);
        Step firstLeft = stats.minLeft(C);
        Step firstRight = stats.minRight(C);


        WalkStatsWithFrontiers leftStats = makeSteps(garden, firstLeft.p, 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0);
        WalkStatsWithFrontiers rightStats = makeSteps(garden, firstRight.p, 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0);
        WalkStatsWithFrontiers upStats = makeSteps(garden, firstUp.p, 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0);
        WalkStatsWithFrontiers downStats = makeSteps(garden, firstDown.p, 400, this::nextPlots, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0);

        System.out.println(leftStats.stats);
        System.out.println(rightStats.stats);
        System.out.println(upStats.stats);
        System.out.println(leftStats.stats);

        return stats.stats().even()
                + leftStats.visitedPlotsAtStep(maxSteps - firstLeft.step)
                + rightStats.visitedPlotsAtStep(maxSteps - firstRight.step)
                + upStats.visitedPlotsAtStep(maxSteps - firstUp.step)
                + downStats.visitedPlotsAtStep(maxSteps - firstDown.step);
    }


    public List<Long> plot2(List<String> input, int maxSteps) {
        char[][] garden = parseInput(input);
        Point start = getInitialPoint(garden);
        garden[start.x][start.y] = '.';

        int R = garden.length;
        int C = garden[0].length;

        WalkStatsWithFrontiers stats = makeSteps(garden, start, maxSteps, this::nextPlotsWithOverFlow, s -> s.p.x == R - 1 || s.p.y == C - 1 || s.p.x == 0 || s.p.y == 0);

//        System.out.println(stats.stats);
        return stats.frontiers;
    }

    private WalkStatsWithFrontiers makeSteps(char[][] garden, Point start, int maxSteps, BiFunction<Point, char[][], List<Point>> computeNextPlots, Predicate<Step> predicate) {
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

        printMatrix(garden, visitedPlots);

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
        return new WalkStatsWithFrontiers(frontiers, visitedPlots, new WalkStats(even, odd, minStep, maxStep));
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
                    System.out.print("O");
                } else {
                    System.out.print(garden[r][c]);
                }
            }
            System.out.println();
        }
    }
}
