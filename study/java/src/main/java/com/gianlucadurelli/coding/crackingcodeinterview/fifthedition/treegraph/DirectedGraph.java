package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.treegraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DirectedGraph<T> {
    private Set<GraphNode<T>> nodes;
    private Map<GraphNode<T>, Set<GraphNode<T>>> edges;

    public DirectedGraph() {
        this.nodes = new HashSet<>();
        this.edges = new HashMap<>();
    }

    public void addNode(GraphNode<T> node) {
        nodes.add(node);
        edges.put(node, new HashSet<>());
    }

    public void addEdge(GraphNode<T> src, GraphNode<T> dst) {
        Set<GraphNode<T>> curEdges = edges.get(src);
        curEdges.add(dst);
    }

    public Set<GraphNode<T>> getEdgesForNode(GraphNode<T> node) {
        return edges.get(node);
    }

    public Set<GraphNode<T>> getNodes() {
        return nodes;
    }
}
