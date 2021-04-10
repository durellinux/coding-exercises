package com.gianlucadurelli.coding.leetcode;


import java.util.Objects;

public class RemoveLinkedListElements {
	public static class ListNode {
		public int val;
		public ListNode next;
		public ListNode() {}
		public ListNode(int val) { this.val = val; }
		public ListNode(int val, ListNode next) { this.val = val; this.next = next; }
	}

	public ListNode solve(ListNode head, int val) {
		ListNode cur = head;
		ListNode result = null;
		ListNode lastResult = null;

		while(cur != null) {
			if (cur.val != val) {
				if (result == null) {
					result = new ListNode(cur.val);
					lastResult = result;
				} else {
					lastResult.next = new ListNode(cur.val);
					lastResult = lastResult.next;
				}
			}
			cur = cur.next;
		}

		return result;
	}

	public ListNode solve2(ListNode head, int val) {
		ListNode cur = head;

		if (head == null) {
			return null;
		}

		while(cur!=null) {
			ListNode next = cur.next;
			while(next != null) {
				if (next.val == val) {
					cur.next = next.next;
					next = next.next;
				} else {
					break;
				}
			}

			cur = cur.next;
		}

		if (head.val == 1) {
			return head.next;
		}

		return head;
	}
}
