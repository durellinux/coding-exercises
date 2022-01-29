package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlienDictionaryTest {

	@Test
	public void test1() {
		AlienDictionary solver = new AlienDictionary();

		String solution1 = solver.alienOrder(new String[]{"wrt","wrf","er","ett","rftt"});
		Assertions.assertThat(solution1).isEqualTo("wertf");

		String solution2 = solver.alienOrder(new String[]{"x","z"});
		Assertions.assertThat(solution2).isEqualTo("xz");

		String solution3 = solver.alienOrder(new String[]{"z","x","z"});
		Assertions.assertThat(solution3).isEqualTo("");

		String solution5 = solver.alienOrder(new String[]{"aac","aabb","aaba"});
		Assertions.assertThat(solution5).isEqualTo("cba");

		String solution6 = solver.alienOrder(new String[]{"zx","zy"});
		Assertions.assertThat(solution6).isEqualTo("zxy");

		String solution7 = solver.alienOrder(new String[]{"ac","ab","zc","zb"});
		Assertions.assertThat(solution7).isEqualTo("cbaz");

		String solution8 = solver.alienOrder(new String[]{"ac","ab","b"});
		Assertions.assertThat(solution8).isEqualTo("cab");

		String solution9 = solver.alienOrder(new String[]{"abc","ab"});
		Assertions.assertThat(solution9).isEqualTo("");

		String solution10 = solver.alienOrder(new String[]{"ri","xz","qxf","jhsguaw","dztqrbwbm","dhdqfb","jdv","fcgfsilnb","ooby"});
		Assertions.assertThat(solution10).isEqualTo("");

		String solution11 = solver.alienOrder(new String[]{"a","b","ca","cc"});
		Assertions.assertThat(solution11).isEqualTo("abc");
	}

}