package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Day8HauntedWasteland {

    public int solve1(List<String> input) {
        String commandsString = input.get(0);
        List<String> map = input.subList(2, input.size());

        Map<String, List<String>> graph = buildGraph(map);
        char[] commands = commandsString.toCharArray();
        return solveOne("AAA", commands, graph, (String node) -> node.equals("ZZZ"));
    }

    public long solve2(List<String> input) {
        String commandsString = input.get(0);
        List<String> map = input.subList(2, input.size());

        Map<String, List<String>> graph = buildGraph(map);
        char[] commands = commandsString.toCharArray();

        List<String> currentNodes = new ArrayList<>(graph.keySet().stream()
                .filter(n -> n.endsWith("A"))
                .toList());

        List<Long> solutions = currentNodes.stream().map(n -> solveOne(n, commands, graph, (String node) -> node.endsWith("Z"))).map(Long::valueOf).toList();

        Long count = solutions.get(0);

        for(int i = 1; i < solutions.size(); i++) {
            count = lcm(count, solutions.get(i));
        }

        return count;
    }

    private int solveOne(String startNode, char[] commands, Map<String, List<String>> graph, Predicate<String> reachedDestination) {
        String currentNode = startNode;
        int commandCount = 0;
        while(!reachedDestination.test(currentNode)) {
            char command = commands[commandCount % commands.length];

            if (command == 'L') {
                currentNode = graph.get(currentNode).get(0);
            } else {
                currentNode = graph.get(currentNode).get(1);
            }

            commandCount++;
        }

        return commandCount;
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    private long gcd(long a, long b) {
        long t;
        while (b != 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    private Map<String, List<String>> buildGraph(List<String> map) {
        Map<String, List<String>> graph = new HashMap<>();

        for (String val: map) {
            String[] data = val.split(" = ");
            String node = data[0];
            String[] connections = data[1].split(", ");
            String left = connections[0].substring(1);
            String right = connections[1].substring(0, connections[1].length() - 1);
            graph.put(node, List.of(left, right));
        }

        return graph;
    }
}
