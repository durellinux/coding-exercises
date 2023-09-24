package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContainerWithMostWaterTest {

    private static final ContainerWithMostWater solver = new ContainerWithMostWater();

    @Test
    public void example1() {
        Assertions.assertThat(solver.maxArea(new int[]{1,8,6,2,5,4,8,3,7})).isEqualTo(49);
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.maxArea(new int[]{1,1})).isEqualTo(1);
    }

}