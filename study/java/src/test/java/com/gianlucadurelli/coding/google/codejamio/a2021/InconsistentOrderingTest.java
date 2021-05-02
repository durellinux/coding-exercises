package com.gianlucadurelli.coding.google.codejamio.a2021;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class InconsistentOrderingTest {

    //3
    //2
    //2 3
    //2
    //5 1
    //1
    //2

    @Test
    public void sampleTest() {
        String t1 = InconsistentOrdering.solve(List.of(2, 3));
        Assertions.assertThat(t1).isEqualTo("ABDCBA");

        String t2 = InconsistentOrdering.solve(List.of(5, 1));
        Assertions.assertThat(t2).isEqualTo("ABCDEFA");

        String t3 = InconsistentOrdering.solve(List.of(2));
        Assertions.assertThat(t3).isEqualTo("ABC");
    }

}