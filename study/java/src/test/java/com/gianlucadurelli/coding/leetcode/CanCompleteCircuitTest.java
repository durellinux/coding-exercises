package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class CanCompleteCircuitTest {

    private static final CanCompleteCircuit solver = new CanCompleteCircuit();

    @Test
    public void example1() {
        Assertions.assertThat(
                solver.canCompleteCircuit(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2})
        ).isEqualTo(3);
    }

    @Test
    public void example2() {
        Assertions.assertThat(
                solver.canCompleteCircuit(new int[]{2,3,4}, new int[]{3,4,3})
        ).isEqualTo(-1);
    }

}