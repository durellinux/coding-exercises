package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.THE_FLOOR_IS_LAVA_INPUT;

public class Day16TheFloorWillBeLavaTest {

    private static final Day16TheFloorWillBeLava solver = new Day16TheFloorWillBeLava();

    @Test
    public void test1() {
        Assertions.assertThat(
                solver.solve(List.of(".|...\\....", "|.-.\\.....", ".....|-...", "........|.", "..........", ".........\\", "..../.\\\\..", ".-.-/..|..", ".|....-|.\\", "..//.|...."))
        ).isEqualTo(46);
    }

    @Test
    public void solution1() {
        Assertions.assertThat(solver.solve(THE_FLOOR_IS_LAVA_INPUT)).isEqualTo(7623L);
    }

    @Test
    public void test2() {
        Assertions.assertThat(
                solver.solve2(List.of(".|...\\....", "|.-.\\.....", ".....|-...", "........|.", "..........", ".........\\", "..../.\\\\..", ".-.-/..|..", ".|....-|.\\", "..//.|...."))
        ).isEqualTo(51);
    }

    @Test
    public void solution2() {
        Assertions.assertThat(solver.solve2(THE_FLOOR_IS_LAVA_INPUT)).isEqualTo(8244L);
    }


}