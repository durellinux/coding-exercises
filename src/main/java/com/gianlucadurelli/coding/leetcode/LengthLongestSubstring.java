package com.gianlucadurelli.coding.leetcode;

public class LengthLongestSubstring {
	public int solve(String s) {
		int[] chars = new int[128];

		if (s.isEmpty()) {
			return 0;
		}

		clearChars(chars);

		int maxSubstringLength = 0;
		int substringLength = 0;
		char[] stringChar = s.toCharArray();
		for (char c: stringChar) {
			if (chars[c] == 0) {
				substringLength++;
				chars[c] = substringLength;
			} else {
				if (substringLength > maxSubstringLength) {
					maxSubstringLength = substringLength;
				}

				int oldPosition = chars[c];
				resetChars(chars, oldPosition);
				substringLength = substringLength - oldPosition + 1;
				chars[c] = substringLength;
			}
		}

		if (substringLength > maxSubstringLength) {
			maxSubstringLength = substringLength;
		}

		return maxSubstringLength;
	}

	public void clearChars(int[] chars) {
		for(int i = 0; i < chars.length; i++) {
			chars[i] = 0;
		}
	}

	public void resetChars(int[] chars, int duplicatePosition) {
		for(int i = 0; i < chars.length; i++) {
			chars[i] = Math.max(0, chars[i] - duplicatePosition);
		}
	}
}
