package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Day21StepCounterTest {

    private static final Day21StepCounter solver = new Day21StepCounter();
    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day21StepCounter.class);
    private static final List<String> TEST_INPUT_2 = AdventOfCodeTestHelpers.loadWithSuffix(Day21StepCounter.class, "-test2.txt");
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day21StepCounter.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT, 6)).isEqualTo(16);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(INPUT, 64)).isEqualTo(3562);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(TEST_INPUT, 6)).isEqualTo(16);
        Assertions.assertThat(solver.solve2(TEST_INPUT, 10)).isEqualTo(50);
    }

    @Ignore
    public void test_2() {
        Assertions.assertThat(solver.solve(TEST_INPUT_2, 50)).isEqualTo(16);
    }

    @Test
    public void test2_plot() {
        for (int i = 499; i < 500; i++) {
            List<Long> solution = solver.plot2(TEST_INPUT, i);
            long area = area(i);
            List<Long> expected =frontiers(solution.size());
            List<Long> diff = error(solution, expected);
//            System.out.println(i + " " + solver.plot2(TEST_INPUT, i) + " - " + area + " - " + (solution - area));
            System.out.println(solution);
            System.out.println(expected);
            System.out.println(diff);
//            for (int tmp = 0; tmp < solution.size(); tmp++) {
//                System.out.println(tmp + " " + diff.get(tmp));
//            }
        }
    }

    long area(long s) {
        long i = s / 2;
        if ( s % 2 == 0) {
            return 1 + 8 * i * (i+1) / 2;
        }

        return 4 + 4*i + 8*i*(i+1)/2;
    }

    List<Long> error(List<Long> actual, List<Long> expected) {
        List<Long> diff = new ArrayList<>();
        for (int i = 0; i < actual.size(); i++) {
            diff.add(expected.get(i) -actual.get(i));
        }

        return diff;
    }

    List<Long> frontiers(long s) {
        List<Long> f = new ArrayList<>();
        for (int i = 0; i < s; i++) {
            f.add(perimeter(i));
        }

        return f;
    }

    long perimeter(long s) {
        if (s == 0) {
            return 1;
        }

        return 4 * (s);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT, 196)).isEqualTo(32768);
    }
}