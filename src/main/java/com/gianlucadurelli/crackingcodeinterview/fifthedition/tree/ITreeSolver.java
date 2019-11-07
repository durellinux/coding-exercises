package com.gianlucadurelli.crackingcodeinterview.fifthedition.tree;


import java.util.List;

public interface ITreeSolver<T> {


    boolean isBalanced(BinaryTreeNode<T> root);

    BinaryTreeNode buildBinarySearchTree(T[] sortedValues);

    List<List<BinaryTreeNode<T>>> getListOfNodesPerLevel(BinaryTreeNode<T> node);
}
