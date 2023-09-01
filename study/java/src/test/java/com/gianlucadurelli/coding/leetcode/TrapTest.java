package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrapTest {

    private static final Trap solver = new Trap();

    @Test
    public void example1() {
        Assertions.assertThat(
                solver.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1})
        ).isEqualTo(6);
    }

    @Test
    public void example2() {
        Assertions.assertThat(
                solver.trap(new int[]{4,2,0,3,2,5})
        ).isEqualTo(9);
    }

}