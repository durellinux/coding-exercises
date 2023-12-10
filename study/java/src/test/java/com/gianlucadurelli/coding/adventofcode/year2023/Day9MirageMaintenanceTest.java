package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.MIRAGE_MAINTENANCE_INPUT;

public class Day9MirageMaintenanceTest {

    private static final Day9MirageMaintenance solver = new Day9MirageMaintenance();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve1(List.of("0 3 6 9 12 15", "1 3 6 10 15 21", "10 13 16 21 30 45"))).isEqualTo(114);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(MIRAGE_MAINTENANCE_INPUT)).isEqualTo(1938800261);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(List.of("0 3 6 9 12 15", "1 3 6 10 15 21", "10 13 16 21 30 45"))).isEqualTo(2);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(MIRAGE_MAINTENANCE_INPUT)).isEqualTo(1112);
    }



}