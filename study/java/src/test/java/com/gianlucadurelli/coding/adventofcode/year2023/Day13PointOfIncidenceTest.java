package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day13PointOfIncidenceTest {

    private static final Day13PointOfIncidence solver = new Day13PointOfIncidence();

    private static final List<String> INPUT = AdventOfCode2023Inputs.loadInput(Day13PointOfIncidence.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(
                List.of("#.##..##.", "..#.##.#.", "##......#", "##......#", "..#.##.#.", "..##..##.", "#.#.##.#.", "", "#...##..#", "#....#..#", "..##..###", "#####.##.", "#####.##.", "..##..###", "#....#..#"),
                false
        )).isEqualTo(405);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(INPUT, false)).isEqualTo(35360L);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve(
                List.of("#.##..##.", "..#.##.#.", "##......#", "##......#", "..#.##.#.", "..##..##.", "#.#.##.#.", "", "#...##..#", "#....#..#", "..##..###", "#####.##.", "#####.##.", "..##..###", "#....#..#"),
                true
        )).isEqualTo(400);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve(INPUT, true)).isEqualTo(36755L);
    }

}