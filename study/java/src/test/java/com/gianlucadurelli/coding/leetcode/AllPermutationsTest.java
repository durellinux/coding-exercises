package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AllPermutationsTest {

	@Test
	public void test() {
		AllPermutations solver = new AllPermutations();
		List<List<Integer>> solution = solver.permute(new int[]{1,2,3});
		Assertions.assertThat(solution).containsExactlyInAnyOrderElementsOf(List.of(
				List.of(1,2,3),
				List.of(1,3,2),
				List.of(2,1,3),
				List.of(2,3,1),
				List.of(3,1,2),
				List.of(3,2,1)
		));
	}
}