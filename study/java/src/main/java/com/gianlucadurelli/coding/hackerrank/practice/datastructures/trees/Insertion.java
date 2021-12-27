package com.gianlucadurelli.coding.hackerrank.practice.datastructures.trees;


import java.util.Objects;

public class Insertion {

	public static class Node {
		int data;
		Node left;
		Node right;

		public Node(int data) {
			this.data = data;
		}
	}

	public static Node insert(Node root,int data) {
		Node newNode = new Node(data);
		newNode.left = null;
		newNode.right = null;

		if (root == null) {
			return newNode;
		}

		insertRecursive(root, newNode);
		return root;
	}

	public static void insertRecursive(Node root, Node newNode) {
		if (newNode.data < root.data) {
			if (root.left == null) {
				root.left = newNode;
			} else {
				insertRecursive(root.left, newNode);
			}
		} else {
			if (root.right == null) {
				root.right = newNode;
			} else {
				insertRecursive(root.right, newNode);
			}
		}
	}

}
