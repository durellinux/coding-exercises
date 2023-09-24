package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class KokoEatingBananasTest {

    private static final KokoEatingBananas solver = new KokoEatingBananas();

    @Test
    public void example1() {
        Assertions.assertThat(solver.minEatingSpeed(new int[]{3,6,7,11}, 8))
                .isEqualTo(4);
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.minEatingSpeed(new int[]{30,11,23,4,20}, 5))
                .isEqualTo(30);
    }

    @Test
    public void example3() {
        Assertions.assertThat(solver.minEatingSpeed(new int[]{30,11,23,4,20}, 6))
                .isEqualTo(23);
    }

    @Test
    public void testCase1() {
        Assertions.assertThat(solver.minEatingSpeed(new int[]{312884470}, 968709470))
                .isEqualTo(1);
    }
}