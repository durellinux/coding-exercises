package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class BestTimeBuyStockTest {
	BestTimeBuyStock solver = new BestTimeBuyStock();

	@Test
	public void solve1Test() {
		List<Integer> example = List.of(6, 4, 1, 2, 5, 8, 4, 7);
		Integer result = solver.solve1(example);

		Assertions.assertThat(result).isEqualTo(7);
	}

	@Test
	public void solve2Test() {
		List<Integer> example = List.of(7,1,5,3,6,4);
		Integer result = solver.solve2(example);

		Assertions.assertThat(result).isEqualTo(5);
	}

}