package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import junit.framework.TestCase;

public class TrebuchetTest extends TestCase {

    private static final Trebuchet solver = new Trebuchet();

    @Test
    public void test1() {
        Assertions.assertThat(
            solver.solve(List.of("1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet"))
        ).isEqualTo(142);
    }

    @Test
    public void testSolution() {
        System.out.println(solver.solve(AdventOfCode2023Inputs.TREBUCHET_INPUT));
    }

}