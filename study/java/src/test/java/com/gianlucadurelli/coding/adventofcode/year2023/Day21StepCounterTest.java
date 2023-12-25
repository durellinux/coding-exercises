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
        Assertions.assertThat(solver.solve2(TEST_INPUT, 100)).isEqualTo(6536);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT, 131)).isEqualTo(14679);
        Assertions.assertThat(solver.solve2(INPUT, 195)).isEqualTo(32404);
        Assertions.assertThat(solver.solve2(INPUT, 197)).isEqualTo(33196);
        Assertions.assertThat(solver.solve2(INPUT, 261)).isEqualTo(57872);
        Assertions.assertThat(solver.solve2(INPUT, 263)).isEqualTo(58764);
        Assertions.assertThat(solver.solve2(INPUT, 327)).isEqualTo(90820);
        Assertions.assertThat(solver.solve2(INPUT, 393)).isEqualTo(130933);
        Assertions.assertThat(solver.solve2(INPUT, 523)).isEqualTo(231608L);
        Assertions.assertThat(solver.solve2(INPUT, 655)).isEqualTo(363051L);
        Assertions.assertThat(solver.solve2(INPUT, 787)).isEqualTo(523876L);
        Assertions.assertThat(solver.solve2(INPUT, 919)).isEqualTo(714127L);
        Assertions.assertThat(solver.solve2(INPUT, 1047)).isEqualTo(926672L);
        Assertions.assertThat(solver.solve2(INPUT, 26501365)).isEqualTo(593992588165537L);
    }

    @Test
    public void testSolution2_validate() {
        // 263  1 0 0
        // 393  1 1 +124 130933L 131057L
        // 523  2 1 -124 231608L 231484L
        // 655  2 2 +248 363051L 363299L
        // 787  3 2 -248 523876L 523628L
        // 919  3 3 +372 714127L 714499L
        // 1047 4 3 -372 926672L 926300L
        for(int s = 523; s <= 523; s+=2) {
            System.out.println("Testing steps: " + s);
            Assertions.assertThat(solver.solve2(INPUT, s)).isEqualTo(solver.solveBruteForce(INPUT, s));
        }
    }

    private List<Long> solutionsList(Long value) {
//        return List.of(value, value + 124, value - 124);
        return List.of(value);
    }
}