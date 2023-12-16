package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import junit.framework.TestCase;

import java.util.List;

public class Day4ScratchCardsTest extends TestCase {

    private static final Day4ScratchCards solver = new Day4ScratchCards();

    private static final List<String> TEST_INPUT = AdventOfCode2023Inputs.loadTestInput(Day4ScratchCards.class);
    private static final List<String> INPUT = AdventOfCode2023Inputs.loadInput(Day4ScratchCards.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve1(TEST_INPUT))
            .isEqualTo(13);
    }


    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(INPUT))
            .isEqualTo(18519);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(TEST_INPUT))
            .isEqualTo(30);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT))
            .isEqualTo(11787590);
    }


}