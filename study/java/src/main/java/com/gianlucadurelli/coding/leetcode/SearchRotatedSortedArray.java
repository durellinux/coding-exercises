package com.gianlucadurelli.coding.leetcode;

public class SearchRotatedSortedArray {
	public int search(int[] nums, int target) {
		if (nums.length == 0) {
			return -1;
		}
		if (nums.length == 1) {
			return nums[0] == target ? 0 : -1;
		}

		int pivotPosition = searchPivot(nums);
		int minR = nums[pivotPosition];
		int maxR = nums[nums.length - 1];

		if (target >= minR && target <= maxR) {
			return binarySearch(nums, target, pivotPosition, nums.length - 1);
		} else {
			return binarySearch(nums, target, 0, pivotPosition - 1);
		}
	}

	public int searchPivot(int[] nums) {
		int minP = 0;
		int maxP = nums.length - 1;

		if (nums[minP] < nums[maxP]) {
			return minP;
		}

		while(maxP - minP > 1) {
			int curP = (minP + maxP) / 2;
			int maxV = nums[minP];
			int minV = nums[maxP];
			int v = nums[curP];

			if (v >= maxV) {
				minP = curP;
			} else {
				maxP = curP;
			}
		}

		return maxP;
	}

	public int binarySearch(int[] nums, int target, int start, int end) {
		if (start < 0 || end < 0) {
			return -1;
		}

		if (nums[start] == target) {
			return start;
		}

		if (nums[end] == target) {
			return end;
		}

		while(end - start > 1) {
			int p = (start + end) / 2;
			int v = nums[p];

			if (v == target) {
				return p;
			} else if (v > target) {
				end = p;
			} else {
				start = p;
			}
		}

		return -1;
	}
}
