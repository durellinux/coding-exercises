package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GearRatiosTest {

    public static final GearRatios solver = new GearRatios();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve1(
                List.of("467..114..", "...*......", "..35..633.", "......#...", "617*......", ".....+.58.", "..592.....", "......755.", "...$.*....", ".664.598..")
        )).isEqualTo(4361);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(AdventOfCode2023Inputs.GEAR_RATIOS_INPUT)).isEqualTo(532331);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(
                List.of("467..114..", "...*......", "..35..633.", "......#...", "617*......", ".....+.58.", "..592.....", "......755.", "...$.*....", ".664.598..")
        )).isEqualTo(467835);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(AdventOfCode2023Inputs.GEAR_RATIOS_INPUT)).isEqualTo(82301120);
    }

}