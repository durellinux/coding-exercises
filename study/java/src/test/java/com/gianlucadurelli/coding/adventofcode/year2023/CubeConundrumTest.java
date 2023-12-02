package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CubeConundrumTest {

    public static final CubeConundrum solver = new CubeConundrum();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve1(AdventOfCode2023Inputs.CUBE_CONUNDRUM_TEST, AdventOfCode2023Inputs.CUBE_CONUNDRUM_CONFIG))
                .isEqualTo(8);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(AdventOfCode2023Inputs.CUBE_CONUNDRUM_INPUT, AdventOfCode2023Inputs.CUBE_CONUNDRUM_CONFIG))
                .isEqualTo(2239);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(AdventOfCode2023Inputs.CUBE_CONUNDRUM_TEST))
                .isEqualTo(2286);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(AdventOfCode2023Inputs.CUBE_CONUNDRUM_INPUT))
                .isEqualTo(83435);
    }
}