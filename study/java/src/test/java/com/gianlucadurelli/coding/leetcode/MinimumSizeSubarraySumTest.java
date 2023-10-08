package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinimumSizeSubarraySumTest {

    private static final MinimumSizeSubarraySum solver = new MinimumSizeSubarraySum();

    @Test
    public void example1() {
        Assertions.assertThat(solver.minSubArrayLen(7, new int[]{2,3,1,2,4,3}))
                .isEqualTo(2);
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.minSubArrayLen(4, new int[]{1, 4, 4}))
                .isEqualTo(1);
    }

    @Test
    public void example3() {
        Assertions.assertThat(solver.minSubArrayLen(11, new int[]{1,1,1,1,1,1,1,1}))
                .isEqualTo(0);
    }

}