package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day18LavaductLagoonTest {

    private static final Day18LavaductLagoon solver = new Day18LavaductLagoon();
    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day18LavaductLagoon.class);
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day18LavaductLagoon.class);

    @Test
    public void test() {
        Assertions.assertThat(solver.solve(solver.parseInput(TEST_INPUT))).isEqualTo(62);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(solver.parseInput(INPUT))).isEqualTo(48503);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve(solver.parseInput2(INPUT))).isEqualTo(48503);
    }

}