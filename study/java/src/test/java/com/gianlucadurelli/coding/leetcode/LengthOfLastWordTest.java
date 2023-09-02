package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class LengthOfLastWordTest {

    private static final LengthOfLastWord solver = new LengthOfLastWord();

    @Test
    public void example1() {
        Assertions.assertThat(solver.lengthOfLastWord("Hello World")).isEqualTo(5);
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.lengthOfLastWord("   fly me   to   the moon  ")).isEqualTo(4);
    }

    @Test
    public void example3() {
        Assertions.assertThat(solver.lengthOfLastWord("luffy is still joyboy")).isEqualTo(6);
    }

}