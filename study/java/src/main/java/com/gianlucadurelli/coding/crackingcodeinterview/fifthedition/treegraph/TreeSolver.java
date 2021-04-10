package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.treegraph;

import java.util.*;

public class TreeSolver implements ITreeSolver {

    @Override
    public <T> boolean isBalanced(BinaryTreeNode<T> root) {
        IsBalancedHelper result = isBalancedInner(root);
        return result.isBalanced();
    }

    private <T>  IsBalancedHelper isBalancedInner(BinaryTreeNode<T> root) {
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
    public <T>  BinaryTreeNode buildBinarySearchTree(T[] sortedValues) {
        return buildBinarySearchTree(sortedValues, 0, sortedValues.length);
    }

    private <T>  BinaryTreeNode<T> buildBinarySearchTree(T[] sortedValues, int first, int last) {
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
    public <T>  List<List<BinaryTreeNode<T>>> getListOfNodesPerLevel(BinaryTreeNode<T> node) {
        List<List<BinaryTreeNode<T>>> result = new LinkedList<>();
        Deque<NodeAndHeight<T>> nodesToVisit = new LinkedList<>();
        nodesToVisit.push(new NodeAndHeight<>(node, 0));
        fillLists(nodesToVisit, result);
        return result;
    }

    private <T>  void fillLists(Deque<NodeAndHeight<T>> nodesToVisit, List<List<BinaryTreeNode<T>>> result) {
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

    @Override
    public <T>  BinaryTreeNode<T> nextBinarySearchTreeNode(BinaryTreeNode<T> node) {
        if (node == null) {
            return null;
        }

        if (node.getRight() != null) {
            return getMinOnSubtree(node.getRight());
        } else {
            return getParentNotRight(node);
        }

    }

    private <T> BinaryTreeNode<T> getMinOnSubtree(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> curNode = node;
        while (curNode.getLeft() != null) {
            curNode = curNode.getLeft();
        }

        return curNode;
    }

    private <T> BinaryTreeNode<T> getParentNotRight(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> curNode = node;
        while(curNode.getParent() != null) {
            BinaryTreeNode<T> parent = curNode.getParent();
            if (curNode.equals(parent.right)) {
                curNode = parent;
            } else {
                return parent;
            }
        }

        return null;
    }

    @Override
    public Set<List<BinaryTreeNode<Integer>>> getPathsSummingToNumber(BinaryTreeNode<Integer> root, Integer value) {
        Set<List<BinaryTreeNode<Integer>>> result = new HashSet<>();
        traverseGetPathsSummingToNumber(root, value, result);
        return result;
    }

    private void traverseGetPathsSummingToNumber(BinaryTreeNode<Integer> node, Integer value, Set<List<BinaryTreeNode<Integer>>> result) {
        if (node == null) {
            return;
        }

        result.addAll(visitGetPathsSummingToNumber(node, value));
        traverseGetPathsSummingToNumber(node.left, value, result);
        traverseGetPathsSummingToNumber(node.right, value, result);
    }

    private Set<List<BinaryTreeNode<Integer>>> visitGetPathsSummingToNumber(BinaryTreeNode<Integer> node, Integer value) {
        Set<List<BinaryTreeNode<Integer>>> result = new HashSet<>();

        if (node == null) {
            return result;
        }

        if (node.data.equals(value)) {
            result.add(List.of(node));
        }

        BinaryTreeNode<Integer> curNode = node;
        StraightPathHelper pathHelper = new StraightPathHelper();
        pathHelper.push(node);

        while(curNode.getLeft() != null) {
            curNode = curNode.getLeft();

            pathHelper.push(curNode);
            if (pathHelper.getSum().equals(value)) {
                result.add(pathHelper.getPath());
            }
        }

        curNode = node;
        pathHelper = new StraightPathHelper();
        pathHelper.push(node);

        while(curNode.getRight() != null) {
            curNode = curNode.getRight();
            pathHelper.push(curNode);
            if (pathHelper.getSum().equals(value)) {
                result.add(pathHelper.getPath());
            }
        }

        return result;
    }
}
