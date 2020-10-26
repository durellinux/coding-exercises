package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class RemoveLinkedListElementsTest {

	@Test
	public void test() {
		RemoveLinkedListElements.ListNode listNode1 = new RemoveLinkedListElements.ListNode();
		listNode1.val = 1;
		RemoveLinkedListElements.ListNode listNode2 = new RemoveLinkedListElements.ListNode();
		listNode2.val = 1;
		RemoveLinkedListElements.ListNode listNode3 = new RemoveLinkedListElements.ListNode();
		listNode3.val = 1;

		listNode1.next = listNode2;
		listNode2.next = listNode3;
		listNode3.next = null;

		RemoveLinkedListElements removeLinkedListElements = new RemoveLinkedListElements();
		Assertions.assertThat(removeLinkedListElements.solve(listNode1, 1)).isNull();

		Assertions.assertThat(removeLinkedListElements.solve2(listNode1, 1)).isNull();

	}
}