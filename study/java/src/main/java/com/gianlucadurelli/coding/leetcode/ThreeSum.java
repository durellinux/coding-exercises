package com.gianlucadurelli.coding.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> solutions = new ArrayList<>();

        int currentPosition = 0;
        while(currentPosition < nums.length - 2) {
            List<List<Integer>> twoSumsSolution = twoSum(nums, currentPosition + 1, 0 - nums[currentPosition]);
            for(List<Integer> twoSum: twoSumsSolution) {
                List<Integer> threeSumSolution = new ArrayList<>();
                threeSumSolution.add(nums[currentPosition]);
                threeSumSolution.addAll(twoSum);
                solutions.add(threeSumSolution);
            }

            Optional<Integer> nextPosition = computeNextLeft(currentPosition, nums);
            if (nextPosition.isEmpty()) {
                return solutions;
            }

            currentPosition = nextPosition.get();
        }

        return solutions;
    }

    public List<List<Integer>> twoSum(int[] nums, int start, int target) {
        List<List<Integer>> solutions = new ArrayList<>();

        int left = start;
        int right = nums.length - 1;

        while (left < right) {
            int currentSum = nums[left] + nums[right];
            if (currentSum == target) {
                solutions.add(List.of(nums[left], nums[right]));
                Optional<Integer> nextLeft = computeNextLeft(left, nums);
                if (nextLeft.isEmpty()) {
                    return solutions;
                }

                left = nextLeft.get();
            } else if (currentSum < target) {
                left++;
            } else {
                right--;
            }
        }

        return solutions;
    }

    private Optional<Integer> computeNextLeft(int currentLeft, int[] nums) {
        for (int i = currentLeft + 1; i < nums.length; i++) {
            if (nums[currentLeft] != nums[i]) {
                return Optional.of(i);
            }
        }

        return Optional.empty();
    }
}
