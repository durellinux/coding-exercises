package com.gianlucadurelli.coding.libraries.graph;

import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class GraphAlgorithms {
    public static <T> Map<String, Set<String>> computeGraphDominators(
            Graph<T> graph) {

        if (graph == null || graph.nodes() == null || graph.nodes().isEmpty()) {
            throw new IllegalArgumentException("Graph cannot be null or empty");
        }

        // Add dummy node and connect it to all nodes without incoming edges
        String dummyNodeId = "dummy";
        Node<T> dummyNode = new Node<>(dummyNodeId, "Dummy Node");
        Map<String, Node<T>> newNodes = new HashMap<>(graph.nodes());
        newNodes.put(dummyNodeId, dummyNode);

        Set<Edge<T>> newEdges = new HashSet<>(graph.edges());
        Set<String> nodesWithIncomingEdges = new HashSet<>();
        // Find nodes with incoming edges
        for (Edge<T> edge : graph.edges()) {
            nodesWithIncomingEdges.add(edge.target().id());
        }
        // Connect dummy node to all nodes without incoming edges
        for (String nodeId : graph.nodes().keySet()) {
            if (!nodesWithIncomingEdges.contains(nodeId)) {
                newEdges.add(new Edge<>(dummyNode, graph.nodes().get(nodeId)));
            }
        }

        Graph<T> augmentedGraph = Graph.from(new ArrayList<>(newNodes.values()), new ArrayList<>(newEdges));

        // Compute dominators starting from the dummy node
        Map<String, Set<String>> dominators = computeGraphDominators(augmentedGraph, dummyNodeId);
        // Remove the dummy node from the result
        dominators.remove(dummyNodeId);
        // Remove self-dominators for all nodes
        for (String nodeId : dominators.keySet()) {
            Set<String> doms = dominators.get(nodeId);
            doms.remove(dummyNodeId);
            dominators.put(nodeId, doms);
        }

        return dominators;
    }

    private static <T> Map<String, Set<String>> computeGraphDominators(
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

        Graph<T> connectedGraph = connectedGraphWithNode(graph, startNodeId);

        // Step 1: Build a map of predecessors for each node
        Map<String, Set<String>> predecessors = buildPredecessorsMap(connectedGraph);

        // Step 2: Initialize dominators
        // - Start node is only dominated by itself
        // - All other nodes are dominated by all nodes (including themselves)
        Map<String, Set<String>> dominators = initializeDominators(connectedGraph, startNodeId);

        // Step 3: Iteratively compute dominators until no changes occur
        boolean changed;
        do {
            changed = false;

            // For each node except the start node
            for (String nodeId : connectedGraph.nodes().keySet()) {
                if (nodeId.equals(startNodeId)) {
                    continue;
                }

                // Get the current dominators for this node
                Set<String> oldDominators = dominators.get(nodeId);

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

        // Add nodes from other connected components
        for (String nodeId : graph.nodes().keySet()) {
            if (!dominators.containsKey(nodeId)) {
                dominators.put(nodeId, Collections.emptySet());
            }
        }

        return dominators;
    }

    public static <T> Map<String, Set<String>> computeConnectedComponents(Graph<T> graph) {
        // For each node we return the set of nodes in the connected component
        Map<String, Set<String>> components = new HashMap<>();
        Set<String> visited = new HashSet<>();
        for (String nodeId : graph.nodes().keySet()) {
            if (!visited.contains(nodeId)) {
                Set<String> component = new HashSet<>();
                exploreComponent(graph, nodeId, visited, component);
                for (String id : component) {
                    components.put(id, component);
                }
            }
        }

        return components;
    }

    public static <T> Graph<T> connectedGraphWithNode(Graph<T> graph, String nodeId) {
        if (graph == null || graph.nodes() == null || graph.nodes().isEmpty()) {
            throw new IllegalArgumentException("Graph cannot be null or empty");
        }

        if (nodeId == null || nodeId.isEmpty()) {
            throw new IllegalArgumentException("Node ID cannot be null or empty");
        }

        if (!graph.nodes().containsKey(nodeId)) {
            throw new IllegalArgumentException("Node ID does not exist in the graph");
        }

        Map<String, Set<String>> connectedComponents = computeConnectedComponents(graph);
        Set<String> component = connectedComponents.get(nodeId);

        // Create a new graph with only the nodes in the connected component
        Map<String, Node<T>> filteredNodes = new HashMap<>();
        Set<Edge<T>> filteredEdges = new HashSet<>();

        for (String id : component) {
            filteredNodes.put(id, graph.nodes().get(id));
        }

        for (Edge<T> edge : graph.edges()) {
            if (component.contains(edge.source().id()) && component.contains(edge.target().id())) {
                filteredEdges.add(edge);
            }
        }

        return Graph.from(new ArrayList<>(filteredNodes.values()), new ArrayList<>(filteredEdges));
    }

    private static <T> void exploreComponent(Graph<T> graph, String nodeId, Set<String> visited, Set<String> component) {
        visited.add(nodeId);
        component.add(nodeId);

        for (Edge<T> edge : graph.edges()) {
            if (edge.source().id().equals(nodeId) && !visited.contains(edge.target().id())) {
                exploreComponent(graph, edge.target().id(), visited, component);
            } else if (edge.target().id().equals(nodeId) && !visited.contains(edge.source().id())) {
                exploreComponent(graph, edge.source().id(), visited, component);
            }
        }
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
