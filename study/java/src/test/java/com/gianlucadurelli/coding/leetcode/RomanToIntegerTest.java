package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class RomanToIntegerTest {

    private static final RomanToInteger solver = new RomanToInteger();

    @Test
    public void example1() {
        Assertions.assertThat(
                solver.romanToInt("III")
        ).isEqualTo(3);
    }

    @Test
    public void example2() {
        Assertions.assertThat(
                solver.romanToInt("LVIII")
        ).isEqualTo(58);
    }

    @Test
    public void example3() {
        Assertions.assertThat(
                solver.romanToInt("MCMXCIV")
        ).isEqualTo(1994);
    }

}