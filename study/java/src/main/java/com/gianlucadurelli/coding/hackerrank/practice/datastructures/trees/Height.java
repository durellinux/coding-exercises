package com.gianlucadurelli.coding.hackerrank.practice.datastructures.trees;


import java.util.Objects;

public class Height {

	public static class Node {
		int data;
		Node left;
		Node right;
	}

	public static int height(Node root) {
		if (root == null) {
			return 0;
		}

		int leftHeight = 0;
		int rightHeight = 0;

		if (root.left != null) {
			leftHeight = 1 + height(root.left);
		}

		if (root.right != null) {
			rightHeight = 1 + height(root.right);
		}

		return Math.max(leftHeight, rightHeight);
	}

}
