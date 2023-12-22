package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day21StepCounterTest {

    private static final Day21StepCounter solver = new Day21StepCounter();
    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day21StepCounter.class);
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day21StepCounter.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT, 6)).isEqualTo(16);
    }

    @Test
    public void test1_enum() {
        long last = 0;
//        for (int i = 0; i < 1000; i++) {
//            long newValue = solver.solve2(TEST_INPUT, i);
//            System.out.println("f("+i+") = " + newValue + " - delta: " + (newValue - last) );
//            last = newValue;
//        }
        Assertions.assertThat(solver.solve2(TEST_INPUT, 26501365)).isEqualTo(16);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(INPUT, 64)).isEqualTo(3562);
    }

    @Test
    public void test2() {
//        Assertions.assertThat(solver.solve2(TEST_INPUT, 6)).isEqualTo(16);
//        Assertions.assertThat(solver.solve2(INPUT, 6)).isEqualTo(37);
//        Assertions.assertThat(solver.solve2(TEST_INPUT, 10)).isEqualTo(50);
//        Assertions.assertThat(solver.solve2(INPUT, 10)).isEqualTo(89);
//        Assertions.assertThat(solver.solve2(TEST_INPUT, 50)).isEqualTo(1594);
//        Assertions.assertThat(solver.solve2(INPUT, 50)).isEqualTo(2130);
//        Assertions.assertThat(solver.solve2(TEST_INPUT, 100)).isEqualTo(6536);
//        Assertions.assertThat(solver.solve2(INPUT, 100)).isEqualTo(8624);
//        Assertions.assertThat(solver.solve2(TEST_INPUT, 500)).isEqualTo(167004);
//        Assertions.assertThat(solver.solve2(TEST_INPUT, 1000)).isEqualTo(668697);
        Assertions.assertThat(solver.solve2(TEST_INPUT, 5000)).isEqualTo(16733044);
    }


    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT, 64)).isEqualTo(3562);
    }
}