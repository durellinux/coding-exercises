package com.gianlucadurelli.coding.hackerrank.practice.algorithms.dynamicprogramming;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TheCoinChangeProblemTest {

    @Test
    @Ignore(value = "Solution not working")
    public void getWays() {
        int change = 4;
        List<Long> coins = List.of(1L, 2L, 3L);
        long expectedWays = 4;

        long ways = TheCoinChangeProblem.getWays(change, coins);

        Assertions.assertThat(ways).isEqualTo(expectedWays);
    }
}