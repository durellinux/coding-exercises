package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class JumpGameTest {

    private static final JumpGame solver = new JumpGame();

    @Test
    public void example1() {
        int[] nums = {2,3,1,1,4};
        boolean result = solver.canJump(nums);
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void example2() {
        int[] nums = {3,2,1,0,4};
        boolean result = solver.canJump(nums);
        Assertions.assertThat(result).isFalse();
    }

}