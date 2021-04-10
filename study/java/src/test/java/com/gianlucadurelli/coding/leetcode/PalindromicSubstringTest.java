package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class PalindromicSubstringTest {

	@Test
	public void palindromicSubstrings() {
		PalindromicSubstring palindromicSubstring = new PalindromicSubstring();
		Assertions.assertThat(palindromicSubstring.solve("abc")).isEqualTo(3);
		Assertions.assertThat(palindromicSubstring.solve("aaa")).isEqualTo(6);
	}
}