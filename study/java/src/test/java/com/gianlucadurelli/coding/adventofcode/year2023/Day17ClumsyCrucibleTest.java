package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.adventofcode.AdventOfCodeTestHelpers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Day17ClumsyCrucibleTest {

    private static final Day17ClumsyCrucible solver = new Day17ClumsyCrucible();
    private static final List<String> TEST_INPUT = AdventOfCodeTestHelpers.loadTestInput(Day17ClumsyCrucible.class);

    private static final List<String> TEST_INPUT_2 = AdventOfCodeTestHelpers.loadWithSuffix(Day17ClumsyCrucible.class, "-test2.txt");

    private static final List<String> INPUT = AdventOfCodeTestHelpers.loadInput(Day17ClumsyCrucible.class);

    private static final Day17ClumsyCrucible.CrucibleConfig NORMAL_CRUCIBLE = Day17ClumsyCrucible.CrucibleConfig.normalCrucible();
    private static final Day17ClumsyCrucible.CrucibleConfig ULTRA_CRUCIBLE = Day17ClumsyCrucible.CrucibleConfig.ultraCrucible();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(TEST_INPUT, NORMAL_CRUCIBLE)).isEqualTo(102);
    }

    @Test
    public void solution1() {
        Assertions.assertThat(solver.solve(INPUT, NORMAL_CRUCIBLE)).isEqualTo(722);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve(TEST_INPUT, ULTRA_CRUCIBLE)).isEqualTo(94);
    }

    @Test
    public void test2_2() {
        Assertions.assertThat(solver.solve(TEST_INPUT_2, ULTRA_CRUCIBLE)).isEqualTo(71);
    }

    @Test
    public void solution2() {
        Assertions.assertThat(solver.solve(INPUT, ULTRA_CRUCIBLE)).isEqualTo(894);
    }


}