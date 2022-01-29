package com.gianlucadurelli.coding.leetcode;

import java.util.*;

// https://leetcode.com/explore/interview/card/amazon/76/array-and-strings/508/
public class TwoSum {
    public int[] twoSum(int[] numbers, int target) {
        if (Objects.isNull(numbers) || numbers.length < 2) {
            return null;
        }

        Map<Integer, Set<Integer>> valueMap = new HashMap<Integer, Set<Integer>>();
        for (int i = 0; i < numbers.length; i++) {
            int v = numbers[i];
            Set<Integer> positions = valueMap.getOrDefault(v, new HashSet<>());
            positions.add(i);
            valueMap.put(v, positions);
        }

        for (int i = 0; i < numbers.length; i++) {
            int v = numbers[i];
            int delta = target - v;
            if (valueMap.containsKey(delta)) {
                Set<Integer> possiblePositions = valueMap.get(delta);
                for (int i2: possiblePositions) {
                    if (i2 != i) {
                        return new int[]{i, i2};
                    }
                }
            }
        }

        return null;
    }
}
