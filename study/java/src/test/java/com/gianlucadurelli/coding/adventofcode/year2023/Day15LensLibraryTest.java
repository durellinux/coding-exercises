package com.gianlucadurelli.coding.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day15LensLibraryTest {

    private static final Day15LensLibrary solver = new Day15LensLibrary();
    private static final String INPUT = AdventOfCode2023Inputs.loadInput(Day15LensLibrary.class).get(0);

    @Test
    public void test1() {
        Assertions.assertThat(solver.solve("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7")).isEqualTo(1320);
    }

    @Test
    public void solution1() {
        Assertions.assertThat(solver.solve(INPUT)).isEqualTo(502139);
    }

    @Test
    public void test2() {
        Assertions.assertThat(solver.solve2("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7")).isEqualTo(145L);
    }

    @Test
    public void solution2() {
        Assertions.assertThat(solver.solve2(INPUT)).isEqualTo(284132L);
    }


}