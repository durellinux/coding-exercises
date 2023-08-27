package com.gianlucadurelli.coding.leetcode;

import java.util.HashMap;
import java.util.Map;

// https://leetcode.com/problems/majority-element/?envType=study-plan-v2&envId=top-interview-150
public class MajorityElement {
    public int majorityElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("nums do not have a majority element");
        }

        if (nums.length == 1) {
            return nums[0];
        }

        int majorityElement = nums[0];
        int count = 1;

        for(int idx = 1; idx < nums.length; idx++) {
            int current = nums[idx];
            if (current == majorityElement) {
                count += 1;
            } else {
                count -= 1;
            }

            if (count == 0) {
                majorityElement = current;
                count = 1;
            }
        }

        return majorityElement;
    }

    public int majorityElement_v1(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("nums do not have a majority element");
        }

        Map<Integer, Integer> count = new HashMap<>();

        for (int n: nums) {
            count.put(n, count.getOrDefault(n, 0) + 1);
        }

        int target = nums.length / 2;
        for (Map.Entry<Integer, Integer> entries: count.entrySet()) {
            if (entries.getValue() > target) {
                return entries.getKey();
            }
        }

        throw new IllegalArgumentException("nums do not have a majority element");
    }
}
