package com.gianlucadurelli.coding.hackerrank.neurodiversity;


import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class DiversePopulationPairsTest {

    @Test
    public void case1() {
        long result = DiversePopulationPairs.interestingPairs(List.of(1, 3, 2, 0), 2);
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    public void case2() {
        long result = DiversePopulationPairs.interestingPairs(List.of(1, 3, 2, 0), 3);
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    public void case3() {
        long result = DiversePopulationPairs.interestingPairs(List.of(1, 4, -1, 2), 4);
        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    public void test2() {
        long result = DiversePopulationPairs.interestingPairs(List.of(1, 1, 2, 0), 2);
        Assertions.assertThat(result).isEqualTo(3);
    }
}