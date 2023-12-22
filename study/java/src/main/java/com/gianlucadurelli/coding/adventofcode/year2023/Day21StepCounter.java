package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Day21StepCounter {

    public record Point(int r, int c) {}
    public record Step(Point p, int step) {}

    public long solve(List<String> input, int maxSteps) {
        char[][] garden = parseInput(input);
        Point start = getInitialPoint(garden);
        garden[start.r][start.c] = '.';
        return makeSteps(garden, start, maxSteps, this::nextPlots);
//        long solution = 0;
//        for (int s = maxSteps; s >= 0; s-=2) {
//            solution += frontiers.get(s);
//        }
//
//        return solution;
    }

    public long solve2(List<String> input, int maxSteps) {
        char[][] garden = parseInput(input);
        Point start = getInitialPoint(garden);
        garden[start.r][start.c] = '.';
        return makeSteps(garden, start, maxSteps, this::nextPlotsWithOverFlow);
//        long solution = 0;
//        for (int s = maxSteps; s >= 0; s-=2) {
//            solution += frontiers.get(s);
//        }
//
//        return solution;
    }

    private long makeSteps(char[][] garden, Point start, int maxSteps, BiFunction<Point, char[][], List<Point>> computeNextPlots) {
//        List<Long> frontiers = new ArrayList<>();
//        for (int s = 0; s <= maxSteps; s++) {
//            frontiers.add(0L);
//        }

        long even = 1;
        long odd = 0;

//        Set<Point> visitedPlots = new HashSet<>();

        Queue<Step> toVisit = new LinkedList<>();
//        Map<Point, Integer> visited = new HashMap<>();
        Map<Integer, Set<Point>> visited = new HashMap<>();

//        Set<Point> visited = new HashSet<>();

        toVisit.add(new Step(start, 0));
//        visited.put(start, 0);
        visited.put(-1, Collections.EMPTY_SET);
        visited.put(0, Set.of(start));
        visited.put(-2, new HashSet<>());
        visited.get(-2).add(start);
//        frontiers.set(0, 1L);

        int curStep = 0;
        while (!toVisit.isEmpty()) {
            Step current = toVisit.poll();

            if (current.step == maxSteps) {
//                visitedPlots.add(current.p);
                continue;
            }

            if (current.step != curStep) {
                if (current.step % 500 == 0) {
                    System.out.println("Analyzing step: " + current.step);
                }
                visited.remove(current.step - 2);
                curStep = current.step;
            }

            int currentStep = current.step;
            List<Point> nextPositions = computeNextPlots.apply(current.p, garden);
            for (Point p: nextPositions) {
                if (!visited.get(currentStep - 1).contains(p) && !visited.getOrDefault(currentStep + 1, Collections.EMPTY_SET).contains(p)) {
                    Step next = new Step(p, current.step + 1);
//                    frontiers.set(current.step + 1, frontiers.get(current.step + 1) + 1);
                    if ((currentStep + 1) % 2 == 0) {
                        even++;
                    } else {
                        odd++;
                    }
                    Set<Point> nextSet = visited.getOrDefault(currentStep + 1, new HashSet<>());
                    nextSet.add(p);
                    visited.put(currentStep + 1, nextSet);
                    toVisit.add(next);
//                    System.out.println("New Point: " + p + " - " + visited.size());
                } else {
//                    System.out.println("Analyzing: " + current + " - skipping " + p + " - seen: " + visited.get(p) + " - " + (current.step));
                }
            }
        }

        if (maxSteps % 2 == 0) {
            return even;
        }

        return odd;

//        System.out.println(visited);
//        System.out.println(frontiers);
//        return frontiers;
    }

    private List<Point> nextPlots(Point start, char[][] garden) {
        List<Point> points = new ArrayList<>();

        int[] drs = new int[]{0, 1, 0, -1};
        int[] dcs = new int[]{1, 0, -1, 0};

        for (int d = 0; d < 4; d++) {
            int dr = drs[d];
            int dc = dcs[d];
            int r = start.r + dr;
            int c = start.c + dc;
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
            int r = (start.r + dr);
            int c = (start.c + dc);
            Point pointInRange = pointInRange(r, c, R, C);
            Optional<Character> value = getValue(pointInRange.r, pointInRange.c, garden);
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
}
