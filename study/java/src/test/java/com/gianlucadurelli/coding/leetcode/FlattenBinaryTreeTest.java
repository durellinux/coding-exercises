package com.gianlucadurelli.coding.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class FlattenBinaryTreeTest {

	@Test
	public void test() {
		FlattenBinaryTree.TreeNode n1 = new FlattenBinaryTree.TreeNode(1);
		FlattenBinaryTree.TreeNode n2 = new FlattenBinaryTree.TreeNode(2);
		FlattenBinaryTree.TreeNode n3 = new FlattenBinaryTree.TreeNode(3);
		FlattenBinaryTree.TreeNode n4 = new FlattenBinaryTree.TreeNode(4);
		FlattenBinaryTree.TreeNode n5 = new FlattenBinaryTree.TreeNode(5);
		FlattenBinaryTree.TreeNode n6 = new FlattenBinaryTree.TreeNode(6);

		n1.left = n2;
		n1.right = n5;
		n2.left = n3;
		n2.right = n4;
		n5.right = n6;

		FlattenBinaryTree solver = new FlattenBinaryTree();
		solver.flatten(n1);

		// TODO: Actual test
		System.out.print("Solved");
	}

	@Test
	public void testNull() {
		FlattenBinaryTree solver = new FlattenBinaryTree();
		solver.flatten(null);

		// TODO: Actual test
		System.out.print("Solved");
	}

	@Test
	public void testSingle() {
		FlattenBinaryTree.TreeNode n1 = new FlattenBinaryTree.TreeNode(0);

		FlattenBinaryTree solver = new FlattenBinaryTree();
		solver.flatten(n1);

		// TODO: Actual test
		System.out.print("Solved");
	}

}