package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day7CamelCardsTest {

    private static final Day7CamelCards solver = new Day7CamelCards();

    private static final List<String> TEST_INPUT = List.of("32T3K 765", "T55J5 684", "KK677 28", "KTJJT 220", "QQQJA 483");
    private static final List<String> INPUT = AdventOfCode2023Inputs.loadInput(Day7CamelCards.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT, 1)).isEqualTo(6440);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(INPUT, 1)).isEqualTo(251545216);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve(TEST_INPUT, 2)).isEqualTo(5905);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve(INPUT, 2)).isEqualTo(250384185);
    }

}