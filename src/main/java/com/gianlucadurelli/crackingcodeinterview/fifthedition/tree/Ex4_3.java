package com.gianlucadurelli.crackingcodeinterview.fifthedition.tree;

import java.util.List;

public class Ex4_3<T> {

    public BinaryTreeNode<T> buildBinarySearchTree(T[] sortedValues) {
        return buildBinarySearchTree(sortedValues, 0, sortedValues.length);
    }

    private BinaryTreeNode<T> buildBinarySearchTree(T[] sortedValues, int first, int last) {
        int length = last - first;
        if (length == 0) {
            return null;
        }

        if (length == 1) {
            return new BinaryTreeNode<T>(sortedValues[first], null, null);
        }

        if (length == 2) {
            BinaryTreeNode<T> left = buildBinarySearchTree(sortedValues, first, first + 1);
            return new BinaryTreeNode<>(sortedValues[first + 1], left, null);
        }

        int middle = (first + last) / 2;
        BinaryTreeNode<T> left = buildBinarySearchTree(sortedValues, first, middle);
        BinaryTreeNode<T> right = buildBinarySearchTree(sortedValues, middle + 1, last);
        return new BinaryTreeNode<>(sortedValues[middle], left, right);
    }

}
