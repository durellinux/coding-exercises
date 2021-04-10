package com.gianlucadurelli.coding.leetcode.contest38;

import java.util.HashMap;
import java.util.Map;

public class pippo {
	public int numWays(String[] words, String target) {
		Map<Integer, Map<Character, Integer>> map = new HashMap<>();

		for(String w: words) {
			for (int i = 0; i < words[0].length(); i++) {
				if (!map.containsKey(i)) {
					map.put(i, new HashMap<>());
				}

				if (!map.get(i).containsKey(w.charAt(i))) {
					map.get(i).put(w.charAt(i), 0);
				}

				int v = map.get(i).get(w.charAt(i));
				map.get(i).put(w.charAt(i), v + 1);
			}
		}
		long tmpResult = solve(target, 0, words[0].length(), map);
		return (int) ( tmpResult % (Math.pow(10, 9) + 7));
	}

	private long solve(String target, int pos, int posMax, Map<Integer, Map<Character, Integer>> map) {
		if (target.equals("")) {
			return 1;
		}

		if (pos >= posMax) {
			return 0;
		}

		long solutions = 0;
		for(int p = pos; p < posMax; p++) {
			if (map.get(p).containsKey(target.charAt(0))) {
				long tmpResult = map.get(p).get(target.charAt(0)) * solve(target.substring(1), p + 1, posMax, map);
				solutions += tmpResult;
			}
		}

		System.out.println(solutions);

		return solutions;
	}
}
