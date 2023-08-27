package com.gianlucadurelli.coding.leetcode;

import java.util.Arrays;
import java.util.Map;

// https://leetcode.com/problems/rotate-array/?envType=study-plan-v2&envId=top-interview-150
public class RotateArray {
    public void rotate(int[] nums, int K) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int k = K % nums.length;
        int firstIndex = nums.length - k;

        for(int i = 0; i < firstIndex; i++) {
            int swapPosition = i + firstIndex;
            if (swapPosition >= nums.length) {
                swapPosition = firstIndex + nums.length % firstIndex;
            }

            int tmp = nums[swapPosition];
            nums[swapPosition] = nums[i];
            nums[i] = tmp;
        }
    }

    public void rotateV4(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int rotations = k % nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, rotations - 1);
        reverse(nums, rotations, nums.length - 1);
    }

    private void reverse(int[] array, int start, int end) {
        while(start < end) {
            int tmp = array[end];
            array[end] = array[start];
            array[start] = tmp;
            start++;
            end--;
        }
    }

    public void rotateV3(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int rotations = k % nums.length;
        int gcd = computeGcd(nums.length, rotations);

        for (int g = 0; g < gcd; g++) {
            for (int i = 0; i < nums.length/gcd - 1; i++) {
                int valueToMove = nums[g];
                int destination = (g + rotations * i + rotations) % nums.length;
                int nextValueToMove = nums[destination];

                nums[g] = nextValueToMove;
                nums[destination] = valueToMove;
            }
        }
    }

    private int computeGcd(int a, int b) {
        int t;
        while (b != 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    public void rotateV2(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int rotations = k % nums.length;
        int[] result = new int[nums.length];

        for(int n = 0; n < nums.length; n++) {
            int newPosition = (n + rotations) % nums.length;
            result[newPosition] = nums[n];
        }

        System.arraycopy(result, 0, nums, 0, nums.length);
    }

    public void rotateV1(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int rotations = k % nums.length;

        for(int i = 0; i < rotations; i++) {
            rotate1(nums);
        }
    }

    private void rotate1(int[] nums) {
        int val = nums[0];
        for(int i = 1; i < nums.length; i++) {
            int tmp = nums[i];
            nums[i] = val;
            val = tmp;
        }
        nums[0] = val;
    }
}
