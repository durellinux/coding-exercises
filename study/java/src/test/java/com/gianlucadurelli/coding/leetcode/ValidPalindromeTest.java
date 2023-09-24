package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidPalindromeTest {

    private static final ValidPalindrome solver = new ValidPalindrome();

    @Test
    public void example1() {
        Assertions.assertThat(
                solver.isPalindrome("A man, a plan, a canal: Panama")
        ).isTrue();
    }

    @Test
    public void example2() {
        Assertions.assertThat(
                solver.isPalindrome("race a car")
        ).isFalse();
    }

    @Test
    public void example3() {
        Assertions.assertThat(
                solver.isPalindrome(" ")
        ).isTrue();
    }

    @Test
    public void example4() {
        Assertions.assertThat(
                solver.isPalindrome("")
        ).isTrue();
    }

    @Test
    public void example5() {
        Assertions.assertThat(
                solver.isPalindrome("A 0an, a plan, a canal: Pana0a")
        ).isTrue();
    }

}