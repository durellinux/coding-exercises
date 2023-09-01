package com.gianlucadurelli.coding.leetcode;

// https://leetcode.com/problems/trapping-rain-water/?envType=study-plan-v2&envId=top-interview-150
public class Trap {
    public int trap(int[] height) {
        int[] maxLeft = new int[height.length];
        int[] maxRight = new int[height.length];

        int max = 0;
        for (int i = 0; i < height.length; i++) {
            maxLeft[i] = max;
            if (height[i] > max) {
                max = height[i];
            }
        }

        max = 0;
        for (int i = height.length - 1; i >= 0; i--) {
            maxRight[i] = max;
            if (height[i] > max) {
                max = height[i];
            }
        }

        int waterTrapped = 0;
        for (int i = height.length - 1; i >= 0; i--) {
            int minMax = Math.min(maxLeft[i], maxRight[i]);
            waterTrapped += Math.max(minMax - height[i], 0);
        }

        return waterTrapped;
    }
}
