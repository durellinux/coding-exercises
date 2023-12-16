package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class Day2CubeConundrumTest {

    public static final Day2CubeConundrum solver = new Day2CubeConundrum();

    private static final Map<String, Integer> CUBE_CONUNDRUM_CONFIG = Map.of(
            "red", 12,
            "green", 13,
            "blue", 14
    );

    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day2CubeConundrum.class);
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day2CubeConundrum.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve1(TEST_INPUT, CUBE_CONUNDRUM_CONFIG))
                .isEqualTo(8);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(INPUT, CUBE_CONUNDRUM_CONFIG))
                .isEqualTo(2239);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(TEST_INPUT))
                .isEqualTo(2286);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT))
                .isEqualTo(83435);
    }
}