package com.gianlucadurelli.coding.leetcode;

import java.util.Deque;
import java.util.LinkedList;

// https://leetcode.com/problems/minimum-size-subarray-sum/description/?envType=study-plan-v2&envId=top-interview-150
public class MinimumSizeSubarraySum {
    public int minSubArrayLen(int target, int[] nums) {
        Deque<Integer> window = new LinkedList<>();
        int sum = 0;

        int minLength = 0;

        for (int i: nums) {
            sum = addToWindow(window, sum, i);
            if (sum >= target) {
                while (sum >= target) {
                    if (minLength == 0 || window.size() < minLength) {
                        minLength = window.size();
                    }

                    sum = removeFromWindow(window, sum);
                }
            }
        }

        return minLength;
    }

    private int addToWindow(Deque<Integer> window, int currentSum, int n) {
        window.add(n);
        return currentSum + n;
    }

    private int removeFromWindow(Deque<Integer> window, int currentSum) {
        int n = window.remove();
        return currentSum - n;
    }
}
