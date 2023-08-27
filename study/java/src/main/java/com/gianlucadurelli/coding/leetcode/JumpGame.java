package com.gianlucadurelli.coding.leetcode;

// https://leetcode.com/problems/jump-game/?envType=study-plan-v2&envId=top-interview-150
public class JumpGame {
    public boolean canJump(int[] nums) {
        int currentIndex = nums.length - 1;

        boolean isPossible = true;
        while (currentIndex > 0 && isPossible) {
            boolean found = false;

            int from = currentIndex - 1;
            while (from >= 0 && !found) {
                int distance = currentIndex - from;
                if (nums[from] >= distance) {
                    currentIndex = from;
                    found = true;
                }

                from--;
            }

            isPossible = found;
        }

        return isPossible;
    }
}
