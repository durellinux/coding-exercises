package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day24NeverTellMeTheOddsTest {

    private static final Day24NeverTellMeTheOdds solver = new Day24NeverTellMeTheOdds();
    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day24NeverTellMeTheOdds.class);
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day24NeverTellMeTheOdds.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT, Day24NeverTellMeTheOdds.TestArea.example())).isEqualTo(2);
    }

    @Test
    public void solution1() {
        Assertions.assertThat(solver.solve(INPUT, Day24NeverTellMeTheOdds.TestArea.real())).isEqualTo(19976);
    }

    @Test
    public void solution2() {
        // Prints equations => then input them to ChatGPT
        solver.solve2(TEST_INPUT, Day24NeverTellMeTheOdds.TestArea.example());
        solver.solve2(INPUT, Day24NeverTellMeTheOdds.TestArea.example());
    }
}