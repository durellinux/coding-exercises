package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GroupAnagramsTest {

	@Test
	public void test() {
		GroupAnagrams solver = new GroupAnagrams();
		List<List<String>> solution = solver.groupAnagrams2(new String[]{"eat","tea","tan","ate","nat","bat"});
		System.out.println(solution);
		// TODO: Enable this check
//		Assertions.assertThat(solution).containsExactlyInAnyOrderElementsOf(List.of(
//				List.of("bat"),
//				List.of("nat","tan"),
//				List.of("ate","eat","tea")
//		));

		List<List<String>> solution2 = solver.groupAnagrams2(new String[]{""});
		Assertions.assertThat(solution2).containsExactlyInAnyOrderElementsOf(List.of(
				List.of("")
		));

		List<List<String>> solution3 = solver.groupAnagrams2(new String[]{"a"});
		Assertions.assertThat(solution3).containsExactlyInAnyOrderElementsOf(List.of(
				List.of("a")
		));

		List<List<String>> solution4 = solver.groupAnagrams(new String[]{});
		Assertions.assertThat(solution4).isEmpty();
	}
}