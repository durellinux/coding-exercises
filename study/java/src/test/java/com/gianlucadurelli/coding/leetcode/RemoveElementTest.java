package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.chrono.IsoEra;

import static org.junit.Assert.*;

public class RemoveElementTest {

    RemoveElement solver = new RemoveElement();

    @Test
    public void example1() {
        int[] nums = {3,2,2,3};
        int res = solver.removeElement(nums, 3);
        Assertions.assertThat(res).isEqualTo(2);
        Assertions.assertThat(nums).isEqualTo(new int[]{2, 2, 3, 3});
    }

    @Test
    public void example2() {
        int[] nums = {0,1,2,2,3,0,4,2};
        int res = solver.removeElement(nums, 2);
        Assertions.assertThat(res).isEqualTo(5);
        Assertions.assertThat(nums).isEqualTo(new int[]{0,1,4,0,3, 2, 2, 2});
    }

}