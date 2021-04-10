package com.gianlucadurelli.coding.leetcode.contest38;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class SortArrayDecTest {

	SortArrayDec solve = new SortArrayDec();

	@Test
	public void test() {
		int[] result = solve.frequencySort(new int[] {-1,1,-6,4,5,-6,1,4,1});
		Assertions.assertThat(result).isEqualTo(new int[] {5,-1,4,4,-6,-6,1,1,1});
	}

}