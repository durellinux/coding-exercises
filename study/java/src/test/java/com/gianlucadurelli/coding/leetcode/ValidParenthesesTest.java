package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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