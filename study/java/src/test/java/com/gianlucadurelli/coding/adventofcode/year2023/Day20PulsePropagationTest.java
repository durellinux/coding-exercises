package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day20PulsePropagationTest {

    private static final Day20PulsePropagation solver = new Day20PulsePropagation();
    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day20PulsePropagation.class);
    private static final List<String> TEST_INPUT_2 = AdventOfCodeTestHelpers.loadWithSuffix(Day20PulsePropagation.class, "-test2.txt");
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day20PulsePropagation.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT)).isEqualTo(32000000);
    }

    @Test
    public void test1_2() {
        Assertions.assertThat(solver.solve(TEST_INPUT_2)).isEqualTo(11687500);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(INPUT)).isEqualTo(886701120);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT, List.of("xc", "bp", "th", "pd"))).isEqualTo(228134431501037L);
    }

}