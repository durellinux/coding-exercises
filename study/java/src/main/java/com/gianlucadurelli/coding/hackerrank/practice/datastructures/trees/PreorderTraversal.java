package com.gianlucadurelli.coding.hackerrank.practice.datastructures.trees;


import java.util.Objects;

public class PreorderTraversal {

	public static class Node {
		int data;
		Node left;
		Node right;
	}

	void preOrder(Node root) {
		if (!Objects.isNull(root)) {
			System.out.print(root.data + " ");
			preOrder(root.left);
			preOrder(root.right);
		}
	}

}
