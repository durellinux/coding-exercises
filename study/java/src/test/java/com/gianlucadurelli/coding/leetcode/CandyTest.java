package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class CandyTest {

    private static final Candy solver = new Candy();

    @Test
    public void test1() {
        Assertions.assertThat(
                solver.candy(new int[]{1, 2})
        ).isEqualTo(3);
    }

    @Test
    public void test2() {
        Assertions.assertThat(
                solver.candy(new int[]{2, 1})
        ).isEqualTo(3);
    }

    @Test
    public void test3() {
        Assertions.assertThat(
                solver.candy(new int[]{1, 1})
        ).isEqualTo(2);
    }

    @Test
    public void example1() {
        Assertions.assertThat(
                solver.candy(new int[]{1, 0, 2})
        ).isEqualTo(5);
    }

    @Test
    public void example2() {
        Assertions.assertThat(
                solver.candy(new int[]{1, 2, 2})
        ).isEqualTo(4);
    }

    @Test
    public void failed1() {
        Assertions.assertThat(
                solver.candy(new int[]{1,3,2,2,1})
        ).isEqualTo(7);
    }
}