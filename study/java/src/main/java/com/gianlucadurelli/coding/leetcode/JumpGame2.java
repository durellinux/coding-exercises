package com.gianlucadurelli.coding.leetcode;

import java.util.Arrays;

// https://leetcode.com/problems/jump-game-ii/?envType=study-plan-v2&envId=top-interview-150
public class JumpGame2 {
    public int jump(int[] nums) {
        int[] minReachIn = new int[nums.length];
        Arrays.fill(minReachIn, nums.length);

        minReachIn[0] = 0;

        for(int i = 0; i < nums.length; i++) {
            int start = minReachIn[i];
            int jumps = nums[i];

            for (int j = 1; j <= jumps && i + j  < nums.length; j++) {
                int destination = i + j;
                int minDestinationReach = minReachIn[destination];
                int currentDestinationReach = start + 1;

                if (currentDestinationReach < minDestinationReach) {
                    minReachIn[destination] = currentDestinationReach;
                }
            }
        }

        return minReachIn[nums.length - 1];
    }
}
