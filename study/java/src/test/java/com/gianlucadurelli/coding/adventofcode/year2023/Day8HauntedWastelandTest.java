package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day8HauntedWastelandTest {

    private static final Day8HauntedWasteland solver = new Day8HauntedWasteland();
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day8HauntedWasteland.class);
    private static final List<String> TEST_INPUT_1 = AdventOfCodeTestHelpers.loadTestInput(Day8HauntedWasteland.class);
    private static final List<String> TEST_INPUT_2 = AdventOfCodeTestHelpers.loadWithSuffix(Day8HauntedWasteland.class, "-test2.txt");
    private static final List<String> TEST_INPUT_3 = AdventOfCodeTestHelpers.loadWithSuffix(Day8HauntedWasteland.class, "-test3.txt");

    @Test
    public void test1() {
        Assertions
                .assertThat(solver.solve1(TEST_INPUT_1))
                .isEqualTo(2);

        Assertions
                .assertThat(solver.solve1(TEST_INPUT_2))
                .isEqualTo(6);
    }

    @Test
    public void testSolution1() {
        Assertions
                .assertThat(solver.solve1(INPUT))
                .isEqualTo(19199);
    }

    @Test
    public void test2() {
        Assertions
                .assertThat(solver.solve2(TEST_INPUT_3))
                .isEqualTo(6);
    }

    @Test
    public void testSolution2() {
        Assertions
                .assertThat(solver.solve2(INPUT))
                .isEqualTo(13663968099527L);
    }

}