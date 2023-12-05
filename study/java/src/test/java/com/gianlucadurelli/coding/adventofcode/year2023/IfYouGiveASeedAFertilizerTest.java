package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.List;

import javax.xml.stream.events.Characters;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import junit.framework.TestCase;

import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.SEEDS_INPUT_FERTILIZERS;
import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.SEEDS_INPUT_HUMIDITIES;
import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.SEEDS_INPUT_LIGHTS;
import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.SEEDS_INPUT_LOCATIONS;
import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.SEEDS_INPUT_SEEDS;
import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.SEEDS_INPUT_SOILS;
import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.SEEDS_INPUT_TEMPERATURES;
import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.SEEDS_INPUT_WATERS;

public class IfYouGiveASeedAFertilizerTest extends TestCase {

    public static final IfYouGiveASeedAFertilizer solver = new IfYouGiveASeedAFertilizer();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve(
                solver.parseSeeds1("79 14 55 13"),
            List.of("50 98 2", "52 50 48"),
            List.of("0 15 37", "37 52 2", "39 0 15"),
            List.of("49 53 8", "0 11 42", "42 0 7", "57 7 4"),
            List.of("88 18 7", "18 25 70"),
            List.of("45 77 23", "81 45 19", "68 64 13"),
            List.of("0 69 1", "1 0 69"),
            List.of("60 56 37", "56 93 4")
        )).isEqualTo(35);
    }

    @Test
    public void testSolution1() {
        Assertions.assertThat(solver.solve(
            solver.parseSeeds1(SEEDS_INPUT_SEEDS),
            SEEDS_INPUT_SOILS,
            SEEDS_INPUT_FERTILIZERS,
            SEEDS_INPUT_WATERS,
            SEEDS_INPUT_LIGHTS,
            SEEDS_INPUT_TEMPERATURES,
            SEEDS_INPUT_HUMIDITIES,
            SEEDS_INPUT_LOCATIONS
        )).isEqualTo(403695602);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve(
                solver.parseSeeds2("79 14 55 13"),
                List.of("50 98 2", "52 50 48"),
                List.of("0 15 37", "37 52 2", "39 0 15"),
                List.of("49 53 8", "0 11 42", "42 0 7", "57 7 4"),
                List.of("88 18 7", "18 25 70"),
                List.of("45 77 23", "81 45 19", "68 64 13"),
                List.of("0 69 1", "1 0 69"),
                List.of("60 56 37", "56 93 4")
        )).isEqualTo(46);
    }

    @Test
    public void testSolution2() {
        Assertions.assertThat(solver.solve(
                solver.parseSeeds2(SEEDS_INPUT_SEEDS),
                SEEDS_INPUT_SOILS,
                SEEDS_INPUT_FERTILIZERS,
                SEEDS_INPUT_WATERS,
                SEEDS_INPUT_LIGHTS,
                SEEDS_INPUT_TEMPERATURES,
                SEEDS_INPUT_HUMIDITIES,
                SEEDS_INPUT_LOCATIONS
        )).isEqualTo(219529182);
    }
}