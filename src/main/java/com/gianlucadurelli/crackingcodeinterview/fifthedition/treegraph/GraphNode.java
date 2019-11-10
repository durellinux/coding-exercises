package com.gianlucadurelli.crackingcodeinterview.fifthedition.treegraph;

public class GraphNode<T> {
    private T value;

    public GraphNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
