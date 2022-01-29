package com.gianlucadurelli.coding.leetcode;

public class PalindromicSubstring {
	public int solve(String s) {
		int lenght = s.length();
		int palindromicSubstrings = 0;

		for (int i = 1; i <= lenght; i++) {
			palindromicSubstrings += countSubstringsWithLenght(s, i);
		}

		return palindromicSubstrings;
	}

	private int countSubstringsWithLenght(String string, int lenght) {
		int palindromes = 0;

		int stringLenght = string.length();
		for(int i = 0; i + lenght <= stringLenght; i++) {
			String curString = string.substring(i, i + lenght);
			palindromes += isPalindrome(curString) ? 1 : 0;
		}

		return palindromes;
	}

	private boolean isPalindrome(String curString) {
		int i = 0;
		int j = curString.length() - 1;

		while(i < j) {
			if (curString.charAt(i) != curString.charAt(j)) {
				return false;
			}

			i++;
			j--;
		}

		return true;
	}
}
