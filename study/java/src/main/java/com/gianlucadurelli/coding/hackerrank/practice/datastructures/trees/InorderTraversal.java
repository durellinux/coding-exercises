package com.gianlucadurelli.coding.hackerrank.practice.datastructures.trees;


import java.util.Objects;

public class InorderTraversal {

	public static class Node {
		int data;
		Node left;
		Node right;
	}

	void inOrder(Node root) {
		if (!Objects.isNull(root)) {
			inOrder(root.left);
			System.out.print(root.data + " ");
			inOrder(root.right);
		}
	}

}
