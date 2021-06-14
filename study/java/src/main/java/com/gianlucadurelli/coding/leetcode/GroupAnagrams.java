package com.gianlucadurelli.coding.leetcode;

import java.util.*;

public class GroupAnagrams {
	public List<List<String>> groupAnagrams(String[] strs) {
		HashMap<HashMap<Character, Integer>, List<String>> lettersToWords = new HashMap<>();
		for (String s: strs) {
			HashMap<Character, Integer> lettersCount = new HashMap<>();
			for (Character c: s.toCharArray()) {
				Integer prevCount = lettersCount.getOrDefault(c, 0);
				lettersCount.put(c, prevCount + 1);
			}

			List<String> prevWords = lettersToWords.getOrDefault(lettersCount, new ArrayList<>());
			prevWords.add(s);
			lettersToWords.put(lettersCount, prevWords);
		}

		return new LinkedList<>(lettersToWords.values());
	}

	public List<List<String>> groupAnagrams2(String[] strs) {
		HashMap<String, List<String>> lettersToWords = new HashMap<>();
		for (String s: strs) {
			String sorted = sort(s);

			List<String> prevWords = lettersToWords.getOrDefault(sorted, new ArrayList<>());
			prevWords.add(s);
			lettersToWords.put(sorted, prevWords);
		}

		return new LinkedList<>(lettersToWords.values());
	}

	private String sort(String s) {
		char[] chars = s.toCharArray();
		Arrays.sort(chars);
		return new String(chars);
	}
}
