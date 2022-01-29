package com.gianlucadurelli.coding.leetcode;

//https://leetcode.com/explore/interview/card/facebook/52/trees-and-graphs/322/
public class FlattenBinaryTree {
	 public static class TreeNode {
	      int val;
	      TreeNode left;
	      TreeNode right;
	      TreeNode() {}
	      TreeNode(int val) { this.val = val; }
	      TreeNode(int val, TreeNode left, TreeNode right) {
	          this.val = val;
	          this.left = left;
	          this.right = right;
	      }
	 }

	public void flatten(TreeNode root) {
	 	if (root == null) {
	 		return;
	    }

		TreeNode dummy = new TreeNode();
		preOrderTraversal(root, dummy);
		root.left = null;
		root.right = dummy.right.right;
	}

	private TreeNode preOrderTraversal(TreeNode root, TreeNode appendTo) {
		TreeNode result = appendTo;
	 	if (root != null) {
		    appendTo.right = new TreeNode(root.val);

		    result = preOrderTraversal(root.left, appendTo.right);
		    result = preOrderTraversal(root.right, result);
	    }
		return result;
	}
}
