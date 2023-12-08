package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.HAUNTED_WASTELAND_INPUT_COMMANDS;
import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.HAUNTED_WASTELAND_INPUT_MAP;

public class HauntedWastelandTest {

    private static final HauntedWasteland solver = new HauntedWasteland();

    @Test
    public void test1() {
        Assertions
                .assertThat(solver.solve1("RL", List.of("AAA = (BBB, CCC)", "BBB = (DDD, EEE)", "CCC = (ZZZ, GGG)", "DDD = (DDD, DDD)", "EEE = (EEE, EEE)", "GGG = (GGG, GGG)", "ZZZ = (ZZZ, ZZZ)")))
                .isEqualTo(2);

        Assertions
                .assertThat(solver.solve1("LLR", List.of("AAA = (BBB, BBB)", "BBB = (AAA, ZZZ)", "ZZZ = (ZZZ, ZZZ)")))
                .isEqualTo(6);
    }

    @Test
    public void testSolution1() {
        Assertions
                .assertThat(solver.solve1(HAUNTED_WASTELAND_INPUT_COMMANDS, HAUNTED_WASTELAND_INPUT_MAP))
                .isEqualTo(19199);
    }

    @Test
    public void test2() {
        Assertions
                .assertThat(solver.solve2("LR", List.of("11A = (11B, XXX)", "11B = (XXX, 11Z)", "11Z = (11B, XXX)", "22A = (22B, XXX)", "22B = (22C, 22C)", "22C = (22Z, 22Z)", "22Z = (22B, 22B)", "XXX = (XXX, XXX)")))
                .isEqualTo(6);
    }

    @Test
    public void testSolution2() {
        Assertions
                .assertThat(solver.solve2(HAUNTED_WASTELAND_INPUT_COMMANDS, HAUNTED_WASTELAND_INPUT_MAP))
                .isEqualTo(13663968099527L);
    }

}