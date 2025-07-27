package com.gianlucadurelli.coding.libraries.graph;

public record Node<T>(String id, String label, T data) {

    public Node(String id, String label) {
        this(id, label, null);
    }

    public Node(String id) {
        this(id, null, null);
    }

    public Node() {
        this(null, null, null);
    }
}
