package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinimumWindowSubstringTest {

    private static final MinimumWindowSubstring solver = new MinimumWindowSubstring();

    @Test
    public void example1() {
        Assertions.assertThat(solver.minWindow("ADOBECODEBANC", "ABC"))
                .isEqualTo("BANC");
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.minWindow("a", "a"))
                .isEqualTo("a");
    }

    @Test
    public void example3() {
        Assertions.assertThat(solver.minWindow("a", "aa"))
                .isEqualTo("");
    }
}