package com.gianlucadurelli.coding.hackerrank.practice.algorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

// https://www.hackerrank.com/challenges/play-game/problem
public class BricksGame {

    public static int bricksGame(List<Integer> arr) {
        // Write your code here
        HashMap<List<Integer>, List<Integer>> memoized = new HashMap<>();
        List<Integer> solution = bricksGameRecursive(arr, 0, 0, memoized);
        return solution.get(0);
    }

    private static List<Integer> bricksGameRecursive(List<Integer> bricks, int currentBrick, int currentPlayer, HashMap<List<Integer>, List<Integer>> memoized) {
        List<Integer> memoizedKey = List.of(currentPlayer, currentBrick);
        if (memoized.containsKey(memoizedKey)) {
            return memoized.get(memoizedKey);
        }

        if (currentBrick == bricks.size()) {
            return List.of(0, 0);
        }

        List<List<Integer>> scores = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            if (currentBrick + i <= bricks.size()) {
                int moveScore = take(bricks, i, currentBrick);
                List<Integer> otherScore = bricksGameRecursive(bricks, currentBrick + i, Math.abs(currentPlayer - 1), memoized);
                List<Integer> currentResult = List.of(otherScore.get(0) + moveScore, otherScore.get(1));
                if (currentPlayer == 1) {
                    currentResult = List.of(otherScore.get(0), otherScore.get(1) + moveScore);
                }
                scores.add(currentResult);
            }
        }

        List<Integer> bestOption = scores.stream().max(Comparator.comparing(v -> v.get(0))).get();
        if (currentPlayer == 1) {
            bestOption = scores.stream().min(Comparator.comparing(v -> v.get(0))).get();
        }

        memoized.put(memoizedKey, bestOption);

        return bestOption;
    }

    private static int take(List<Integer> bricks, int taking, int currentBrick) {
        int sum = 0;
        for (int i = currentBrick; i < currentBrick + taking; i++) {
            sum += bricks.get(i);
        }

        return sum;
    }
}
