package com.gianlucadurelli.coding.leetcode;

//https://leetcode.com/explore/interview/card/facebook/5/array-and-strings/3016/
public class ProductOfArrayExceptSelf {

	public int[] productExceptSelf(int[] nums) {
		if (nums.length < 2) {
			return nums;
		}

		int[] result = new int[nums.length];
		int[] prefix = new int[nums.length];
		int[] suffix = new int[nums.length];

		prefix[0] = 1;
		suffix[nums.length - 1] = 1;

		for (int i = 1; i < nums.length; i++) {
			int vp = nums[i - 1];
			int prevP = prefix[i - 1];
			prefix[i] = prevP * vp;

			int vs = nums[nums.length - 1 - i + 1];
			int prevS = suffix[nums.length - 1 - i + 1];
			suffix[nums.length - 1 - i] = prevS * vs;
		}

		for (int i = 0; i < nums.length; i++) {
			int p = prefix[i];
			int s = suffix[i];
			result[i] = p * s;
		}

		return result;
	}


	public int[] productExceptSelfFollowup(int[] nums) {
		if (nums.length < 2) {
			return nums;
		}

		int[] result = new int[nums.length];

		result[0] = 1;

		int prefix = nums[0];
		for (int i = 1; i < nums.length; i++) {
			result[i] = prefix;
			prefix = prefix * nums[i];
		}

		int suffix = nums[nums.length - 1];
		for (int i = nums.length - 2; i >= 0; i--) {
			result[i] = result[i] * suffix;
			suffix = suffix * nums[i];
		}

		return result;
	}
}
