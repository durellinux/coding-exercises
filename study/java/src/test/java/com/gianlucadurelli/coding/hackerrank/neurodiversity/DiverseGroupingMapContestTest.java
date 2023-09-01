package com.gianlucadurelli.coding.hackerrank.neurodiversity;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class DiverseGroupingMapContestTest {
    @Test
    @Ignore
    public void case1() {
        int solution = DiverseGroupingMapContest.numberOfPartitions(List.of(1, 2, 3, 3));
        Assertions.assertThat(solution).isEqualTo(4);
    }
}