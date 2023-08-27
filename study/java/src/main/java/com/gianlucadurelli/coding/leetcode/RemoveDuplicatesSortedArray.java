package com.gianlucadurelli.coding.leetcode;

// https://leetcode.com/problems/remove-duplicates-from-sorted-array/?envType=study-plan-v2&envId=top-interview-150
public class RemoveDuplicatesSortedArray {
    public int removeDuplicates(int[] nums) {
        if (nums == null) {
            return 0;
        }

        if (nums.length < 2) {
            return nums.length;
        }

        int currentWritingPos = 1;
        for(int currentReadingPos = 1; currentReadingPos < nums.length; currentReadingPos++) {
            if (nums[currentReadingPos] != nums[currentWritingPos - 1]) {
                nums[currentWritingPos] = nums[currentReadingPos];
                currentWritingPos++;
            }
        }

        return currentWritingPos;
    }
}
