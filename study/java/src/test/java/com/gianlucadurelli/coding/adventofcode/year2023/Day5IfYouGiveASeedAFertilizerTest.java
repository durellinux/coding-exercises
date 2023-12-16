package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day5IfYouGiveASeedAFertilizerTest {

    private static final Day5IfYouGiveASeedAFertilizer solver = new Day5IfYouGiveASeedAFertilizer();
    private static final List<String> TEST_INPUT = AdventOfCode2023Inputs.loadTestInput(Day5IfYouGiveASeedAFertilizer.class);
    private static final List<String> INPUT = AdventOfCode2023Inputs.loadInput(Day5IfYouGiveASeedAFertilizer.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT, solver::parseSeeds1)).isEqualTo(35);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(INPUT, solver::parseSeeds1)).isEqualTo(403695602);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve(TEST_INPUT, solver::parseSeeds2)).isEqualTo(46);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve(INPUT, solver::parseSeeds2)).isEqualTo(219529182);
    }
}