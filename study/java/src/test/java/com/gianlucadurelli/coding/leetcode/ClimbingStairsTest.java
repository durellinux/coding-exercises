package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClimbingStairsTest {

    @Test
    public void test() {
        ClimbingStairs solver = new ClimbingStairs();
        Assertions.assertThat(solver.climbStairs(4)).isEqualTo(5);
        Assertions.assertThat(solver.climbStairs(8)).isEqualTo(34);
    }

}