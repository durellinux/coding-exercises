package com.gianlucadurelli.coding.adventofcode.year2023;

import org.jgrapht.Graph;
import org.jgrapht.alg.clustering.KSpanningTreeClustering;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.alg.interfaces.ClusteringAlgorithm;
import org.jgrapht.alg.interfaces.PartitioningAlgorithm;
import org.jgrapht.alg.partition.BipartitePartitioning;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

public class Day25Snowverload {

    public long solve(List<String> input) {
        Graph<String, DefaultWeightedEdge> graph = parseInput(input);
        EdmondsKarpMFImpl<String, DefaultWeightedEdge> edmondsKarpMF = new EdmondsKarpMFImpl<>(graph);
        for (String source: graph.vertexSet()) {
            for (String dst: graph.vertexSet()) {
                if (!source.equals(dst)) {
                    double cutCost = edmondsKarpMF.calculateMinCut(source, dst);
                    if (cutCost == 3) {
                        return (long) edmondsKarpMF.getSourcePartition().size() * edmondsKarpMF.getSinkPartition().size();
                    }
                }
            }
        }
        return 0;
    }

    private Graph<String, DefaultWeightedEdge> parseInput(List<String> input) {
        Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        for (String line: input) {
            String[] data = line.split(": ");
            String src = data[0];
            String[] dsts = data[1].split(" ");

            graph.addVertex(src);
            for (String dst: dsts) {
                graph.addVertex(dst);
                graph.addEdge(src, dst);
            }
        }

        return graph;
    }

}
