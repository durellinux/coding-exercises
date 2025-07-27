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
        List<Long> coins = List.of(1, 2, 3).stream().map(Long::valueOf).toList();
        long expectedWays = 4;

        long ways = TheCoinChangeProblem.getWays(change, coins);

        Assertions.assertThat(ways).isEqualTo(expectedWays);
    }

    @Test
    public void getWays10() {
        int change = 250;
        List<Long> coins = List.of(8, 47, 13, 24, 25, 31, 32, 35, 3, 19, 40, 48, 1, 4, 17, 38, 22, 30, 33, 15, 44, 46, 36, 9, 20, 49)
                .stream().map(Long::valueOf).toList();;
        long expectedWays = 3542323427L;

        long ways = TheCoinChangeProblem.getWays(change, coins);

        Assertions.assertThat(ways).isEqualTo(expectedWays);
    }
}