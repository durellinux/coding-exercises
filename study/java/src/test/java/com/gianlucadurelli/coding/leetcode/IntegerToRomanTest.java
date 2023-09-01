package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerToRomanTest {

    private static final IntegerToRoman solver = new IntegerToRoman();

    @Test
    public void example1() {
        Assertions.assertThat(solver.intToRoman(3)).isEqualTo("III");
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.intToRoman(58)).isEqualTo("LVIII");
    }

    @Test
    public void example3() {
        Assertions.assertThat(solver.intToRoman(1994)).isEqualTo("MCMXCIV");
    }
}