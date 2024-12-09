package com.gianlucadurelli.coding.leetcode;

import java.util.*;

// https://leetcode.com/problems/minimum-time-to-visit-a-cell-in-a-grid/description/
public class MinimumTimeToVisitCellInAGrid {

    record Point(int x, int y) {}
    record Step(Point p, int time) {}

    public int minimumTime(int[][] grid) {
        // If both initial moves require more than 1 second, impossible to proceed
        if (grid[0][1] > 1 && grid[1][0] > 1) {
            return -1;
        }

        Map<Point, Integer> minVisit = new HashMap<>();
        Set<Point> visited = new HashSet<>();

        Step initial = new Step(new Point(0, 0), 0);
        Point target = new Point(grid.length - 1, grid[grid.length - 1].length - 1);

        Queue<Step> steps = new PriorityQueue<Step>(Comparator.comparing(Step::time));
        steps.add(initial);
        while(!steps.isEmpty()) {
            Step current = steps.poll();
            if (current.p().equals(target)) {
                return current.time();
            }

//            if (visited.contains(current.p())) {
//                continue;
//            }
//            visited.add(current.p());

            List<Point> neighbors = getNeighbors(current.p(), target);

            for (Point n: neighbors) {
                int minTime = grid[n.x()][n.y()];
                int waitTime = minTime <= current.time() ? 0 : minTime - current.time();

                int nextTime;
                if (waitTime % 2 == 0) {
                    nextTime = current.time() + waitTime + 1;
                } else {
                    nextTime = current.time() + waitTime ;
                }

                int pruneTime = minVisit.getOrDefault(n, Integer.MAX_VALUE);
                if (nextTime >= minTime && nextTime < pruneTime) {
                    minVisit.put(n, nextTime);
                    steps.add(new Step(n, nextTime));
                }
            }
        }

        return -1;
    }

    private Optional<Point> getOtherPoint(Point p, int dx, int dy, Point target) {
        int newX = p.x() + dx;
        int newY = p.y() + dy;

        if (newX >=0 && newX <= target.x() && newY >= 0 && newY <= target.y()) {
            return Optional.of(new Point(newX, newY));
        }

        return Optional.empty();
    }

    private List<Point> getNeighbors(Point current, Point target) {
        List<Point> neighbors = new ArrayList<>();
        getOtherPoint(current, -1, 0, target).ifPresent(neighbors::add);
        getOtherPoint(current, 1, 0, target).ifPresent(neighbors::add);
        getOtherPoint(current, 0, -1, target).ifPresent(neighbors::add);
        getOtherPoint(current, 0, +1, target).ifPresent(neighbors::add);

        return neighbors;
    }
}
