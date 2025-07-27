package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MajorityElementTest {

    private static MajorityElement majorityElement = new MajorityElement();

    @Test
    public void example1() {
        int result = majorityElement.majorityElement(new int[]{3, 2, 3});
        Assertions.assertThat(result).isEqualTo(3);
    }

    @Test
    public void example2() {
        int result = majorityElement.majorityElement(new int[]{2,2,1,1,1,2,2});
        Assertions.assertThat(result).isEqualTo(2);
    }
}