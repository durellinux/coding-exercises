package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class IsSubsequenceTest {

    private static final IsSubsequence solver = new IsSubsequence();

    @Test
    public void example1() {
        Assertions.assertThat(solver.isSubsequence("abc", "ahbgdc")).isTrue();
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.isSubsequence("axc", "ahbgdc")).isFalse();
    }

}