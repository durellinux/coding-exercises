package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.HOT_SPRINGS_INPUT;
import static org.junit.Assert.*;

public class Day12HotSpringsTest {

    private static final Day12HotSprings solver = new Day12HotSprings();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(
                List.of("???.### 1,1,3", ".??..??...?##. 1,1,3", "?#?#?#?#?#?#?#? 1,3,1,6", "????.#...#... 4,1,1", "????.######..#####. 1,6,5", "?###???????? 3,2,1")
        )).isEqualTo(21);
    }

    @Test
    public void solution1() {
        Assertions.assertThat(solver.solve(HOT_SPRINGS_INPUT)).isEqualTo(7916);
    }

}