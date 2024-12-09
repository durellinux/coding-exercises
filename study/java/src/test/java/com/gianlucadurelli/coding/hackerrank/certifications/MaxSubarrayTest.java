package com.gianlucadurelli.coding.hackerrank.certifications;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MaxSubarrayTest {
    private static final MaxSubarray solver = new MaxSubarray();

    @Test
    public void example() {
        Assertions.assertThat(solver.maxSubarrayValue(List.of(2, -1, -4, 5))).isEqualTo(36);
    }

    @Test
    public void test1() {
        Assertions.assertThat(solver.maxSubarrayValue(List.of(1, -1, 1, -1, 1))).isEqualTo(25);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.maxSubarrayValue(List.of(-1, 2, 3, 4, -5))).isEqualTo(81);
    }

}