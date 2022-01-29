package com.gianlucadurelli.coding.hackerrank.practice.datastructures.trees;


import java.util.LinkedList;
import java.util.Queue;

public class TopView {

	public static class Node {
		int data;
		Node left;
		Node right;
	}

	public static void topView(Node root) {
		if (root != null) {
			printTop(root.left, true);
			print(root);
			printTop(root.right, false);
		}
	}

	public static void printTop(Node node, boolean goLeft) {
		if (node != null) {
			if (goLeft) {
				printTop(node.left, goLeft);
				print(node);
			} else {
				print(node);
				printTop(node.right, goLeft);
			}
		}
	}

	public static void print(Node node) {
		System.out.print(node.data + " ");
	}
}
