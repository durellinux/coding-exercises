package com.gianlucadurelli.coding.leetcode.contest38;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubstringOneCharTest {

	@Test
	public void test() {
		SubstringOneChar solver = new SubstringOneChar();
		int result = solver.countSubstrings("aba", "baba");
		Assertions.assertThat(result).isEqualTo(6);

		result = solver.countSubstrings("ab", "bb");
		Assertions.assertThat(result).isEqualTo(3);

		result = solver.countSubstrings("a", "a");
		Assertions.assertThat(result).isEqualTo(0);

		result = solver.countSubstrings("abe", "bbc");
		Assertions.assertThat(result).isEqualTo(10);
	}
}