package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import junit.framework.TestCase;

public class Day1TrebuchetTest {

    private static final Day1Trebuchet solver = new Day1Trebuchet();
    private static final List<String> INPUT = AdventOfCode2023Inputs.loadInput(Day1Trebuchet.class);

    @Test
    public void test1() {
        Assertions.assertThat(
            solver.solve1(List.of("1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet"))
        ).isEqualTo(142);
    }

    @Test
    public void test2() {
        Assertions.assertThat(
            solver.solve2(List.of("two1nine" ,"eightwothree" ,"abcone2threexyz" ,"xtwone3four" ,"4nineeightseven2" ,"zoneight234" ,"7pqrstsixteen"))
        ).isEqualTo(281);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(INPUT))
            .isEqualTo(54390);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT))
            .isEqualTo(54277);
    }

}