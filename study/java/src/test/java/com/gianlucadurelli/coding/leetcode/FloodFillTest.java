package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class FloodFillTest {

    @Test
    public void test() {
        FloodFill solver = new FloodFill();

        Assertions.assertThat(solver.floodFill(
                new int[][]{
                        new int[]{1,1,1},
                        new int[]{1,1,0},
                        new int[]{1,0,1}
                }, 1, 1, 2
        )).isEqualTo(new int[][]{
                new int[]{2,2,2},
                new int[]{2,2,0},
                new int[]{2,0,1}
        });
    }
}