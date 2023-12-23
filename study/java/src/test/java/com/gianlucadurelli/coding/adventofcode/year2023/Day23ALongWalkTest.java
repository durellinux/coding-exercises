package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day23ALongWalkTest {

    private static final Day23ALongWalk solver = new Day23ALongWalk();
    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day23ALongWalk.class);
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day23ALongWalk.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT)).isEqualTo(94);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(INPUT)).isEqualTo(2254);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(TEST_INPUT)).isEqualTo(154);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT)).isEqualTo(6394L);
    }
}