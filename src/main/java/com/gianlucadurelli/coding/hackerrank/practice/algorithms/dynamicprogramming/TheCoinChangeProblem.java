package com.gianlucadurelli.coding.hackerrank.practice.algorithms.dynamicprogramming;

import java.util.*;

/**
 * Solution for: https://www.hackerrank.com/challenges/coin-change/problem
 */
public class TheCoinChangeProblem {

    public static long getWays(int change, List<Long> coins) {
        Map<Long, List<Long>> cache = new HashMap<>();
        cache.put(0L, generateEmptySolution(coins));

        for (long curChange = 1; curChange <= change; curChange++) {
            List<Long> currentSolution = generateEmptySolution(coins);
            for (Long coin: coins) {
                if (coin.equals(curChange)) {
                    currentSolution.set(1, 1L);
                } else if (curChange - coin > 0) {
                    long remainder = curChange - coin;
                    if (remainder >= coin || !coins.contains(remainder)) {
                        List<Long> prevSolution = cache.get(curChange - coin);
                        incrementCurrentSolution(currentSolution, prevSolution);
                    }
                }
            }

            for (int i = 0; i<currentSolution.size(); i++) {
                long curValue = currentSolution.get(i);
                currentSolution.set(i, curValue/factorial(i));
            }

            cache.put(curChange, currentSolution);
        }

        long totalSolutions = 0;
        long target = change;
        List<Long> solution = cache.get(target);
        for (Long s: solution) {
            totalSolutions += s;
        }

        return totalSolutions;
    }

    private static List<Long> generateEmptySolution(List<Long> coins) {
        List<Long> solution = new ArrayList<>();
        solution.add(0L);
        solution.add(0L);
        return solution;
    }

    private static void incrementCurrentSolution(List<Long> currentSolution, List<Long> otherSolution) {
        int missingSpots = ( 1 + otherSolution.size()) - currentSolution.size();
        for (int s = 0; s < missingSpots; s++) {
            currentSolution.add(0L);
        }

        for (int i = 1; i<otherSolution.size(); i++) {
            int currentSolutionIndex = i+1;
            long currentSolutionValue = currentSolution.get(currentSolutionIndex);
            long oldSolution = otherSolution.get(i);
            long newSolution = oldSolution * factorial(currentSolutionIndex);
            currentSolutionValue += newSolution;
            currentSolution.set(currentSolutionIndex, currentSolutionValue);
        }
    }

    private static long factorial(int value) {
        long fact = 1;
        for (int i = 1; i <= value; i++) {
            fact = fact * i;
        }
        return fact;
    }

//    public static long getWays(int change, List<Long> coins) {
//        Map<Long, List<Long>> cache = new HashMap<>();
//        getWaysDP(change, 0, 0, coins, cache);
//        return solutions;
//    }
//
//    static long getWaysDP(int change, int currentLength, long currentValue, List<Long> coins, Map<Long, List<Long>> cache) {
//        if (cache.containsKey(currentValue)) {
//            if (cache.get(currentValue).size() > currentLength) {
//                cache.get(currentValue).get(currentLength);
//            }
//        }
//
//        if (currentValue > change) {
//            return 0;
//        }
//
//        if (change == currentValue) {
//            if (!cache.containsKey(currentValue)) {
//                cache.put(currentValue, new ArrayList<>());
//                for (int c = 0; c<=coins.size(); c++) {
//                    cache.get(currentValue).add(0L);
//                }
//            }
//
//            cache.get(currentValue).set(currentLength, 1L);
//            return 1L;
//        }
//
//        List<Long> solutions = new ArrayList<>();
//        for (long coin: coins) {
//            Set<Long> newSolution = new HashSet<>(curSolution);
//            newSolution.add(coin);
//            Set<Set<Long>> otherSolutions = getWaysDP(change, currentValue + coin, coins, cache, newSolution);
//            solutions.addAll(otherSolutions);
//        }
//
//        cache.put(currentValue, solutions);
//
//        return solutions;
//    }

}
