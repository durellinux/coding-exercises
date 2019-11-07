package com.gianlucadurelli.crackingcodeinterview.fifthedition.tree;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TreeSolverTest {

    private ITreeSolver<Integer> solver = new TreeSolver<>();

    @Test
    public void Ex4_1_balancedTree() {
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4, null, null);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3, null, null);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2, n4, null);
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1, n3, null);
        BinaryTreeNode<Integer> n0 = new BinaryTreeNode<>(0, n1, n2);

        boolean isBalanced = solver.isBalanced(n0);
        Assertions.assertThat(isBalanced).isTrue();
    }

    @Test
    public void Ex4_1_unbalancedTree() {
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4, null, null);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3, n4, null);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2, null, null);
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1, n3, null);
        BinaryTreeNode<Integer> n0 = new BinaryTreeNode<>(0, n1, n2);

        boolean isBalanced = solver.isBalanced(n0);
        Assertions.assertThat(isBalanced).isFalse();
    }


    @Test
    public void Ex4_3_buildBinarySearchTree() {
        Integer[] sortedValues = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        BinaryTreeNode<Integer> searchTree = solver.buildBinarySearchTree(sortedValues);

        Assertions.assertThat(searchTree).isNotNull();
        Assertions.assertThat(searchTree.isBinarySearch(Integer::compareTo)).isNotNull();
    }


    @Test
    public void Ex4_4_getListOfNodesPerLevel() {
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4, null, null);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3, null, null);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2, n4, null);
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1, n3, null);
        BinaryTreeNode<Integer> n0 = new BinaryTreeNode<>(0, n1, n2);

        List<List<BinaryTreeNode<Integer>>> expectedResult = List.of(
                List.of(n0),
                List.of(n1, n2),
                List.of(n3, n4)
        );

        List<List<BinaryTreeNode<Integer>>> actual = solver.getListOfNodesPerLevel(n0);

        // TODO: Assertions
    }
}