package com.gianlucadurelli.crackingcodeinterview.fifthedition.tree;

class IsBalancedHelper {
    private boolean isBalanced;
    private int height;

    public IsBalancedHelper(boolean isBalanced, int height) {
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
