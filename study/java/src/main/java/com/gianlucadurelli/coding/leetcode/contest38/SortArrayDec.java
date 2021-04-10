package com.gianlucadurelli.coding.leetcode.contest38;

import java.util.*;

public class SortArrayDec {
	public int[] frequencySort(int[] nums) {
		Map<Integer, Integer> freqs = new HashMap<>();
		Map<Integer, List<Integer>> freqToNum = new HashMap<>();

		for(Integer n: nums) {
			if (!freqs.containsKey(n)) {
				freqs.put(n, 0);
			}
			Integer v = freqs.get(n);
			freqs.put(n, v + 1);
		}

		for(Map.Entry<Integer, Integer> entry: freqs.entrySet()) {
			Integer v = entry.getKey();
			Integer f = entry.getValue();

			if (!freqToNum.containsKey(f)) {
				freqToNum.put(f, new ArrayList<>());
			}

			freqToNum.get(f).add(v);
		}

		List<Integer> sortedFreqs = new ArrayList<Integer>(freqToNum.keySet());
		Collections.sort(sortedFreqs);


		List<Integer> result = new ArrayList<>();
		for(Integer freq: sortedFreqs) {
			List<Integer> numbers = freqToNum.get(freq);
			Collections.sort(numbers);
			Collections.reverse(numbers);
			for (Integer n: numbers) {
				for(int i = 0; i < freq; i++) {
					result.add(n);
				}
			}
		}

		return result.stream().mapToInt(i -> i).toArray();
	}
}
