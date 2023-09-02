package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class FindIndexOfFirstOccurrenceTest {

    private static final FindIndexOfFirstOccurrence solver = new FindIndexOfFirstOccurrence();

    @Test
    public void example1() {
        Assertions.assertThat(solver.strStr("sadbutsad", "sad"))
                .isEqualTo(0);
    }

    @Test
    public void example2() {
        Assertions.assertThat(solver.strStr("leetcode", "leeto"))
                .isEqualTo(-1);
    }

}