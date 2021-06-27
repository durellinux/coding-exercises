package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;


public class ProductOfArrayExceptSelfTest {

	@Test
	public void test() {
		ProductOfArrayExceptSelf solver = new ProductOfArrayExceptSelf();

		Assertions.assertThat(solver.productExceptSelf(new int[]{1, 2, 3, 4})).isEqualTo(new int[]{24, 12, 8, 6});
		Assertions.assertThat(solver.productExceptSelf(new int[]{-1,1,0,-3,3})).isEqualTo(new int[]{0,0,9,0,0});

		Assertions.assertThat(solver.productExceptSelfFollowup(new int[]{1, 2, 3, 4})).isEqualTo(new int[]{24, 12, 8, 6});
		Assertions.assertThat(solver.productExceptSelfFollowup(new int[]{-1,1,0,-3,3})).isEqualTo(new int[]{0,0,9,0,0});
	}
}