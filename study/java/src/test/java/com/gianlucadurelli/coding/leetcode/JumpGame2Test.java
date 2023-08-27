package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class JumpGame2Test {

    private static final JumpGame2 solver = new JumpGame2();

    @Test
    public void example1() {
        int[] nums = {2,3,1,1,4};
        int result = solver.jump(nums);
        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    public void example2() {
        int[] nums = {2,3,0,1,4};
        int result = solver.jump(nums);
        Assertions.assertThat(result).isEqualTo(2);
    }


}