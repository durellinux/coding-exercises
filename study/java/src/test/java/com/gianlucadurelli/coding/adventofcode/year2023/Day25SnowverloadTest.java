package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day25SnowverloadTest {

    private static final Day25Snowverload solver = new Day25Snowverload();
    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day25Snowverload.class);
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day25Snowverload.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT)).isEqualTo(54);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(INPUT)).isEqualTo(567606);
    }
}