package com.gianlucadurelli.coding.libraries.graph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphAlgorithmsTest {

    @Nested
    public class Dominators {
        @Test
        public void shouldHandleTree() {
            // Create a simple tree graph
            //      A
            //     / \
            //    B   C
            //   / \   \
            //  D   E   F

            Node<String> root = new Node<>("A", "Root Node", "Data A");
            Node<String> child1 = new Node<>("B", "Child Node 1", "Data B");
            Node<String> child2 = new Node<>("C", "Child Node 2", "Data C");
            Node<String> grandChild1 = new Node<>("D", "Grandchild Node 1", "Data D");
            Node<String> grandChild2 = new Node<>("E", "Grandchild Node 2", "Data E");
            Node<String> grandChild3 = new Node<>("F", "Grandchild Node 3", "Data F");
            Edge<String> edgeAB = new Edge<>(root, child1);
            Edge<String> edgeAC = new Edge<>(root, child2);
            Edge<String> edgeBD = new Edge<>(child1, grandChild1);
            Edge<String> edgeBE = new Edge<>(child1, grandChild2);
            Edge<String> edgeCF = new Edge<>(child2, grandChild3);
            Graph<String> graph = Graph.from(
                List.of(root, child1, child2, grandChild1, grandChild2, grandChild3),
                List.of(edgeAB, edgeAC, edgeBD, edgeBE, edgeCF)
            );

            // Compute dominators
            Map<String, Set<String>> dominators = GraphAlgorithms.computeGraphDominators(graph, "A");
            // Verify dominators
            assertEquals(Set.of("A"), dominators.get("A")); // Root dominates itself
            assertEquals(Set.of("A", "B"), dominators.get("B")); // Child 1 is dominated by Root and itself
            assertEquals(Set.of("A", "C"), dominators.get("C")); // Child 2 is dominated by Root and itself
            assertEquals(Set.of("A", "B", "D"), dominators.get("D")); // Grandchild 1 is dominated by Root, Child 1, and itself
            assertEquals(Set.of("A", "B", "E"), dominators.get("E")); // Grandchild 2 is dominated by Root, Child 1, and itself
            assertEquals(Set.of("A", "C", "F"), dominators.get("F")); // Grandchild 3 is dominated by Root, Grandchild 2, and itself
        }

        @Test
        public void shouldHandleEmptyGraph() {
            // Create an empty graph
            Graph<String> emptyGraph = Graph.from(List.of(), List.of());

            // Compute dominators
            Assertions.assertThrows(IllegalArgumentException.class, () -> GraphAlgorithms.computeGraphDominators(emptyGraph, "A"));
        }

        @Test
        public void shouldHandleSingleNodeGraph() {
            // Create a graph with a single node
            Node<String> singleNode = new Node<>("A", "Single Node", "Data A");
            Graph<String> singleNodeGraph = Graph.from(List.of(singleNode), List.of());

            // Compute dominators
            Map<String, Set<String>> dominators = GraphAlgorithms.computeGraphDominators(singleNodeGraph, "A");

            // Verify that the only dominator is itself
            assertEquals(Set.of("A"), dominators.get("A"));
        }

        @Test
        @Disabled
        public void shouldHandleDisconnectedGraph() {
            // Create a disconnected graph
            //           A    C
            //          /
            //         B
            Node<String> nodeA = new Node<>("A", "Node A", "Data A");
            Node<String> nodeB = new Node<>("B", "Node B", "Data B");
            Node<String> nodeC = new Node<>("C", "Node C", "Data C");
            Edge<String> edgeAB = new Edge<>(nodeA, nodeB);
            Graph<String> disconnectedGraph = Graph.from(
                List.of(nodeA, nodeB, nodeC),
                List.of(edgeAB)
            );

            // Compute dominators
            Map<String, Set<String>> dominators = GraphAlgorithms.computeGraphDominators(disconnectedGraph, "A");

            // Verify dominators
            assertEquals(Set.of("A"), dominators.get("A")); // A dominates itself
            assertEquals(Set.of("A", "B"), dominators.get("B")); // B is dominated by A and itself
            assertEquals(Set.of("C"), dominators.get("C")); // C is isolated and only dominates itself
        }

        @Test
        public void shouldHandleCyclicGraph() {
            // Create a cyclic graph
            //     A
            //    / \
            //   B - C

            Node<String> nodeA = new Node<>("A", "Node A", "Data A");
            Node<String> nodeB = new Node<>("B", "Node B", "Data B");
            Node<String> nodeC = new Node<>("C", "Node C", "Data C");
            Edge<String> edgeAB = new Edge<>(nodeA, nodeB);
            Edge<String> edgeBC = new Edge<>(nodeB, nodeC);
            Edge<String> edgeCA = new Edge<>(nodeC, nodeA); // Cycle A -> B -> C -> A
            Graph<String> cyclicGraph = Graph.from(
                List.of(nodeA, nodeB, nodeC),
                List.of(edgeAB, edgeBC, edgeCA)
            );

            // Compute dominators
            Map<String, Set<String>> dominators = GraphAlgorithms.computeGraphDominators(cyclicGraph, "A");

            // Verify dominators
            assertEquals(Set.of("A"), dominators.get("A")); // A dominates itself
            assertEquals(Set.of("A", "B"), dominators.get("B")); // B is dominated by A and itself
            assertEquals(Set.of("A", "B", "C"), dominators.get("C")); // C is dominated by A, B, and itself
        }

        @Test
        public void shouldHandleDAGs() {
            // Create a DAG for testing
            // Graph structure:
            //     A
            //    / \
            //   B   C
            //  / \ /
            // D   E
            //    /
            //   F

            // Create nodes
            Node<String> nodeA = new Node<>("A", "Node A", "Data A");
            Node<String> nodeB = new Node<>("B", "Node B", "Data B");
            Node<String> nodeC = new Node<>("C", "Node C", "Data C");
            Node<String> nodeD = new Node<>("D", "Node D", "Data D");
            Node<String> nodeE = new Node<>("E", "Node E", "Data E");
            Node<String> nodeF = new Node<>("F", "Node F", "Data F");

            // Create edges
            Edge<String> edgeAB = new Edge<>(nodeA, nodeB);
            Edge<String> edgeAC = new Edge<>(nodeA, nodeC);
            Edge<String> edgeBD = new Edge<>(nodeB, nodeD);
            Edge<String> edgeBE = new Edge<>(nodeB, nodeE);
            Edge<String> edgeCE = new Edge<>(nodeC, nodeE);
            Edge<String> edgeEF = new Edge<>(nodeE, nodeF);

            // Create graph
            Graph<String> graph = Graph.from(
                    List.of(nodeA, nodeB, nodeC, nodeD, nodeE, nodeF),
                    List.of(edgeAB, edgeAC, edgeBD, edgeBE, edgeCE, edgeEF)
            );

            // Compute dominators
            Map<String, Set<String>> dominators = GraphAlgorithms.computeGraphDominators(graph, "A");

            // Verify dominators
            // A is dominated by A
            assertEquals(Set.of("A"), dominators.get("A"));

            // B is dominated by A and B
            assertEquals(Set.of("A", "B"), dominators.get("B"));

            // C is dominated by A and C
            assertEquals(Set.of("A", "C"), dominators.get("C"));

            // D is dominated by A, B, and D
            assertEquals(Set.of("A", "B", "D"), dominators.get("D"));

            // E is dominated by A and E (A dominates E because all paths to E go through A)
            assertEquals(Set.of("A", "E"), dominators.get("E"));

            // F is dominated by A, E, and F
            assertEquals(Set.of("A", "E", "F"), dominators.get("F"));
        }

        @Test
        public void testComputeGraphDominatorsWithInvalidInput() {
            // Test with null graph
            assertThrows(IllegalArgumentException.class, () -> {
                GraphAlgorithms.computeGraphDominators(null, "A");
            });

            // Create a valid graph for testing other invalid inputs
            Node<String> nodeA = new Node<>("A");
            Graph<String> graph = Graph.from(List.of(nodeA), List.of());

            // Test with null start node ID
            assertThrows(IllegalArgumentException.class, () -> {
                GraphAlgorithms.computeGraphDominators(graph, null);
            });

            // Test with empty start node ID
            assertThrows(IllegalArgumentException.class, () -> {
                GraphAlgorithms.computeGraphDominators(graph, "");
            });

            // Test with non-existent start node ID
            assertThrows(IllegalArgumentException.class, () -> {
                GraphAlgorithms.computeGraphDominators(graph, "Z");
            });
        }
    }
}