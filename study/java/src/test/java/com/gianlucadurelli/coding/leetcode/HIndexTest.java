package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class HIndexTest {

    private static final HIndex solver = new HIndex();

    @Test
    public void example1() {
        Assertions.assertThat(
                solver.hIndex(new int[]{3,0,6,1,5})
        ).isEqualTo(3);
    }

    @Test
    public void example2() {
        Assertions.assertThat(
                solver.hIndex(new int[]{1,3,1})
        ).isEqualTo(1);
    }

}