package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day16TheFloorWillBeLavaTest {

    private static final Day16TheFloorWillBeLava solver = new Day16TheFloorWillBeLava();
    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day16TheFloorWillBeLava.class);

    @Test
    public void test1() {
        Assertions.assertThat(
                solver.solve(List.of(".|...\\....", "|.-.\\.....", ".....|-...", "........|.", "..........", ".........\\", "..../.\\\\..", ".-.-/..|..", ".|....-|.\\", "..//.|...."))
        ).isEqualTo(46);
    }

    @Test
    public void solution1() {
        Assertions.assertThat(solver.solve(INPUT)).isEqualTo(7623L);
    }

    @Test
    public void test2() {
        Assertions.assertThat(
                solver.solve2(List.of(".|...\\....", "|.-.\\.....", ".....|-...", "........|.", "..........", ".........\\", "..../.\\\\..", ".-.-/..|..", ".|....-|.\\", "..//.|...."))
        ).isEqualTo(51);
    }

    @Test
    public void solution2() {
        Assertions.assertThat(solver.solve2(INPUT)).isEqualTo(8244L);
    }
}