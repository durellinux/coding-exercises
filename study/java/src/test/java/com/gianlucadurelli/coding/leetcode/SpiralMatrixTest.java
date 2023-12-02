package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class SpiralMatrixTest {

    private static final SpiralMatrix solver = new SpiralMatrix();

    @Test
    public void example1() {
        Assertions.assertThat(solver.spiralOrder(
                new int[][] {
                        new int[] {1,2,3},
                        new int[] {4,5,6},
                        new int[] {7,8,9},
                }
        )).isEqualTo(List.of(1,2,3,6,9,8,7,4,5));
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.spiralOrder(
                new int[][] {
                        new int[]{1,2,3,4},
                        new int[]{5,6,7,8},
                        new int[]{9,10,11,12}
                }
        )).isEqualTo(List.of(1,2,3,4,8,12,11,10,9,5,6,7));
    }



}