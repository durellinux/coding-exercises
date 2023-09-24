package com.gianlucadurelli.coding.leetcode;

import java.util.Objects;
import java.util.Optional;

// https://leetcode.com/problems/container-with-most-water/description/?envType=study-plan-v2&envId=top-interview-150
public class ContainerWithMostWater {
    public int maxArea(int[] height) {
        if (Objects.isNull(height) || height.length < 2) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int maxArea = computeArea(left, right, height);

        while(left < right) {
            int currentArea = computeArea(left, right, height);
            if (currentArea > maxArea) {
                maxArea = currentArea;
            }

            if (height[left] < height[right]) {
                Optional<Integer> nextLeft = tryIncreaseLeft(left, height);
                if (nextLeft.isEmpty()) {
                    return maxArea;
                }
                left = nextLeft.get();
            } else if (height[right] < height[left]) {
                Optional<Integer> nextRight = tryIncreaseRight(right, height);
                if (nextRight.isEmpty()) {
                    return maxArea;
                }
                right = nextRight.get();
            } else {
                Optional<Integer> nextLeft = tryIncreaseLeft(left, height);
                Optional<Integer> nextRight = tryIncreaseRight(right, height);
                if (nextLeft.isEmpty() || nextRight.isEmpty()) {
                    return maxArea;
                }

                int leftDiff = nextLeft.get() - left;
                int rightDiff = nextRight.get() - right;

                if (leftDiff <= rightDiff) {
                    left = nextLeft.get();
                } else {
                    right = nextRight.get();
                }
            }
        }

        return maxArea;
    }

    private int computeArea(int left, int right, int[] heights) {
        int height = Math.min(heights[left], heights[right]);
        int base = right - left;

        return base * height;
    }

    private Optional<Integer> tryIncreaseLeft(int currentLeft, int[] heights) {
        for (int index = currentLeft + 1; index < heights.length; index++) {
            if (heights[index] > heights[currentLeft]) {
                return Optional.of(index);
            }
        }

        return Optional.empty();
    }

    private Optional<Integer> tryIncreaseRight(int currentRight, int[] heights) {
        for (int index = currentRight - 1; index >=0 ; index--) {
            if (heights[index] > heights[currentRight]) {
                return Optional.of(index);
            }
        }

        return Optional.empty();
    }
}
