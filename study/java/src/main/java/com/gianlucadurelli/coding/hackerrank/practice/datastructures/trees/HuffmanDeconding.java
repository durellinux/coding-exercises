package com.gianlucadurelli.coding.hackerrank.practice.datastructures.trees;

public class HuffmanDeconding {

	public static class Node {
		public  int frequency;
		public  char data;
		public  Node left, right;
	}

	void decode(String s, Node root) {
		char[] chars = s.toCharArray();
		decode(root, chars, 0, root);
	}

	void decode(Node node, char[] chars, int index, Node root) {
		if (node.left == null && node.right == null) {
			System.out.print(node.data);
			decode(root, chars, index, root);
		} else {
			if (index < chars.length) {
				if (chars[index] == '0') {
					decode(node.left, chars, index + 1, root);
				} else {
					decode(node.right, chars, index + 1, root);
				}
			}
		}
	}

}
