package com.gianlucadurelli.coding.hackerrank.practice.datastructures.trees;


import java.util.Objects;

public class PostorderTraversal {

	public static class Node {
		int data;
		Node left;
		Node right;
	}

	void postOrder(Node root) {
		if (!Objects.isNull(root)) {
			postOrder(root.left);
			postOrder(root.right);
			System.out.print(root.data + " ");
		}
	}

}
