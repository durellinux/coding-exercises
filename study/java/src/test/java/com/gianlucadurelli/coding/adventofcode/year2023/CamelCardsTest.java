package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.CAMEL_CARDS_INPUT;

public class CamelCardsTest {

    private static final CamelCards solver = new CamelCards();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(
                List.of("32T3K 765", "T55J5 684", "KK677 28", "KTJJT 220", "QQQJA 483"),
                1
        )).isEqualTo(6440);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(CAMEL_CARDS_INPUT, 1)).isEqualTo(251545216);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve(
                List.of("32T3K 765", "T55J5 684", "KK677 28", "KTJJT 220", "QQQJA 483"),
                2
        )).isEqualTo(5905);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve(CAMEL_CARDS_INPUT, 2)).isEqualTo(250384185);
    }

}