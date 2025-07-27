package com.gianlucadurelli.coding.libraries.graph;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@UtilityClass
public class GraphAlgorithms {
    public static <T> Map<String, Set<String>> computeGraphDominators(
            Graph<T> graph, String startNodeId) {
        if (graph == null || graph.nodes() == null || graph.nodes().isEmpty()) {
            throw new IllegalArgumentException("Graph cannot be null or empty");
        }

        if (startNodeId == null || startNodeId.isEmpty()) {
            throw new IllegalArgumentException("Start node ID cannot be null or empty");
        }

        if (!graph.nodes().containsKey(startNodeId)) {
            throw new IllegalArgumentException("Start node ID does not exist in the graph");
        }

        // Step 1: Build a map of predecessors for each node
        Map<String, Set<String>> predecessors = buildPredecessorsMap(graph);

        // Step 2: Initialize dominators
        // - Start node is only dominated by itself
        // - All other nodes are dominated by all nodes (including themselves)
        Map<String, Set<String>> dominators = initializeDominators(graph, startNodeId);

        // Step 3: Iteratively compute dominators until no changes occur
        boolean changed;
        do {
            changed = false;

            // For each node except the start node
            for (String nodeId : graph.nodes().keySet()) {
                if (nodeId.equals(startNodeId)) {
                    continue;
                }

                // Get the current dominators for this node
                Set<String> oldDominators = new HashSet<>(dominators.get(nodeId));

                // Compute new dominators as the intersection of dominators of all predecessors
                Set<String> newDominators = null;

                // If the node has predecessors
                if (predecessors.containsKey(nodeId) && !predecessors.get(nodeId).isEmpty()) {
                    // Initialize with the dominators of the first predecessor
                    for (String predId : predecessors.get(nodeId)) {
                        if (newDominators == null) {
                            newDominators = new HashSet<>(dominators.get(predId));
                        } else {
                            // Intersect with dominators of other predecessors
                            newDominators.retainAll(dominators.get(predId));
                        }
                    }

                    // Add the node itself to its dominators
                    newDominators.add(nodeId);

                    // Update dominators if changed
                    if (!newDominators.equals(oldDominators)) {
                        dominators.put(nodeId, newDominators);
                        changed = true;
                    }
                }
            }
        } while (changed);

        return dominators;
    }

    private static <T> Map<String, Set<String>> buildPredecessorsMap(Graph<T> graph) {
        Map<String, Set<String>> predecessors = new HashMap<>();

        // Initialize empty sets for all nodes
        for (String nodeId : graph.nodes().keySet()) {
            predecessors.put(nodeId, new HashSet<>());
        }

        // Populate predecessors based on edges
        for (Edge<T> edge : graph.edges()) {
            String sourceId = edge.source().id();
            String targetId = edge.target().id();

            // Add source as a predecessor of target
            predecessors.get(targetId).add(sourceId);
        }

        return predecessors;
    }

    private static <T> Map<String, Set<String>> initializeDominators(Graph<T> graph, String startNodeId) {
        Map<String, Set<String>> dominators = new HashMap<>();
        Set<String> allNodes = graph.nodes().keySet();

        // For the start node, it's only dominated by itself
        Set<String> startNodeDominators = new HashSet<>();
        startNodeDominators.add(startNodeId);
        dominators.put(startNodeId, startNodeDominators);

        // For all other nodes, initially they are dominated by all nodes
        for (String nodeId : allNodes) {
            if (!nodeId.equals(startNodeId)) {
                dominators.put(nodeId, new HashSet<>(allNodes));
            }
        }

        return dominators;
    }
}
