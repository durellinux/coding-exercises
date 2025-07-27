package com.gianlucadurelli.coding.libraries.graph;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

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
            assertThat(dominators.get("A")).isEqualTo(Set.of("A")); // Root dominates itself
            assertThat(dominators.get("B")).isEqualTo(Set.of("A", "B")); // Child 1 is dominated by Root and itself
            assertThat(dominators.get("C")).isEqualTo(Set.of("A", "C")); // Child 2 is dominated by Root and itself
            assertThat(dominators.get("D")).isEqualTo(Set.of("A", "B", "D")); // Grandchild 1 is dominated by Root, Child 1, and itself
            assertThat(dominators.get("E")).isEqualTo(Set.of("A", "B", "E")); // Grandchild 2 is dominated by Root, Child 1, and itself
            assertThat(dominators.get("F")).isEqualTo(Set.of("A", "C", "F")); // Grandchild 3 is dominated by Root, Grandchild 2, and itself
        }

        @Test
        public void shouldHandleEmptyGraph() {
            // Create an empty graph
            Graph<String> emptyGraph = Graph.from(List.of(), List.of());

            // Compute dominators
            assertThatThrownBy(() -> GraphAlgorithms.computeGraphDominators(emptyGraph, "A"))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        public void shouldHandleSingleNodeGraph() {
            // Create a graph with a single node
            Node<String> singleNode = new Node<>("A", "Single Node", "Data A");
            Graph<String> singleNodeGraph = Graph.from(List.of(singleNode), List.of());

            // Compute dominators
            Map<String, Set<String>> dominators = GraphAlgorithms.computeGraphDominators(singleNodeGraph, "A");

            // Verify that the only dominator is itself
            assertThat(dominators.get("A")).isEqualTo(Set.of("A"));
        }

        @Test
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
            assertThat(dominators.get("A")).isEqualTo(Set.of("A")); // A dominates itself
            assertThat(dominators.get("B")).isEqualTo(Set.of("A", "B")); // B is dominated by A and itself
            assertThat(dominators.get("C")).isEqualTo(Set.of("C")); // C is isolated and only dominates itself
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
            assertThat(dominators.get("A")).isEqualTo(Set.of("A")); // A dominates itself
            assertThat(dominators.get("B")).isEqualTo(Set.of("A", "B")); // B is dominated by A and itself
            assertThat(dominators.get("C")).isEqualTo(Set.of("A", "B", "C")); // C is dominated by A, B, and itself
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
            assertThat(dominators.get("A")).isEqualTo(Set.of("A"));

            // B is dominated by A and B
            assertThat(dominators.get("B")).isEqualTo(Set.of("A", "B"));

            // C is dominated by A and C
            assertThat(dominators.get("C")).isEqualTo(Set.of("A", "C"));

            // D is dominated by A, B, and D
            assertThat(dominators.get("D")).isEqualTo(Set.of("A", "B", "D"));

            // E is dominated by A and E (A dominates E because all paths to E go through A)
            assertThat(dominators.get("E")).isEqualTo(Set.of("A", "E"));

            // F is dominated by A, E, and F
            assertThat(dominators.get("F")).isEqualTo(Set.of("A", "E", "F"));
        }

        @Test
        public void shouldWorkForNonReducibleCFG() {
            // Create a non-reducible control flow graph (CFG)
            // Graph structure:
            //     A
            //    / \
            //   B - C
            Node<String> nodeA = new Node<>("A", "Node A", "Data A");
            Node<String> nodeB = new Node<>("B", "Node B", "Data B");
            Node<String> nodeC = new Node<>("C", "Node C", "Data C");

            Edge<String> edgeAB = new Edge<>(nodeA, nodeB);
            Edge<String> edgeAC = new Edge<>(nodeA, nodeC);
            Edge<String> edgeBC = new Edge<>(nodeB, nodeC);
            Edge<String> edgeCB = new Edge<>(nodeC, nodeB);

            Graph<String> graph = Graph.from(
                    List.of(nodeA, nodeB, nodeC),
                    List.of(edgeAB, edgeAC, edgeBC, edgeCB)
            );

            // Compute dominators
            Map<String, Set<String>> dominators = GraphAlgorithms.computeGraphDominators(graph, "A");

            // Verify dominators
            assertThat(dominators.get("A")).isEqualTo(Set.of("A")); // A dominates itself
            assertThat(dominators.get("B")).isEqualTo(Set.of("A", "B")); // B is dominated by A and itself
            assertThat(dominators.get("C")).isEqualTo(Set.of("A", "C")); // C is dominated by A and itself
        }

        @Test
        public void testComputeGraphDominatorsWithInvalidInput() {
            // Test with null graph
            assertThatThrownBy(() -> {
                GraphAlgorithms.computeGraphDominators(null, "A");
            }).isInstanceOf(IllegalArgumentException.class);

            // Create a valid graph for testing other invalid inputs
            Node<String> nodeA = new Node<>("A");
            Graph<String> graph = Graph.from(List.of(nodeA), List.of());

            // Test with null start node ID
            assertThatThrownBy(() -> {
                GraphAlgorithms.computeGraphDominators(graph, null);
            }).isInstanceOf(IllegalArgumentException.class);

            // Test with empty start node ID
            assertThatThrownBy(() -> {
                GraphAlgorithms.computeGraphDominators(graph, "");
            }).isInstanceOf(IllegalArgumentException.class);

            // Test with non-existent start node ID
            assertThatThrownBy(() -> {
                GraphAlgorithms.computeGraphDominators(graph, "Z");
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        public void shouldWorkWithLargeGraph() {
            // Create a large graph with many nodes and edges
            int nodesCount = 1000;
            Node<String>[] nodes = new Node[nodesCount];
            for (int i = 0; i < nodesCount; i++) {
                nodes[i] = new Node<>(String.valueOf(i), "Node " + i, "Data " + i);
            }

            // Create edges in a way that forms a tree structure
            List<Edge<String>> edges = new java.util.ArrayList<>();
            for (int i = 1; i < nodesCount; i++) {
                // Connect each node to its parent node
                int parentIndex = (i - 1) / 2; // Simple binary tree structure
                edges.add(new Edge<>(nodes[parentIndex], nodes[i]));
            }

            // Add a few additional edges to create some complexity
            edges.add(new Edge<>(nodes[0], nodes[500])); // Connect root to a middle node
            edges.add(new Edge<>(nodes[250], nodes[750])); // Connect a middle node to another middle node
            edges.add(new Edge<>(nodes[999], nodes[10])); // Connect last node back to something above
            // Run 100 more edges to increase complexity
            for (int i = 0; i < 100; i++) {
                int sourceIndex = (int) (Math.random() * nodesCount);
                int targetIndex = (int) (Math.random() * nodesCount);
                if (sourceIndex != targetIndex) {
                    edges.add(new Edge<>(nodes[sourceIndex], nodes[targetIndex]));
                }
            }

            Graph<String> largeGraph = Graph.from(List.of(nodes), edges);
            // Compute dominators and check it terminates in a less than 1s
            int startTime = (int) System.currentTimeMillis();
            Map<String, Set<String>> dominators = GraphAlgorithms.computeGraphDominators(largeGraph, "0");
            int endTime = (int) System.currentTimeMillis();


            int totalTime = endTime - startTime;
            assertThat(totalTime < 1000).withFailMessage("Dominators computation took too long: " + totalTime + "ms").isTrue();
        }
    }
}
