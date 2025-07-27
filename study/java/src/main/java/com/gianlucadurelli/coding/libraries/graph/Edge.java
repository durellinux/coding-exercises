package com.gianlucadurelli.coding.libraries.graph;

public record Edge<T>(Node<T> source, Node<T> target) {
    public Edge(Node<T> source, Node<T> target) {
        this.source = source;
        this.target = target;
    }
}
