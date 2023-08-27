package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RotateArrayTest {

    private static final RotateArray solver = new RotateArray();

    @Test
    public void emptyArray() {
        int[] array = {};
        int[] expected = {};
        solver.rotate(array, 100);
        Assertions.assertThat(array).isEqualTo(expected);
    }

    @Test
    public void oneElementArray() {
        int[] array = {1};
        int[] expected = {1};
        solver.rotate(array, 100);
        Assertions.assertThat(array).isEqualTo(expected);
    }

    @Test
    public void rotate1() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = {5, 1, 2, 3, 4};
        solver.rotate(array, 1);
        Assertions.assertThat(array).isEqualTo(expected);
    }

    @Test
    public void rotateSome() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = {3, 4, 5, 1, 2};
        solver.rotate(array, 3);
        Assertions.assertThat(array).isEqualTo(expected);
    }

    @Test
    public void noRotations() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        solver.rotate(array, 0);
        Assertions.assertThat(array).isEqualTo(expected);
    }

    @Test
    public void arrayLengthRotations() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        solver.rotate(array, array.length);
        Assertions.assertThat(array).isEqualTo(expected);
    }

    @Test
    public void moreRotationsThanArrayLength() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = {4, 5, 1, 2, 3};
        solver.rotate(array, 3 * array.length + 2);
        Assertions.assertThat(array).isEqualTo(expected);
    }

    @Test
    public void case2() {
        int[] array = {-1,-100,3,99};
        int[] expected = {3,99,-1,-100};
        solver.rotate(array, 2);
        Assertions.assertThat(array).isEqualTo(expected);
    }

}