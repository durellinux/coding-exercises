package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.recursiondynamicprogramming;

import java.util.*;

public class DPSolver {

    public DPCounterSum getClimbingWaysRecursive(long steps, long currentStep, List<Integer> possibleHops) {
        if (currentStep > steps) {
            return new DPCounterSum(0L, 1L);
        }

        if (currentStep == steps) {
            return new DPCounterSum(1L, 1L);
        }

        DPCounterSum ways = new DPCounterSum(0L, 0L);
        for (int hop : possibleHops) {
            DPCounterSum curWays = getClimbingWaysRecursive(steps, currentStep + hop, possibleHops);
            ways.add(curWays);
        }

        return ways;
    }

    public DPCounterSum getClimbingWaysDP(long steps, long currentStep, List<Integer> possibleHops, Map<Long, DPCounterSum> cache) {
        if (currentStep > steps) {
            return new DPCounterSum(0L, 1L);
        }

        if (currentStep == steps) {
            return new DPCounterSum(1L, 1L);
        }

        if (cache.containsKey(currentStep)) {
            return new DPCounterSum(cache.get(currentStep).getResult(), 1L);
        }

        DPCounterSum ways = new DPCounterSum(0L, 1L);
        for (int hop : possibleHops) {
            DPCounterSum curWays = getClimbingWaysDP(steps, currentStep + hop, possibleHops, cache);
            ways.add(curWays);
        }

        cache.put(currentStep, ways);

        return ways;
    }

    public DPCounterSum getRobotPathsRecursive(int curX, int curY, int xEnd, int yEnd) {
        if (curX > xEnd) {
            return new DPCounterSum(0L, 1L);
        }

        if (curY > yEnd) {
            return new DPCounterSum(0L, 1L);
        }

        if (curX == xEnd && curY == yEnd) {
            return new DPCounterSum(1L, 1L);
        }

        DPCounterSum ways = new DPCounterSum(0L, 0L);
        DPCounterSum right = getRobotPathsRecursive(curX + 1, curY, xEnd, yEnd);
        DPCounterSum down = getRobotPathsRecursive(curX, curY + 1, xEnd, yEnd);

        ways.add(right);
        ways.add(down);

        return ways;
    }

    public DPCounterSum getRobotPathsDP(int curX, int curY, int xEnd, int yEnd, Map<Position2D, DPCounterSum> cache) {

        if (curX > xEnd) {
            return new DPCounterSum(0L, 1L);
        }

        if (curY > yEnd) {
            return new DPCounterSum(0L, 1L);
        }

        if (curX == xEnd && curY == yEnd) {
            return new DPCounterSum(1L, 1L);
        }

        Position2D curPosition = new Position2D(curX, curY);
        if (cache.containsKey(curPosition)) {
            DPCounterSum solution = cache.get(curPosition);
            return new DPCounterSum(solution.getResult(), 1);
        }

        DPCounterSum ways = new DPCounterSum(0L, 0L);
        DPCounterSum right = getRobotPathsDP(curX + 1, curY, xEnd, yEnd, cache);
        DPCounterSum down = getRobotPathsDP(curX, curY + 1, xEnd, yEnd, cache);

        ways.add(right);
        ways.add(down);

        cache.put(curPosition, ways);

        return ways;
    }


    public List<Position2D> getRobotPathWithUnallowed(Position2D start, Position2D target, Set<Position2D> unallowed) {
        Map<Position2D, DPCounterSum> cache = new HashMap<>();
        getRobotPathsDPUnallowed(start.getX(), start.getY(), target.getX(), target.getY(), cache, unallowed);

        if (cache.containsKey(start) && cache.get(start).getResult() > 0) {
            List<Position2D> solution = new ArrayList<>();
            solution.add(start);

            Position2D currentPosition = new Position2D(start.getX(), start.getY());
            while(!currentPosition.equals(target)) {
                Position2D rightPosition = new Position2D(currentPosition.getX() + 1, currentPosition.getY());
                Position2D downPosition = new Position2D(currentPosition.getX(), currentPosition.getY() + 1);
                if (cache.containsKey(rightPosition) && cache.get(rightPosition).getResult() > 0) {
                    solution.add(rightPosition);
                    currentPosition = rightPosition;
                } else {
                    solution.add(downPosition);
                    currentPosition = downPosition;
                }
            }

            return solution;
        }

        return new ArrayList<>();
    }

    public DPCounterSum getRobotPathsDPUnallowed(int curX, int curY, int xEnd, int yEnd, Map<Position2D, DPCounterSum> cache, Set<Position2D> unallowed) {

        if (curX > xEnd) {
            return new DPCounterSum(0L, 1L);
        }

        if (curY > yEnd) {
            return new DPCounterSum(0L, 1L);
        }

        Position2D curPosition = new Position2D(curX, curY);

        if (curX == xEnd && curY == yEnd) {
            DPCounterSum solution = new DPCounterSum(1L, 1L);
            cache.put(curPosition, solution);
            return solution;
        }

        if (unallowed.contains(curPosition)) {
            return new DPCounterSum(0L, 1L);
        }

        if (cache.containsKey(curPosition)) {
            DPCounterSum solution = cache.get(curPosition);
            return new DPCounterSum(solution.getResult(), 1);
        }

        DPCounterSum ways = new DPCounterSum(0L, 0L);
        DPCounterSum right = getRobotPathsDPUnallowed(curX + 1, curY, xEnd, yEnd, cache, unallowed);
        DPCounterSum down = getRobotPathsDPUnallowed(curX, curY + 1, xEnd, yEnd, cache, unallowed);

        ways.add(right);
        ways.add(down);

        cache.put(curPosition, ways);

        return ways;
    }

    public Optional<Integer> getMagicIndexDistinct(Integer[] array) {
        int begin = 0;
        int end = array.length - 1;

        while(begin != end) {
            int index = (begin + end) / 2;
            if (array[index] == index) {
                return Optional.of(index);
            }

            if (array[index] > index) {
                end = index - 1;
            } else {
                begin = index + 1;
            }
        }

        if (array[begin] == begin) {
            return Optional.of(begin);
        }

        return Optional.empty();
    }

    public Optional<Integer> getMagicIndex(Integer[] array) {
        for (int i = 0; i<array.length; i++) {
            if (i == array[i]) {
                return Optional.of(i);
            }
        }

        return Optional.empty();
    }

}
