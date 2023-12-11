package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.COSMIC_EXPANSION_INPUT;

public class Day11CosmicExpansionTest {

    private static final Day11CosmicExpansion solver = new Day11CosmicExpansion();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve1(
                List.of("...#......", ".......#..", "#.........", "..........", "......#...", ".#........", ".........#", "..........", ".......#..", "#...#.....")
        )).isEqualTo(374);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(COSMIC_EXPANSION_INPUT)).isEqualTo(9329143);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(
                List.of("...#......", ".......#..", "#.........", "..........", "......#...", ".#........", ".........#", "..........", ".......#..", "#...#....."),
                100
        )).isEqualTo(8410);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(COSMIC_EXPANSION_INPUT, 1000000)).isEqualTo(9329143);
    }

}