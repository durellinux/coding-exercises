package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import junit.framework.TestCase;

import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.SCRATCH_CARDS_INPUT;
import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.SCRATCH_CARDS_TEST_1_INPUT;

public class ScratchCardsTest extends TestCase {

    private static final ScratchCards solver = new ScratchCards();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve1(SCRATCH_CARDS_TEST_1_INPUT))
            .isEqualTo(13);
    }


    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(SCRATCH_CARDS_INPUT))
            .isEqualTo(18519);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(SCRATCH_CARDS_TEST_1_INPUT))
            .isEqualTo(30);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(SCRATCH_CARDS_INPUT))
            .isEqualTo(11787590);
    }


}