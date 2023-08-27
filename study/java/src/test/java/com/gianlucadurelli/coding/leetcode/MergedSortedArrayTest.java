package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MergedSortedArrayTest {

    MergedSortedArray solver = new MergedSortedArray();


    @Test
    public void example1() {
        int[] nums1 = {1,2,3,0,0,0};
        solver.merge(
                nums1,
                3,
                new int[]{2,5,6},
                3
        );

        Assertions.assertThat(nums1).isEqualTo(new int[]{1,2,2,3,5,6});
    }

    @Test
    public void example2() {
        int[] nums1 = {1};
        solver.merge(
                nums1,
                1,
                new int[]{},
                0
        );

        Assertions.assertThat(nums1).isEqualTo(new int[]{1});
    }

    @Test
    public void example3() {
        int[] nums1 = {0};
        solver.merge(
                nums1,
                0,
                new int[]{1},
                1
        );

        Assertions.assertThat(nums1).isEqualTo(new int[]{1});
    }

    @Test
    public void example4() {
        int[] nums1 = {4,5,6,0,0,0};
        solver.merge(
                nums1,
                3,
                new int[]{1,2,3},
                3
        );

        Assertions.assertThat(nums1).isEqualTo(new int[]{1,2,3,4,5,6});
    }
}