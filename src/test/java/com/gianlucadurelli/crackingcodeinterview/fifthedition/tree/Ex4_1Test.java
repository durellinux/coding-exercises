package com.gianlucadurelli.crackingcodeinterview.fifthedition.tree;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class Ex4_1Test {

    private Ex4_1<Integer> solver = new Ex4_1<>();

    @Test
    public void balancedTree() {
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4, null, null);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3, null, null);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2, n4, null);
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1, n3, null);
        BinaryTreeNode<Integer> n0 = new BinaryTreeNode<>(0, n1, n2);

        boolean isBalanced = solver.isBalanced(n0);
        Assertions.assertThat(isBalanced).isTrue();
    }

    @Test
    public void unbalancedTree() {
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4, null, null);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3, n4, null);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2, null, null);
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1, n3, null);
        BinaryTreeNode<Integer> n0 = new BinaryTreeNode<>(0, n1, n2);

        boolean isBalanced = solver.isBalanced(n0);
        Assertions.assertThat(isBalanced).isFalse();
    }
}