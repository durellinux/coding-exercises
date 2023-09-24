package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class ThreeSumTest {

    private static final ThreeSum solver = new ThreeSum();

    @Test
    public void example1() {
        Assertions.assertThat(solver.threeSum(new int[]{-1,0,1,2,-1,-4}))
                .isEqualTo(List.of(
                        List.of(-1, -1, 2),
                        List.of(-1, 0, 1)
                ));
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.threeSum(new int[]{0, 1, 1}))
                .isEqualTo(Collections.emptyList());
    }

    @Test
    public void example3() {
        Assertions.assertThat(solver.threeSum(new int[]{0, 0, 0}))
                .isEqualTo(List.of(
                        List.of(0, 0, 0)
                ));
    }

}