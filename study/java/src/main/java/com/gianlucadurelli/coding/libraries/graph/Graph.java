package com.gianlucadurelli.coding.libraries.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record Graph<T>(Map<String, Node<T>> nodes, Set<Edge<T>> edges) {
    public static <T> Graph<T> from(List<Node<T>> nodes, List<Edge<T>> edges) {
        if (nodes == null || edges == null) {
            throw new IllegalArgumentException("Nodes and edges cannot be null");
        }
        Map<String, Node<T>> nodeMap = nodes.stream()
                .collect(Collectors.toMap(Node::id, node -> node));
        Set<Edge<T>> edgeSet = new HashSet<>(edges);
        return new Graph<>(nodeMap, edgeSet);
    }
}
