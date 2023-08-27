package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RemoveDuplicatesSortedArray2Test {

    private static final RemoveDuplicatesSortedArray2 solver = new RemoveDuplicatesSortedArray2();

    @Test
    public void nullArray() {
        Assertions.assertThat(solver.removeDuplicates(null)).isEqualTo(0);
    }

    @Test
    public void emptyArray() {
        int[] array = {};
        Assertions.assertThat(solver.removeDuplicates(array)).isEqualTo(0);
    }

    @Test
    public void oneElement() {
        int[] array = {1};
        Assertions.assertThat(solver.removeDuplicates(array)).isEqualTo(1);
        Assertions.assertThat(array).containsExactly(1);
    }

    @Test
    public void allRepeated() {
        int[] array = {1, 1, 1, 1};
        int[] expected = {1, 1};
        Assertions.assertThat(solver.removeDuplicates(array)).isEqualTo(expected.length);
        Assertions.assertThat(Arrays.copyOfRange(array, 0, expected.length)).isEqualTo(expected);
    }

    @Test
    public void differentValues() {
        int[] array = {1, 1, 2, 2, 3, 3, 3, 4, 5, 5, 6};
        int[] expected = {1, 1, 2, 2, 3, 3, 4, 5, 5, 6};
        Assertions.assertThat(solver.removeDuplicates(array)).isEqualTo(expected.length);
        Assertions.assertThat(Arrays.copyOfRange(array, 0, expected.length)).isEqualTo(expected);
    }

}