package com.gianlucadurelli.coding.leetcode.contest38;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class WidestAreaTest {
	WidestArea solve = new WidestArea();

	@Test
	public void test() {
		int result = solve.maxWidthOfVerticalArea(new int[][] {{8,7},{9,9},{7,4},{9,7}});
		Assertions.assertThat(result).isEqualTo(1);

		result = solve.maxWidthOfVerticalArea(new int[][] {{3,1},{9,0},{1,0},{1,4},{5,3},{8,8}});
		Assertions.assertThat(result).isEqualTo(3);
	}
}