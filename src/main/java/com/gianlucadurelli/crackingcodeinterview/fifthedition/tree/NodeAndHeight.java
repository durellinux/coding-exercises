package com.gianlucadurelli.crackingcodeinterview.fifthedition.tree;

class NodeAndHeight<T> {
    private BinaryTreeNode<T> node;
    private int height;

    public NodeAndHeight(BinaryTreeNode<T> node, int height) {
        this.node = node;
        this.height = height;
    }

    public BinaryTreeNode<T> getNode() {
        return node;
    }

    public int getHeight() {
        return height;
    }
}
