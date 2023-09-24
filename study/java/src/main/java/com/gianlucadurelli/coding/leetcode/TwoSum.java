package com.gianlucadurelli.coding.leetcode;

import java.util.*;

// https://leetcode.com/explore/interview/card/amazon/76/array-and-strings/508/
// https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/?envType=study-plan-v2&envId=top-interview-150
public class TwoSum {

    public int[] twoSumOffset1(int [] numbers, int target) {
        if (Objects.isNull(numbers) || numbers.length < 2) {
            return null;
        }

        int ptr1 = 0;
        int ptr2 = numbers.length - 1;

        while (ptr1 != ptr2) {
            int curSum = numbers[ptr1] + numbers[ptr2];

            if (curSum == target) {
                return new int[]{ptr1 + 1, ptr2 + 1};
            } else if (curSum < target) {
                ptr1++;
            } else {
                ptr2--;
            }
        }

        return null;
    }

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
