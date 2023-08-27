package com.gianlucadurelli.coding.leetcode;

public class RemoveElement {
    public int removeElement(int[] nums, int val) {
        int toRemove = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                toRemove++;
            }
        }

        int i = 0;
        int j = nums.length - 1;


        while(i < j) {
            while( j >= 0 && nums[j] == val) {
                j--;
            }

            if (i >= j) {
                break;
            }

            if (nums[i] == val) {
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
                i++;
                j--;
            } else {
                i++;
            }
        }

        return nums.length - toRemove;
    }
}
