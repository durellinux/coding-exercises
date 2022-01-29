package com.gianlucadurelli.coding.leetcode;

import java.util.*;

public class RemoveInvalidParenthesis {
	static class Tuple<A, B> {
		A v1;
		B v2;

		public Tuple(A v1, B v2) {
			this.v1 = v1;
			this.v2 = v2;
		}
	}

	Tuple<Integer, Set<String>> solution = null;

	public List<String> removeInvalidParentheses(String s) {
		recursiveSolver(s, 0, 0);
		if (Objects.nonNull(solution)) {
			return new LinkedList<>(solution.v2);
		}
		return List.of("");
	}

	private void recursiveSolver(String s, int removed, int pointer) {
		if (Objects.nonNull(solution) && removed > solution.v1) {
			return;
		}

		if (pointer >= s.length()) {
			if (isValid(s)) {
				if (solution == null) {
					Set<String> value = new HashSet<>();
					value.add(s);
					solution = new Tuple<>(removed, value);
				} else if (solution.v1 == removed) {
					solution.v2.add(s);
				} else {
					Set<String> value = new HashSet<>();
					value.add(s);
					solution.v1 = removed;
					solution.v2 = value;
				}
			}
			return;
		}

		recursiveSolver(s, removed, pointer + 1);

		StringBuilder sb = new StringBuilder(s);
		sb.deleteCharAt(pointer);
		recursiveSolver(sb.toString(), removed + 1, pointer);
	}

	private boolean isValid(String s) {
		int count = 0;
		for (Character c: s.toCharArray()) {
			if (c == '(') {
				count ++;
			} else if (c == ')') {
				count --;
			}

			if (count < 0) {
				return false;
			}
		}

		return count == 0;
	}
}
