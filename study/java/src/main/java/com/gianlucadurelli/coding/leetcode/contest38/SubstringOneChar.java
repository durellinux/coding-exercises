package com.gianlucadurelli.coding.leetcode.contest38;

public class SubstringOneChar {
	public int countSubstrings(String s, String t) {
		int result = 0;
		for (int ss = 0; ss < s.length(); ss++) {

			String s2 = s.substring(ss);
			for (int ti = 0; ti < t.length(); ti++) {
				int diffs = 0;
				for (int s2i = 0; s2i < s2.length() && diffs < 2 && ti + s2i < t.length(); s2i++) {
					if (s2.charAt(s2i) != t.charAt(ti + s2i)) {
						diffs ++;
					}
					if (diffs == 1) {
						result += 1;
					}
				}
			}
		}

		return result;
	}
}
