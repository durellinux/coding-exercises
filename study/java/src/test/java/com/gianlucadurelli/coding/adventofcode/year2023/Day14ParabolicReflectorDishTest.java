package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.PARABOLIC_REFLECTOR_DISH_INPUT;
import static org.junit.Assert.*;

public class Day14ParabolicReflectorDishTest {

    private static final Day14ParabolicReflectorDish solver = new Day14ParabolicReflectorDish();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(
                List.of("O....#....", "O.OO#....#", ".....##...", "OO.#O....O", ".O.....O#.", "O.#..O.#.#", "..O..#O..O", ".......O..", "#....###..", "#OO..#....")
        )).isEqualTo(136);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(PARABOLIC_REFLECTOR_DISH_INPUT)).isEqualTo(108955);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(
                List.of("O....#....", "O.OO#....#", ".....##...", "OO.#O....O", ".O.....O#.", "O.#..O.#.#", "..O..#O..O", ".......O..", "#....###..", "#OO..#....")
        )).isEqualTo(64);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(PARABOLIC_REFLECTOR_DISH_INPUT)).isEqualTo(108955);
    }

}