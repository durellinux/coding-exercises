package com.gianlucadurelli.coding.hackerrank.practice.algorithms.greedy;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class LuckBalanceTest {

    @Test
    public void LuckBakanceTest() {
        int k = 3;
        int[][] contests = new int[][]{
                new int[]{5, 1},
                new int[]{2, 1},
                new int[]{1, 1},
                new int[]{8, 1},
                new int[]{10, 0},
                new int[]{5, 0},
        };

        int solution = LuckBalance.luckBalance(k, contests);
        Assertions.assertThat(solution).isEqualTo(29);
    }
}