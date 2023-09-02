package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ReverseWordsTest {

    private static final ReverseWords solver = new ReverseWords();

    @Test
    public void example1() {
        Assertions.assertThat(
                solver.reverseWords("the sky is blue")
        ).isEqualTo("blue is sky the");
    }

    @Test
    public void example2() {
        Assertions.assertThat(
                solver.reverseWords("  hello world  ")
        ).isEqualTo("world hello");
    }

    @Test
    public void example3() {
        Assertions.assertThat(
                solver.reverseWords("a good   example")
        ).isEqualTo("example good a");
    }

}