package com.gianlucadurelli.coding.hackerrank.practice.datastructures.trees;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Lca {

	public static class Node {
		int data;
		Node left;
		Node right;

		public Node(int data) {
			this.data = data;
		}
	}

	public static Node lca(Node root, int v1, int v2) {
		List<Node> pathToV1 = new LinkedList<>();
		List<Node> pathToV2 = new LinkedList<>();

		pathToNode(root, v1, pathToV1);
		pathToNode(root, v2, pathToV2);

		Node result = root;
		ListIterator<Node> v1Iterator = pathToV1.listIterator();
		ListIterator<Node> v2Iterator = pathToV2.listIterator();
		while(v1Iterator.hasNext() && v2Iterator.hasNext()) {
			Node tmp1 = v1Iterator.next();
			Node tmp2 = v2Iterator.next();

			if (tmp1.data == tmp2.data) {
				result = tmp1;
			} else {
				return result;
			}
		}

		return result;
	}

	private static void pathToNode(Node node, int v, List<Node> path) {
		path.add(node);
		if (v < node.data) {
			pathToNode(node.left, v, path);
		} else if (v > node.data) {
			pathToNode(node.right, v, path);
		}
	}
}
