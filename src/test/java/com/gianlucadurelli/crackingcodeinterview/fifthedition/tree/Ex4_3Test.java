package com.gianlucadurelli.crackingcodeinterview.fifthedition.tree;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class Ex4_3Test {

    private Ex4_3<Integer> solver = new Ex4_3<>();

    @Test
    public void buildBinarySearchTree() {
        Integer[] sortedValues = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        BinaryTreeNode<Integer> searchTree = solver.buildBinarySearchTree(sortedValues);

        Assertions.assertThat(searchTree).isNotNull();
        Assertions.assertThat(searchTree.isBinarySearch(Integer::compareTo)).isNotNull();
    }
}