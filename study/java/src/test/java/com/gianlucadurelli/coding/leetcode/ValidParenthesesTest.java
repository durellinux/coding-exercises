package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidParenthesesTest {

    @Test
    public void test() {
        ValidParentheses solver = new ValidParentheses();

        Assertions.assertThat(solver.isValid("()")).isTrue();
        Assertions.assertThat(solver.isValid("()[]{}")).isTrue();
        Assertions.assertThat(solver.isValid("(]")).isFalse();
        Assertions.assertThat(solver.isValid("([)]")).isFalse();
        Assertions.assertThat(solver.isValid("{[]}")).isTrue();
    }

}