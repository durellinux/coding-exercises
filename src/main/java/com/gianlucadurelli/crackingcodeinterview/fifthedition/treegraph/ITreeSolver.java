package com.gianlucadurelli.crackingcodeinterview.fifthedition.treegraph;


import java.util.List;
import java.util.Set;

public interface ITreeSolver {
    <T> boolean isBalanced(BinaryTreeNode<T> root);
    <T> BinaryTreeNode<T> buildBinarySearchTree(T[] sortedValues);
    <T> List<List<BinaryTreeNode<T>>> getListOfNodesPerLevel(BinaryTreeNode<T> node);
    <T> BinaryTreeNode<T> nextBinarySearchTreeNode(BinaryTreeNode<T> node);
    Set<List<BinaryTreeNode<Integer>>> getPathsSummingToNumber(BinaryTreeNode<Integer> root, Integer value);
}
