package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class SearchRotatedSortedArrayTest {

	@Test
	public void test() {
		SearchRotatedSortedArray solver = new SearchRotatedSortedArray();
		Assertions.assertThat(solver.search(new int[]{4,5,6,7,0,1,2}, 0)).isEqualTo(4);
		Assertions.assertThat(solver.search(new int[]{4,5,6,7,0,1,2}, 1)).isEqualTo(5);
		Assertions.assertThat(solver.search(new int[]{4,5,6,7,0,1,2}, 5)).isEqualTo(1);
		Assertions.assertThat(solver.search(new int[]{4,5,6,7,0,1,2}, 3)).isEqualTo(-1);
		Assertions.assertThat(solver.search(new int[]{1, 3, 5}, 3)).isEqualTo(1);
		Assertions.assertThat(solver.search(new int[]{1, 3}, 1)).isEqualTo(0);
		Assertions.assertThat(solver.search(new int[]{1, 3}, 3)).isEqualTo(1);
		Assertions.assertThat(solver.search(new int[]{3, 1}, 1)).isEqualTo(1);
		Assertions.assertThat(solver.search(new int[]{3, 1}, 3)).isEqualTo(0);
		Assertions.assertThat(solver.search(new int[]{1}, 0)).isEqualTo(-1);
	}

}