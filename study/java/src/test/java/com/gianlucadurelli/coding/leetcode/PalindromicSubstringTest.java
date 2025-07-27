package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PalindromicSubstringTest {

	@Test
	public void palindromicSubstrings() {
		PalindromicSubstring palindromicSubstring = new PalindromicSubstring();
		Assertions.assertThat(palindromicSubstring.solve("abc")).isEqualTo(3);
		Assertions.assertThat(palindromicSubstring.solve("aaa")).isEqualTo(6);
	}
}