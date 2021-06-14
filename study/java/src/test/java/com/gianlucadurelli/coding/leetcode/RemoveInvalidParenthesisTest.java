package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RemoveInvalidParenthesisTest {

	@Test
	public void test() {
		RemoveInvalidParenthesis solver = new RemoveInvalidParenthesis();
		Assertions.assertThat(solver.removeInvalidParentheses("()())()")).containsExactlyInAnyOrderElementsOf(
				List.of("(())()","()()()")
		);
	}

}