package com.gianlucadurelli.crackingcodeinterview.fifthedition.tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TreeSolver<T> implements ITreeSolver<T> {

    @Override
    public boolean isBalanced(BinaryTreeNode<T> root) {
        IsBalancedHelper result = isBalancedInner(root);
        return result.isBalanced();
    }

    private IsBalancedHelper isBalancedInner(BinaryTreeNode<T> root) {
        if (root == null) {
            return new IsBalancedHelper(true, 0);
        }

        IsBalancedHelper leftResult = isBalancedInner(root.getLeft());
        IsBalancedHelper rightResult = isBalancedInner(root.getRight());

        int nextHeight = Math.max(leftResult.getHeight(), rightResult.getHeight()) + 1;
        boolean isBalanced = Math.abs(leftResult.getHeight() - rightResult.getHeight()) <= 1 && leftResult.isBalanced() && rightResult.isBalanced();
        return new IsBalancedHelper(isBalanced, nextHeight);
    }

    @Override
    public BinaryTreeNode buildBinarySearchTree(T[] sortedValues) {
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

    @Override
    public List<List<BinaryTreeNode<T>>> getListOfNodesPerLevel(BinaryTreeNode<T> node) {
        List<List<BinaryTreeNode<T>>> result = new LinkedList<>();
        Deque<NodeAndHeight<T>> nodesToVisit = new LinkedList<>();
        nodesToVisit.push(new NodeAndHeight<>(node, 0));
        fillLists(nodesToVisit, result);
        return result;
    }

    private void fillLists(Deque<NodeAndHeight<T>> nodesToVisit, List<List<BinaryTreeNode<T>>> result) {
        while(!nodesToVisit.isEmpty()) {
            NodeAndHeight<T> currentVisited = nodesToVisit.pop();
            int currentHeight = currentVisited.getHeight();
            BinaryTreeNode<T> node = currentVisited.getNode();

            if (node != null) {
                if (result.size() <= currentHeight) {
                    result.add(new LinkedList<>());
                }
                result.get(currentHeight).add(node);

                nodesToVisit.push(new NodeAndHeight<>(node.left, currentHeight + 1));
                nodesToVisit.push(new NodeAndHeight<>(node.right, currentHeight + 1));
            }
        }
    }

}
