package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day21StepCounterTest {

    private static final Day21StepCounter solver = new Day21StepCounter();
    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day21StepCounter.class);
    private static final List<String> TEST_INPUT2 = AdventOfCodeTestHelpers.loadWithSuffix(Day21StepCounter.class, "-test2.txt");
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day21StepCounter.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT, 6)).isEqualTo(16);
    }

    @Test
    public void test1_enum() {
        long last = 0;
        for (int i = 0; i < 100; i++) {
            long newValue = solver.solve2(TEST_INPUT, i);
            System.out.println("f("+i+") = " + newValue + " - delta: " + (newValue - last) );
            last = newValue;
        }
        Assertions.assertThat(solver.solve(TEST_INPUT, 6)).isEqualTo(16);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(INPUT, 64)).isEqualTo(3562);
    }
}