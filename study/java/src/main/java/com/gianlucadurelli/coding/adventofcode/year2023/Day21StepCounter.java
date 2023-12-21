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
    }

    public long solve2(List<String> input, int maxSteps) {
        char[][] garden = parseInput(input);
        Point start = getInitialPoint(garden);
        garden[start.r][start.c] = '.';
        return makeSteps(garden, start, maxSteps, this::nextPlotsWithOverFlow);
    }

    private long makeSteps(char[][] garden, Point start, int maxSteps, BiFunction<Point, char[][], List<Point>> computeNextPlots) {
        Set<Point> visitedPlots = new HashSet<>();

        Queue<Step> toVisit = new LinkedList<>();
        Set<Step> visited = new HashSet<>();

        toVisit.add(new Step(start, 0));
        visited.add(new Step(start, 0));

        while (!toVisit.isEmpty()) {
            Step current = toVisit.poll();

            if (current.step == maxSteps) {
                visitedPlots.add(current.p);
                continue;
            }

            List<Point> nextPositions = computeNextPlots.apply(current.p, garden);
            for (Point p: nextPositions) {
                Step nextStep = new Step(p, current.step + 1);
                if (!visited.contains(nextStep)) {
                    visited.add(nextStep);
                    toVisit.add(nextStep);
                }
            }
        }

        return visitedPlots.size();
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
            int r = (start.r + dr) % R;
            int c = (start.c + dc) % C;
            Optional<Character> value = getValue(r, c, garden);
            if (value.isPresent() && value.get().equals('.')) {
                points.add(new Point(start.r + dr, start.c + dc));
            }
        }

        return points;
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
