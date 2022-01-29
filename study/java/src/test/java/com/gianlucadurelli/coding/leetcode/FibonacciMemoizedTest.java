package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciMemoizedTest {

    @Test
    public void test() {
        FibonacciMemoized solver = new FibonacciMemoized();
        Assertions.assertThat(solver.fib(0)).isEqualTo(0);
        Assertions.assertThat(solver.fib(1)).isEqualTo(1);
        Assertions.assertThat(solver.fib(2)).isEqualTo(1);
        Assertions.assertThat(solver.fib(3)).isEqualTo(2);
        Assertions.assertThat(solver.fib(4)).isEqualTo(3);
        Assertions.assertThat(solver.fib(5)).isEqualTo(5);
        Assertions.assertThat(solver.fib(6)).isEqualTo(8);
        Assertions.assertThat(solver.fib(7)).isEqualTo(13);

    }
}