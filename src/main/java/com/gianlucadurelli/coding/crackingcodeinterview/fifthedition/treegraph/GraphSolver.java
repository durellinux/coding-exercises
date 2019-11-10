package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.treegraph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class GraphSolver {

    public <T> boolean doesRouteExists(DirectedGraph<T> graph, GraphNode<T> src, GraphNode<T> dst) {
        if (src == null || dst == null) {
            return false;
        }

        Set<GraphNode<T>> visitedNodes = new HashSet<>();
        Queue<GraphNode<T>> nodesToVisit = new LinkedList<>();
        nodesToVisit.add(src);

        while (!nodesToVisit.isEmpty()) {
            GraphNode<T> currentVisited = nodesToVisit.remove();
            if (!visitedNodes.contains(currentVisited)) {
                visitedNodes.add(currentVisited);

                if (currentVisited == dst) {
                    return true;
                }

                nodesToVisit.addAll(graph.getEdgesForNode(currentVisited));
            }
        }

        return false;
    }
}
