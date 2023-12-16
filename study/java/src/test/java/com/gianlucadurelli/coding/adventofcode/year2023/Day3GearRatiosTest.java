package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day3GearRatiosTest {

    public static final Day3GearRatios solver = new Day3GearRatios();

    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day3GearRatios.class);
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day3GearRatios.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve1(TEST_INPUT)).isEqualTo(4361);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(INPUT)).isEqualTo(532331);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(TEST_INPUT)).isEqualTo(467835);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT)).isEqualTo(82301120);
    }

}