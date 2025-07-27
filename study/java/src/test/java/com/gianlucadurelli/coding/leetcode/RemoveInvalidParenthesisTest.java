package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveInvalidParenthesisTest {

	@Test
	public void test() {
		RemoveInvalidParenthesis solver = new RemoveInvalidParenthesis();
		Assertions.assertThat(solver.removeInvalidParentheses("()())()")).containsExactlyInAnyOrderElementsOf(
				List.of("(())()","()()()")
		);
	}

}