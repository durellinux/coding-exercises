package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TwoSumTest {

    private static final TwoSum solver = new TwoSum();

    @Test
    public void test() {
        Assertions.assertThat(solver.twoSum(new int[]{2,7,11,15}, 9)).isEqualTo(new int[]{0, 1});
        Assertions.assertThat(solver.twoSum(new int[]{3, 2, 4}, 6)).isEqualTo(new int[]{1, 2});
        Assertions.assertThat(solver.twoSum(new int[]{3, 3}, 6)).isEqualTo(new int[]{0, 1});
    }

    @Test
    public void example1() {
        Assertions.assertThat(solver.twoSumOffset1(new int[]{2,7,11,15}, 9)).isEqualTo(new int[]{1, 2});
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.twoSumOffset1(new int[]{2, 3, 4}, 6)).isEqualTo(new int[]{1, 3});
    }

    @Test
    public void example3() {
        Assertions.assertThat(solver.twoSumOffset1(new int[]{-1, 0}, -1)).isEqualTo(new int[]{1, 2});
    }
}