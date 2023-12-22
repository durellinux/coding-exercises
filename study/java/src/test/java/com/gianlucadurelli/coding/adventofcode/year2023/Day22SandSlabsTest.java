package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day22SandSlabsTest {

    private static final Day22SandSlabs solver = new Day22SandSlabs();
    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day22SandSlabs.class);
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day22SandSlabs.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT)).isEqualTo(5);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(INPUT)).isEqualTo(475);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(TEST_INPUT)).isEqualTo(7);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT)).isEqualTo(79144);
    }
}