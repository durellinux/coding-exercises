package com.gianlucadurelli.coding.hackerrank.neurodiversity;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class DiversityDayMarathonTest {

    @Test
    public void case1() {
        int clockwise = DiversityDayMarathon.maxClockwiseRuns(15, List.of(50, 50, 50, 40, 10));
        Assertions.assertThat(clockwise).isEqualTo(4);
    }

    @Test
    public void case2() {
        int clockwise = DiversityDayMarathon.maxClockwiseRuns(10, List.of(5, 4));
        Assertions.assertThat(clockwise).isEqualTo(-1);
    }

}