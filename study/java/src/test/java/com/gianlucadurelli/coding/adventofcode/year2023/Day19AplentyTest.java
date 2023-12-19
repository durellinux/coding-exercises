package com.gianlucadurelli.coding.adventofcode.year2023;


import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;

public class Day19AplentyTest {

    private static final Day19Aplenty solver = new Day19Aplenty();
    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day19Aplenty.class);
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day19Aplenty.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT)).isEqualTo(19114);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(INPUT)).isEqualTo(376008);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(TEST_INPUT)).isEqualTo(167409079868000L);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT)).isEqualTo(124078207789312L);
    }
}