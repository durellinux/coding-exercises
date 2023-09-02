package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LongestCommonPrefixTest {

    private static final LongestCommonPrefix solver = new LongestCommonPrefix();

    @Test
    public void exmaple1() {
        Assertions.assertThat(
                solver.longestCommonPrefix(new String[]{"flower","flow","flight"})
        ).isEqualTo("fl");
    }

    @Test
    public void exmaple2() {
        Assertions.assertThat(
                solver.longestCommonPrefix(new String[]{"dog","racecar","car"})
        ).isEqualTo("");
    }

}