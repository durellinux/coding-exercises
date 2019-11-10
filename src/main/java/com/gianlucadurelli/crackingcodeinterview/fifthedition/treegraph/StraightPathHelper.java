package com.gianlucadurelli.crackingcodeinterview.fifthedition.treegraph;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class StraightPathHelper {
    private Deque<BinaryTreeNode<Integer>> path;
    private Integer sum;

    public StraightPathHelper() {
        this.path = new LinkedList<>();
        this.sum = 0;
    }

    public void push(BinaryTreeNode<Integer> value) {
        path.push(value);
        sum += value.data;
    }

    public void pushFront(BinaryTreeNode<Integer> value) {
        path.addFirst(value);
        sum += value.data;
    }

    public BinaryTreeNode<Integer> pop() {
        BinaryTreeNode<Integer> v = path.pop();
        sum -= v.data;

        return v;
    }

    public List<BinaryTreeNode<Integer>> getPath() {
        return new LinkedList<>(path);
    }

    public Integer getSum() {
        return sum;
    }
}
