package com.gianlucadurelli.coding.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BestTimeBuyStock {

	class SolutionHelper {
		int min;
		int max;
		int value;

		public SolutionHelper(int min, int max, int value) {
			this.min = min;
			this.max = max;
			this.value = value;
		}

		@Override
		public String toString() {
			return "Value: " + value + " [" + min + ", " + max + "]";
		}
	}

	public int solveArray(Integer[] prices) {
		return solve2(Arrays.asList(prices));
	}

	public int solve2(List<Integer> prices) {
		return solveRecursive2(prices, 0,prices.size() - 1).value;
	}

	public SolutionHelper solveRecursive2(List<Integer> prices, int start, int end) {
		if (start == end) {
			SolutionHelper solution = new SolutionHelper(prices.get(start), prices.get(start), 0);
			System.out.println("[" + start + ", " + end + "] = " + solution);
			return solution;
		}

		if (start == end - 1) {
			SolutionHelper solution = new SolutionHelper(Math.min(prices.get(start), prices.get(end)), Math.max(prices.get(start), prices.get(end)), Math.max(0, prices.get(end) - prices.get(start)));
			System.out.println("[" + start + ", " + end + "] = " + solution);
			return solution;
		}

		int half = (start + end) / 2;
		SolutionHelper subSol1 = solveRecursive2(prices, start, half);
		SolutionHelper subSol2 = solveRecursive2(prices, half + 1, end);

		int min = subSol1.min;
		int max = subSol2.max;
		int value = Math.max(0, max - min);

		SolutionHelper solution = new SolutionHelper(Math.min(subSol1.min, subSol2.min), Math.max(subSol1.max, subSol2.max), Math.max(value, Math.max(subSol1.value, subSol2.value)));
		System.out.println("[" + start + ", " + end + "] = " + solution);

		return solution;
	}

	public int solve1(List<Integer> prices) {
		return solveRecursive1(prices, 0, prices.size() - 1);
	}

	public int solveRecursive1(List<Integer> prices, int start, int end) {

		if (start == end) {
			return 0;
		}

		if (start == end - 1) {
			return Math.max(0, prices.get(end) - prices.get(start));
		}

		int half = (start + end) / 2;

		int minSx = prices.get(start);
		for (int i = start; i <= half; i++) {
			if (prices.get(i) < minSx) {
				minSx = prices.get(i);
			}
		}

		int maxDx = prices.get(half + 1);
		for (int i = half + 1; i <= end; i++) {
			if (prices.get(i) > maxDx) {
				maxDx = prices.get(i);
			}
		}

		int curSol = Math.max(0, maxDx - minSx);

		int subSol1 = solveRecursive1(prices, start, half);
		int subSol2 = solveRecursive1(prices, half + 1, end);

		return Math.max(curSol, Math.max(subSol1, subSol2));
	}
}
