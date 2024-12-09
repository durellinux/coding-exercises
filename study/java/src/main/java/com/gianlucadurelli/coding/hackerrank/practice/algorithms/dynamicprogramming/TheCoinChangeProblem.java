package com.gianlucadurelli.coding.hackerrank.practice.algorithms.dynamicprogramming;

import java.util.*;

/**
 * Solution for: https://www.hackerrank.com/challenges/coin-change/problem
 */
public class TheCoinChangeProblem {

    public static class Change {
        private final Map<Integer, Integer> coinsMap;

        public Change() {
            coinsMap = new HashMap<>();
        }

        public Change(Map<Integer, Integer> coinsMap) {
            this.coinsMap = coinsMap;
        }

        public void put(Integer coin) {
            int currentCount = coinsMap.getOrDefault(coin, 0);
            coinsMap.put(coin, currentCount + 1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Change memo = (Change) o;
            return Objects.equals(coinsMap, memo.coinsMap);
        }

        @Override
        public int hashCode() {
            return Objects.hash(coinsMap);
        }

        public Change copy() {
            return new Change(new HashMap<>(this.coinsMap));
        }

        @Override
        public String toString() {
            List<Integer> keys = coinsMap.keySet().stream().sorted().toList();
            StringBuilder str = new StringBuilder("{");
            for (int key: keys) {
                int count = coinsMap.get(key);
                for (int i = 0; i < count; i++) {
                    str.append(" ").append(key).append(",");
                }
            }
            str.append("}");

            return str.toString();
        }
    }

    public static long getWays(int change, List<Integer> coins) {
        Map<List<Integer>, Long> memo = new HashMap<>();
        long solution = doChange(change, 0, coins, memo);
        return solution;
    }

    public static long doChange(int target, int coinIndex, List<Integer> coins, Map<List<Integer>, Long> memo) {
        if (target < 0 || coinIndex == coins.size()) {
            return 0;
        }

        if (target == 0) {
            return 1;
        }

        if (!memo.containsKey(List.of(target, coinIndex))) {
            long combinations = doChange(target - coins.get(coinIndex), coinIndex, coins, memo) + doChange(target, coinIndex + 1, coins, memo);
            memo.put(List.of(target, coinIndex), combinations);
        }

        return memo.get(List.of(target, coinIndex));
    }
}
