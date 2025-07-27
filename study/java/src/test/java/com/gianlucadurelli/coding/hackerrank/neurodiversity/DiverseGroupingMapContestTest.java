package com.gianlucadurelli.coding.hackerrank.neurodiversity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DiverseGroupingMapContestTest {
    @Test
    @Disabled
    public void case1() {
        int solution = DiverseGroupingMapContest.numberOfPartitions(List.of(1, 2, 3, 3));
        Assertions.assertThat(solution).isEqualTo(4);
    }
}