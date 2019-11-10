package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.treegraph;

import java.util.Comparator;

public class BinaryTreeNode<T> {
    BinaryTreeNode<T> parent;
    T data;
    BinaryTreeNode<T> left;
    BinaryTreeNode<T> right;

    public BinaryTreeNode(T data, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;

        if (left != null) {
            left.parent = this;
        }
        if (right != null) {
            right.parent = this;
        }
    }

    public T getData() {
        return data;
    }

    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    public BinaryTreeNode<T> getRight() {
        return right;
    }

    // Ex4_5
    public boolean isBinarySearch(Comparator<T> comparator) {
        boolean leftOk = left == null || comparator.compare(left.data, data) <= 0;
        boolean rightOk= right == null || comparator.compare(data, right.data) <= 0;

        boolean isBinarySearchLeft = left == null || left.isBinarySearch(comparator);
        boolean isBinarySearchRight = right == null || right.isBinarySearch(comparator);

        return leftOk && rightOk && isBinarySearchLeft && isBinarySearchRight;
    }

    public BinaryTreeNode<T> getParent() {
        return parent;
    }
}
