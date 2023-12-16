package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day11CosmicExpansionTest {

    private static final Day11CosmicExpansion solver = new Day11CosmicExpansion();

    private static final List<String> INPUT = AdventOfCode2023Inputs.loadInput(Day11CosmicExpansion.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve1(
                List.of("...#......", ".......#..", "#.........", "..........", "......#...", ".#........", ".........#", "..........", ".......#..", "#...#.....")
        )).isEqualTo(374);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(INPUT)).isEqualTo(9329143);
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
        Assertions.assertThat(solver.solve2(INPUT, 1000000)).isEqualTo(710674907809L);
    }

}