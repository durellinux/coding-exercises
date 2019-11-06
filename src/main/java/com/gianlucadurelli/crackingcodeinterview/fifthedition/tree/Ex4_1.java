package com.gianlucadurelli.crackingcodeinterview.fifthedition.tree;

public class Ex4_1<T> {

    public static class ResultHelper {
        private boolean isBalanced;
        private int height;

        public ResultHelper(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }

        public boolean isBalanced() {
            return isBalanced;
        }

        public int getHeight() {
            return height;
        }
    }

    public boolean isBalanced(BinaryTreeNode<T> root) {
        ResultHelper result = isBalancedInner(root);
        return result.isBalanced;
    }

    private ResultHelper isBalancedInner(BinaryTreeNode<T> root) {
        if (root == null) {
            return new ResultHelper(true, 0);
        }

        ResultHelper leftResult = isBalancedInner(root.getLeft());
        ResultHelper rightResult = isBalancedInner(root.getRight());

        int nextHeight = Math.max(leftResult.height, rightResult.height) + 1;
        boolean isBalanced = Math.abs(leftResult.height - rightResult.height) <= 1 && leftResult.isBalanced && rightResult.isBalanced;
        return new ResultHelper(isBalanced, nextHeight);
    }
}
