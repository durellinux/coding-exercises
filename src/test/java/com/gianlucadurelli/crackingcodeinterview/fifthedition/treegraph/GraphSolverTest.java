package com.gianlucadurelli.crackingcodeinterview.fifthedition.treegraph;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class GraphSolverTest {

    GraphSolver solver = new GraphSolver();

    @Test
    public void doesRouteExists() {
        DirectedGraph<Integer> graph = new DirectedGraph<>();

        GraphNode<Integer> n1 = new GraphNode<>(1);
        GraphNode<Integer> n2 = new GraphNode<>(2);
        GraphNode<Integer> n3 = new GraphNode<>(3);
        GraphNode<Integer> n4 = new GraphNode<>(4);
        GraphNode<Integer> n5 = new GraphNode<>(5);
        GraphNode<Integer> n6 = new GraphNode<>(6);
        GraphNode<Integer> n7 = new GraphNode<>(7);
        GraphNode<Integer> n8 = new GraphNode<>(8);

        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.addNode(n5);
        graph.addNode(n6);
        graph.addNode(n7);
        graph.addNode(n8);

        graph.addEdge(n1, n7);
        graph.addEdge(n7, n3);
        graph.addEdge(n3, n2);
        graph.addEdge(n3, n4);
        graph.addEdge(n3, n5);
        graph.addEdge(n2, n1);
        graph.addEdge(n4, n6);
        graph.addEdge(n6, n7);
        graph.addEdge(n8, n7);

        Assertions.assertThat(solver.doesRouteExists(graph, n1, n2)).isTrue();
        Assertions.assertThat(solver.doesRouteExists(graph, n6, n8)).isFalse();
    }
}