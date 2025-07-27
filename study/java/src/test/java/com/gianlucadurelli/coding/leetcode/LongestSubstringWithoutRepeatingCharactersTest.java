package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LongestSubstringWithoutRepeatingCharactersTest {

    private static final LongestSubstringWithoutRepeatingCharacters solver = new LongestSubstringWithoutRepeatingCharacters();

    @Test
    public void example1() {
        Assertions.assertThat(solver.lengthOfLongestSubstring("abcabcbb"))
                .isEqualTo(3);
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.lengthOfLongestSubstring("bbbbbb"))
                .isEqualTo(1);
    }

    @Test
    public void example3() {
        Assertions.assertThat(solver.lengthOfLongestSubstring("pwwkew"))
                .isEqualTo(3);
    }

    @Test
    public void failed1() {
        Assertions.assertThat(solver.lengthOfLongestSubstring(" "))
                .isEqualTo(1);
    }

}