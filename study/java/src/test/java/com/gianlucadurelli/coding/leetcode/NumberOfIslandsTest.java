package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class NumberOfIslandsTest {

	@Test
	public void test1() {
		NumberOfIslands solver = new NumberOfIslands();

		char[][] testCase1 = new char[][]{
		  new char[]{'1','1','1','1','0'},
		  new char[]{'1','1','0','1','0'},
		  new char[]{'1','1','0','0','0'},
		  new char[]{'0','0','0','0','0'}
		};

		int solution1 = solver.numIslands(testCase1);
		Assertions.assertThat(solution1).isEqualTo(1);
	}

	@Test
	public void test2() {
		NumberOfIslands solver = new NumberOfIslands();

		char[][] testCase1 = new char[][]{
				new char[]{'1','1','0','0','0'},
				new char[]{'1','1','0','0','0'},
				new char[]{'0','0','1','0','0'},
				new char[]{'0','0','0','1','1'}
		};

		int solution1 = solver.numIslands(testCase1);
		Assertions.assertThat(solution1).isEqualTo(3);
	}

}