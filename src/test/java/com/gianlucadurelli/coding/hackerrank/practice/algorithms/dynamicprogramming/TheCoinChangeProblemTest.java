package com.gianlucadurelli.coding.hackerrank.practice.algorithms.dynamicprogramming;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class TheCoinChangeProblemTest {

    @Test
    public void getWaysExample() {
        int change = 4;
        List<Long> coins = List.of(1L, 2L, 3L);
        long expectedWays = 4;

        long ways = TheCoinChangeProblem.getWays(change, coins);

        Assertions.assertThat(ways).isEqualTo(expectedWays);
    }

    @Test
    public void getWays10() {
        int change = 260;
        List<Long> coins = List.of(8L, 47L, 13L, 24L, 25L, 31L, 32L, 35L, 3L, 19L, 40L, 48L, 1L, 4L, 17L, 38L, 22L, 30L, 33L, 15L, 44L, 46L, 36L, 9L, 20L, 49L);
        long expectedWays = 3542323427L;

        long ways = TheCoinChangeProblem.getWays(change, coins);

        Assertions.assertThat(ways).isEqualTo(expectedWays);
    }
}