package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day6WaitForItTest {

    private static final Day6WaitForIt solver = new Day6WaitForIt();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve1(
                List.of(7L, 15L, 30L),
                List.of(9L, 40L, 200L)
        )).isEqualTo(288L);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(
                List.of(38L, 94L, 79L, 70L),
                List.of(241L, 1549L, 1074L, 1091L)
        )).isEqualTo(1083852L);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve1(
                List.of(38947970L),
                List.of(241154910741091L)
        )).isEqualTo(23501589L);
    }

}