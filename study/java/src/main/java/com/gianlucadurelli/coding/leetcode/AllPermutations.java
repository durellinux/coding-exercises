package com.gianlucadurelli.coding.leetcode;

import java.util.*;

//https://leetcode.com/explore/interview/card/facebook/53/recursion-3/292
public class AllPermutations {
	public List<List<Integer>> permute(int[] nums) {
		Queue<List<Integer>> solution = new LinkedList<>();

		if (Objects.isNull(nums) || nums.length == 0) {
			return new ArrayList<>();
		}

		List<Integer> initial = new ArrayList<>();
		initial.add(nums[0]);
		solution.add(initial);

		for(int i = 1; i < nums.length; i++) {
			int currentSolutions = solution.size();
			for(int s = 0; s < currentSolutions; s ++) {
				List<Integer> prevSol = solution.remove();
				for(int p = 0; p <= prevSol.size(); p++) {
					List<Integer> newSol = new ArrayList<>(prevSol);
					newSol.add(p, nums[i]);
					solution.add(newSol);
				}
			}
		}

		return new LinkedList<>(solution);
	}
}
