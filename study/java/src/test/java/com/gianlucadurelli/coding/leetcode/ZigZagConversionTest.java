package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZigZagConversionTest {

    private static final ZigZagConversion solver = new ZigZagConversion();

    @Test
    public void example1() {
        Assertions.assertThat(
                solver.convert("PAYPALISHIRING", 3)
        ).isEqualTo("PAHNAPLSIIGYIR");
    }

    @Test
    public void example2() {
        Assertions.assertThat(
                solver.convert("PAYPALISHIRING", 4)
        ).isEqualTo("PINALSIGYAHRPI");
    }

    @Test
    public void example3() {
        Assertions.assertThat(
                solver.convert("A", 1)
        ).isEqualTo("A");
    }

    @Test
    public void test1() {
        Assertions.assertThat(
                solver.convert("ABC", 2)
        ).isEqualTo("ACB");
    }


    @Test
    public void testCase1() {
        Assertions.assertThat(
                solver.convert("ABC", 1)
        ).isEqualTo("ABC");
    }

}