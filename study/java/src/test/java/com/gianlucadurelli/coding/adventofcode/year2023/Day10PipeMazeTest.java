package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.PIPE_MAZE_INPUT;

public class Day10PipeMazeTest {

    private static final Day10PipeMaze solver = new Day10PipeMaze();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve1(
                List.of(".....", ".S-7.", ".|.|.", ".L-J.", ".....")
        )).isEqualTo(4);
    }

    @Test
    public void test1_1() {
        Assertions.assertThat(solver.solve1(
                List.of("-L|F7", "7S-7|", "L|7||", "-L-J|", "L|-JF")
        )).isEqualTo(4);
    }

    @Test
    public void test1_2() {
        Assertions.assertThat(solver.solve1(
                List.of("7-F7-", ".FJ|7", "SJLL7", "|F--J", "LJ.LJ")
        )).isEqualTo(8);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve1(PIPE_MAZE_INPUT)).isEqualTo(6856);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(
                List.of("...........", ".S-------7.", ".|F-----7|.", ".||.....||.", ".||.....||.", ".|L-7.F-J|.", ".|..|.|..|.", ".L--J.L--J.", "..........."),
                "F"
        )).isEqualTo(4);
    }

    @Test
    public void test2_1() {
        Assertions.assertThat(solver.solve2(
                List.of(".F----7F7F7F7F-7....", ".|F--7||||||||FJ....", ".||.FJ||||||||L7....", "FJL7L7LJLJ||LJ.L-7..", "L--J.L7...LJS7F-7L7.", "....F-J..F7FJ|L7L7L7", "....L7.F7||L7|.L7L7|", ".....|FJLJ|FJ|F7|.LJ", "....FJL-7.||.||||...", "....L---J.LJ.LJLJ..."),
                "F"
        )).isEqualTo(8);
    }

    @Test
    public void test2_2() {
        Assertions.assertThat(solver.solve2(
                List.of("FF7FSF7F7F7F7F7F---7", "L|LJ||||||||||||F--J", "FL-7LJLJ||||||LJL-77", "F--JF--7||LJLJ7F7FJ-", "L---JF-JLJ.||-FJLJJ7", "|F|F-JF---7F7-L7L|7|", "|FFJF7L7F-JF7|JL---7", "7-L-JL7||F7|L7F-7F7|", "L.L7LFJ|||||FJL7||LJ", "L7JLJL-JLJLJL--JLJ.L"),
                "7"
        )).isEqualTo(10);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve2(PIPE_MAZE_INPUT, "F")).isEqualTo(501);
    }
}