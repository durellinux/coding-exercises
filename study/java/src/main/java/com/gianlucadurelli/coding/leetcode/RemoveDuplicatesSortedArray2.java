package com.gianlucadurelli.coding.leetcode;

// https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/?envType=study-plan-v2&envId=top-interview-150
public class RemoveDuplicatesSortedArray2 {
    public int removeDuplicates(int[] nums) {
        if (nums == null) {
            return 0;
        }

        if (nums.length < 2) {
            return nums.length;
        }

        int currentWritingPos = 1;
        int count = 1;

        for(int currentReadingPos = 1; currentReadingPos < nums.length; currentReadingPos++) {
            if (nums[currentReadingPos] != nums[currentWritingPos - 1]) {
                currentWritingPos = writeValue(nums, currentReadingPos, currentWritingPos);
                count = 1;
            } else if(count == 1) {
                currentWritingPos = writeValue(nums, currentReadingPos, currentWritingPos);
                count++;
            }
        }

        return currentWritingPos;
    }

    private int writeValue(int[] nums, int currentReadingPos, int currentWritingPos) {
        nums[currentWritingPos] = nums[currentReadingPos];
        return currentWritingPos + 1;
    }
}
