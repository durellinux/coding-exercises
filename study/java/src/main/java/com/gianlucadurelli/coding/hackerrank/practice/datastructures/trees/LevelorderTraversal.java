package com.gianlucadurelli.coding.hackerrank.practice.datastructures.trees;


import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class LevelorderTraversal {

	public static class Node {
		int data;
		Node left;
		Node right;
	}

	public static void levelOrder(Node root) {
		if (root != null) {
			Queue<Node> queue = new LinkedList<>();
			queue.add(root);

			while(!queue.isEmpty()) {
				Node current = queue.remove();
				System.out.print(current.data + " ");

				if (current.left != null) {
					queue.add(current.left);
				}

				if (current.right != null) {
					queue.add(current.right);
				}
			}
		}
	}

}
