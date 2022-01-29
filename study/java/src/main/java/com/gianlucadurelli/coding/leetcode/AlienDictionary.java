package com.gianlucadurelli.coding.leetcode;

import java.util.*;

public class AlienDictionary {
	public String alienOrder(String[] words) {
		HashMap<String, Set<String>> adjacencyList = new HashMap<>();
		HashMap<String, Set<String>> inEdges = new HashMap<>();
		for (String w: words) {
			for (char c: w.toCharArray()) {
				String l = String.valueOf(c);
				if (!adjacencyList.containsKey(l)) {
					adjacencyList.put(l, new HashSet<>());
					inEdges.put(l, new HashSet<>());
				}
			}
		}

		for (int i = 0; i < words.length - 1; i++) {
			String w1 = words[i];
			String w2 = words[i + 1];

			int minLetters = Math.min(w1.length(), w2.length());
			int l = 0;
			for (; l < minLetters; l++) {
				String l1 = String.valueOf(w1.charAt(l));
				String l2 = String.valueOf(w2.charAt(l));

				if (!l1.equals(l2)) {
					adjacencyList.get(l1).add(l2);
					inEdges.get(l2).add(l1);
					break;
				}
			}

			if (l == minLetters && w2.length() < w1.length()) {
				return "";
			}
		}

		return topologicalSort(adjacencyList, inEdges);
	}

	private String topologicalSort(HashMap<String, Set<String>> adjList, HashMap<String, Set<String>> inEdges) {
		String solution = "";
		HashSet<String> visited = new HashSet<>();
		Deque<String> visit = new LinkedList<>();
		for (String s: inEdges.keySet()) {
			if (inEdges.get(s).isEmpty()) {
				visit.push(s);
				visited.add(s);
			}
		}

		while (!visit.isEmpty()) {
			String l = visit.pop();
			solution += l;

			for (String s: adjList.get(l)) {
				inEdges.get(s).remove(l);
			}

			for (String s: inEdges.keySet()) {
				if (inEdges.get(s).isEmpty() && !visited.contains(s)) {
					visit.push(s);
					visited.add(s);
				}
			}
		}

		// Loop detection
		for (String s: inEdges.keySet()) {
			if (!inEdges.get(s).isEmpty()) {
				return "";
			}
		}

		return solution;
	}
}
