package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
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

    @Test
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

    private long polyFit(int step) {
        double x = step * 1.0;
        return  Math.round(8.1773566158360870e+002 * Math.pow(x,0)
                + -7.5791319316010515e+001 * Math.pow(x,1)
                +  3.4332597056310563e+000 * Math.pow(x,2)
                + -5.7228780202924302e-002 * Math.pow(x,3)
                +  7.4030909312120614e-004 * Math.pow(x,4)
                + -6.2825241083701417e-006 * Math.pow(x,5)
                +  3.5666791122072450e-008 * Math.pow(x,6)
                + -1.3488749574404663e-010 * Math.pow(x,7)
                +  3.2820364404242919e-013 * Math.pow(x,8)
                + -4.6533614827148485e-016 * Math.pow(x,9)
                +  2.6567965734824193e-019 * Math.pow(x,10)
                +  1.4077662924135407e-022 * Math.pow(x,11)
                + -1.9850790107829447e-025 * Math.pow(x,12));
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT, 500)).isEqualTo(3562);
    }
}