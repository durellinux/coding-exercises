package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day14ParabolicReflectorDishTest {

    private static final Day14ParabolicReflectorDish solver = new Day14ParabolicReflectorDish();
    private static final List<String> INPUT = AdventOfCode2023Inputs.loadInput(Day14ParabolicReflectorDish.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(
                List.of("O....#....", "O.OO#....#", ".....##...", "OO.#O....O", ".O.....O#.", "O.#..O.#.#", "..O..#O..O", ".......O..", "#....###..", "#OO..#....")
        )).isEqualTo(136);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(INPUT)).isEqualTo(108955);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(
                List.of("O....#....", "O.OO#....#", ".....##...", "OO.#O....O", ".O.....O#.", "O.#..O.#.#", "..O..#O..O", ".......O..", "#....###..", "#OO..#....")
        )).isEqualTo(64);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT)).isEqualTo(106689);
    }

}