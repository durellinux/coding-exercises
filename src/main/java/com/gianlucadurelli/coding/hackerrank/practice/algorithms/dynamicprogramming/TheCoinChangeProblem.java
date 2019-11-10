package com.gianlucadurelli.coding.hackerrank.practice.algorithms.dynamicprogramming;

import java.util.*;

/**
 * Solution for: https://www.hackerrank.com/challenges/coin-change/problem
 */
public class TheCoinChangeProblem {

    public static long getWays(int change, List<Long> coins) {
        Map<Long, Set<Set<Long>>> cache = new HashMap<>();
        long solutions = getWaysDP(change, 0, coins, cache, new HashSet<>()).size();
        return solutions;
    }

    static Set<Set<Long>> getWaysDP(int change, long currentValue, List<Long> coins, Map<Long, Set<Set<Long>>> cache, Set<Long> curSolution) {
        if (cache.containsKey(currentValue)) {
            return cache.get(currentValue);
        }

        if (currentValue > change) {
            return new HashSet<>();
        }

        if (change == currentValue) {
            Set<Set<Long>> solutions = new HashSet<>();
            solutions.add(curSolution);
            return solutions;
        }

        Set<Set<Long>> solutions = new HashSet<>();
        for (long coin: coins) {
            Set<Long> newSolution = new HashSet<>(curSolution);
            newSolution.add(coin);
            Set<Set<Long>> otherSolutions = getWaysDP(change, currentValue + coin, coins, cache, newSolution);
            solutions.addAll(otherSolutions);
        }

        cache.put(currentValue, solutions);

        return solutions;
    }

}
