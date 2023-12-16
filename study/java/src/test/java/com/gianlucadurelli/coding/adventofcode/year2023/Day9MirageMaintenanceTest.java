package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day9MirageMaintenanceTest {

    private static final Day9MirageMaintenance solver = new Day9MirageMaintenance();

    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day9MirageMaintenance.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve1(List.of("0 3 6 9 12 15", "1 3 6 10 15 21", "10 13 16 21 30 45"))).isEqualTo(114);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(INPUT)).isEqualTo(1938800261);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(List.of("0 3 6 9 12 15", "1 3 6 10 15 21", "10 13 16 21 30 45"))).isEqualTo(2);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(INPUT)).isEqualTo(1112);
    }



}