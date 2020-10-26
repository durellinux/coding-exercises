package com.gianlucadurelli.coding.hackerrank.practice.algorithms.dynamicprogramming;

import java.util.*;

/**
 * Solution for: https://www.hackerrank.com/challenges/coin-change/problem
 */
public class TheCoinChangeProblem {

    public static long getWays(int change, List<Long> coins) {
        Map<Long, Long> cache = new HashMap<>();
        cache.put(0L, 0L);
        for(long i = 1; i<=change; i++) {
            long iWays = countWays(i, coins, cache);
            cache.put(i, iWays);
        }
        long cacheIndex = change;
        return cache.get(cacheIndex);
    }

    private static long countWays(long target, List<Long> coins, Map<Long, Long> cache) {
        long ways = 0;
        for (long start: cache.keySet()) {
            for (long coin: coins) {
                if (start + coin == target) {
                    ways += cache.get(start) + 1;
                }
            }
        }

        return ways;
    }


}
