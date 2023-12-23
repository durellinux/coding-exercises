package com.gianlucadurelli.coding.adventofcode.year2023;

import java.math.BigInteger;
import java.util.*;

public class Day23ALongWalk {
    private record Point(int r, int c) {}
    private record Step(Point p, long steps, BigInteger visited) {}

    private record Step2(Point p, long steps) {}

    private record Trail(Set<Point> points, long lenght) {}

    private static final Point GO_RIGHT = new Point(0, 1);
    private static final Point GO_LEFT = new Point(0, -1);
    private static final Point GO_UP = new Point(-1, 0);
    private static final Point GO_DOWN = new Point(1, 0);

    private static final List<Point> directions = List.of(GO_LEFT, GO_RIGHT, GO_UP, GO_DOWN);

    public long solve(List<String> input) {
        int R = input.size();
        int C = input.get(0).length();
        char[][] hikes = parseInput(input);

        Point start = null;
        Point end = null;

        for (int c = 0; c < C; c++) {
            if (hikes[0][c] == '.') {
                start = new Point(0, c);
            }

            if (hikes[R-1][c] == '.') {
                end = new Point(R-1, c);
            }
        }

        return longestPath(start, end, hikes);
    }

    public long solve2(List<String> input) {
        int R = input.size();
        int C = input.get(0).length();
        char[][] hikes = parseInput(input);

        Point start = null;
        Point end = null;

        for (int c = 0; c < C; c++) {
            if (hikes[0][c] == '.') {
                start = new Point(0, c);
            }

            if (hikes[R-1][c] == '.') {
                end = new Point(R-1, c);
            }
        }

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (hikes[r][c] != '#') {
                    hikes[r][c] = '.';
                }
            }
        }

        Set<Point> crossRoads = new HashSet<>();
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                Point p = new Point(r, c);
                if (isJoint(new Point(r,c), hikes)) {
                    crossRoads.add(p);
                }
            }
        }
        crossRoads.add(start);
        crossRoads.add(end);


        Set<Trail> trails = new HashSet<>();
        for (Point p: crossRoads) {
            trails.addAll(findTrails(hikes, p, crossRoads));
        }

        System.out.println("Crossroads: " + crossRoads.size());
        System.out.println("R x C: " + R + " x " + C);
        System.out.println("Trails: " + trails.size());
        return longestPathWithTrailsBFS(start, end, trails);
    }

    private Set<Trail> findTrails(char[][] hikes, Point start, Set<Point> crossRoads) {
        Queue<Step2> toVisit = new LinkedList<>();
        Set<Point> visited = new HashSet<>();

        visited.add(start);
        toVisit.add(new Step2(start, 0));

        Set<Trail> trails = new HashSet<>();

        while(!toVisit.isEmpty()) {
            Step2 step = toVisit.poll();

            if (crossRoads.contains(step.p) && !step.p.equals(start)) {
                trails.add(new Trail(Set.of(start, step.p), step.steps));
                continue;
            }

            List<Point> nextMoves = computeNextMoves(step.p, hikes);
            for (Point next: nextMoves) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    toVisit.add(new Step2(next, step.steps + 1));
                }
            }
        }

        return trails;
    }

    private long longestPath(Point start, Point end, char[][] hikes) {
        long explored = 0;
        int R = hikes.length;
        Queue<Step> toVisit = new LinkedList<>();
        toVisit.add(new Step(start, 0, markVisited(new BigInteger("0"), index(start, R))));

        long longestPath = -1;
        while(!toVisit.isEmpty()) {
            Step step = toVisit.poll();

            if (step.p.equals(end) && step.steps > longestPath) {
                longestPath = step.steps;
                continue;
            }

            List<Point> possibleMoves = computeNextMoves(step.p, hikes);
            for (Point p: possibleMoves) {
                int visitedBit = index(p, R);
                if (!step.visited.testBit(visitedBit)) {
                    toVisit.add(new Step(p, step.steps + 1, step.visited.flipBit(visitedBit)));
                }
            }

            explored++;
            if (explored % 1000000 == 0) {
                System.out.println("Explored: " + explored + " - Remaining: " + toVisit.size() + " - Max Length: " + longestPath);
            }

        }

        return longestPath;
    }

    private long longestPathWithTrailsBFS(Point start, Point end, Set<Trail> trails) {
        Map<Point, Integer> pointIndex = new HashMap<>();
        Map<Point, List<Trail>> trailsFromPoint = new HashMap<>();

        for (Trail t: trails) {
            t.points.forEach(p -> {
                List<Trail> trailsFrom = trailsFromPoint.getOrDefault(p, new ArrayList<>());
                trailsFrom.add(t);
                trailsFromPoint.put(p, trailsFrom);
                pointIndex.put(p, pointIndex.getOrDefault(p, pointIndex.size()));
            });
        }

        long explored = 0;
        Queue<Step> toVisit = new LinkedList<>();
        BigInteger initialVisited = new BigInteger("0").flipBit(pointIndex.get(start));
        toVisit.add(new Step(start, 0, initialVisited));

        long longestPath = -1;
        while(!toVisit.isEmpty()) {
            Step step = toVisit.poll();

            if (step.p.equals(end) && step.steps > longestPath) {
                longestPath = step.steps;
            }

            if (step.p.equals(end)) {
                continue;
            }

            List<Trail> possibleMoves = trailsFromPoint.getOrDefault(step.p, new ArrayList<>());
            for (Trail t: possibleMoves) {
                Point p = otherEnd(t, step.p);
                int visitedBit = pointIndex.get(p);
                if (!step.visited.testBit(visitedBit)) {
                    toVisit.add(new Step(p, step.steps + t.lenght, step.visited.flipBit(visitedBit)));
                }
            }

            explored++;
            if (explored % 1000000 == 0) {
                System.out.println("Explored: " + explored + " - Remaining: " + toVisit.size() + " - Max Length: " + longestPath);
            }

        }

        System.out.println("[Done] Explored: " + explored + " - Remaining: " + toVisit.size() + " - Max Length: " + longestPath);
        return longestPath;
    }

    private Point otherEnd(Trail trail, Point start) {
        return trail.points.stream().filter(p -> !p.equals(start)).findFirst().get();
    }

    private boolean isJoint(Point p, char[][] hikes) {
        return hikes[p.r][p.c] != '#' && computeNextMoves(p, hikes).size() > 2;
    }

    private List<Point> computeNextMoves(Point p, char[][] hikes) {
        int R = hikes.length;
        int C = hikes[0].length;
        List<Point> nextMoves = new ArrayList<>();

        char current = hikes[p.r][p.c];
        List<Point> nextDirections = directions;
        switch (current) {
            case '>' -> nextDirections = List.of(GO_RIGHT);
            case '<' -> nextDirections = List.of(GO_LEFT);
            case '^' -> nextDirections = List.of(GO_UP);
            case 'v' -> nextDirections = List.of(GO_DOWN);
        }

        for (Point d: nextDirections) {
            int r = p.r + d.r;
            int c = p.c + d.c;

            if (r >= 0 && r < R && c >= 0 && c < C && hikes[r][c] != '#') {
                nextMoves.add(new Point(r, c));
            }
        }

        return nextMoves;
    }

    private int index(Point p, int R) {
        return p.r * R + p.c;
    }

    private BigInteger markVisited(BigInteger prevVisited, int index) {
        return prevVisited.flipBit(index);
    }

    private char[][] parseInput(List<String> input) {
        int R = input.size();
        int C = input.get(0).length();
        char[][] hikes = new char[R][C];
        for (int r = 0; r < R; r++) {
            char[] line = input.get(r).toCharArray();
            System.arraycopy(line, 0, hikes[r], 0, C);
        }

        return hikes;
    }
}
