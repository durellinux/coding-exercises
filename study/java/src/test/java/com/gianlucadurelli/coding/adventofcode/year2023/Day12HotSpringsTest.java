package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day12HotSpringsTest {

    private static final Day12HotSprings solver = new Day12HotSprings();

    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day12HotSprings.class);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(
                List.of("???.### 1,1,3", ".??..??...?##. 1,1,3", "?#?#?#?#?#?#?#? 1,3,1,6", "????.#...#... 4,1,1", "????.######..#####. 1,6,5", "?###???????? 3,2,1")
        )).isEqualTo(21);
    }

    @Test
    public void solution1() {
        Assertions.assertThat(solver.solve(INPUT)).isEqualTo(7916);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2(
                List.of("???.### 1,1,3", ".??..??...?##. 1,1,3", "?#?#?#?#?#?#?#? 1,3,1,6", "????.#...#... 4,1,1", "????.######..#####. 1,6,5", "?###???????? 3,2,1"),
                5
        )).isEqualTo(525152);
    }

    @Test
    public void solution2() {
        Assertions.assertThat(solver.solve2(INPUT, 5)).isEqualTo(37366887898686L);
    }

}