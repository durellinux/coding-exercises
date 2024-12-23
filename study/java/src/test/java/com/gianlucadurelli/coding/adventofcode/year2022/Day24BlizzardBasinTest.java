package com.gianlucadurelli.coding.adventofcode.year2022;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day24BlizzardBasinTest {

    private static final Day24BlizzardBasin solver = new Day24BlizzardBasin();
    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day24BlizzardBasin.class);
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day24BlizzardBasin.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT, 1)).isEqualTo(18);
    }

    @Test
    public void solution1() {
        Assertions.assertThat(solver.solve(INPUT, 1)).isEqualTo(269L);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve(TEST_INPUT, 3)).isEqualTo(54);
    }

    @Test
    public void solution2() {
        Assertions.assertThat(solver.solve(INPUT, 3)).isEqualTo(825L);
    }

}