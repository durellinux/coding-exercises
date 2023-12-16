package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static com.gianlucadurelli.coding.adventofcode.year2023.AdventOfCode2023Inputs.LENS_LIBRARY_INPUT;

public class Day15LensLibraryTest {

    private static final Day15LensLibrary solver = new Day15LensLibrary();

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7")).isEqualTo(1320);
    }

    @Test
    public void solution1() {
        Assertions.assertThat(solver.solve(LENS_LIBRARY_INPUT)).isEqualTo(502139);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7")).isEqualTo(145L);
    }

    @Test
    public void solution2() {
        Assertions.assertThat(solver.solve2(LENS_LIBRARY_INPUT)).isEqualTo(284132L);
    }


}