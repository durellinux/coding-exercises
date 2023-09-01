package com.gianlucadurelli.coding.leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class Candy {
    public int candy(int[] ratings) {
        Map<Integer, Integer> incomingEdges = new HashMap<>();
        Map<Integer, List<Integer>> outgoingEdges = new HashMap<>();

        for(int i = 0; i < ratings.length; i++) {
            incomingEdges.put(i, 0);
            outgoingEdges.put(i, new ArrayList<>());
        }

        for(int i = 0; i < ratings.length-1; i++) {
            if (ratings[i] < ratings[i + 1]) {
                incomingEdges.put(i+1, incomingEdges.getOrDefault(i+1, 0) + 1);
                outgoingEdges.get(i).add(i+1);
            } else if (ratings[i+1] < ratings[i]) {
                incomingEdges.put(i, incomingEdges.getOrDefault(i, 0) + 1);
                outgoingEdges.get(i+1).add(i);
            }
        }

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> toVisit = new LinkedList<>();
        for (Map.Entry<Integer, Integer> value: incomingEdges.entrySet()) {
            if (value.getValue() == 0) {
                toVisit.add(value.getKey());
                visited.add(value.getKey());
            }
        }


        int[] result = new int[ratings.length];
        int totalCandies = 0;
        int candies = 1;
        while(!toVisit.isEmpty()) {
            List<Integer> levelNodes = new ArrayList<>();
            while(!toVisit.isEmpty()) {
                int visitedNode = toVisit.remove();
                result[visitedNode] = candies;
                levelNodes.add(visitedNode);
            }

            totalCandies += candies * levelNodes.size();

            for(Integer node: levelNodes) {
                for(Integer sibling: outgoingEdges.get(node)) {
                    if (!visited.contains(sibling)) {
                        incomingEdges.put(sibling, incomingEdges.get(sibling) - 1);
                        if (incomingEdges.get(sibling) == 0) {
                            visited.add(sibling);
                            toVisit.add(sibling);
                        }
                    }
                }
            }

            candies++;
        }

        return totalCandies;
    }
}
