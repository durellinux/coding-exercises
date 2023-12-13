package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.POINT_OF_INCIDENCE_INPUT;

public class Day13PointOfIncidenceTest {

    private static final Day13PointOfIncidence solver = new Day13PointOfIncidence();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(
                List.of("#.##..##.", "..#.##.#.", "##......#", "##......#", "..#.##.#.", "..##..##.", "#.#.##.#.", "", "#...##..#", "#....#..#", "..##..###", "#####.##.", "#####.##.", "..##..###", "#....#..#"),
                false
        )).isEqualTo(405);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(POINT_OF_INCIDENCE_INPUT, false)).isEqualTo(35360L);
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
        Assertions.assertThat(solver.solve(POINT_OF_INCIDENCE_INPUT, true)).isEqualTo(36755L);
    }

}