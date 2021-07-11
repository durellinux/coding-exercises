package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class MissingNumberTest {

    @Test
    public void test() {
        MissingNumber solver = new MissingNumber();
        Assertions.assertThat(solver.missingNumber(new int[]{3,0,1})).isEqualTo(2);
        Assertions.assertThat(solver.missingNumber(new int[]{0,1})).isEqualTo(2);
        Assertions.assertThat(solver.missingNumber(new int[]{9,6,4,2,3,5,7,0,1})).isEqualTo(8);
        Assertions.assertThat(solver.missingNumber(new int[]{0})).isEqualTo(1);
        Assertions.assertThat(solver.missingNumber(new int[]{1})).isEqualTo(0);
    }
}