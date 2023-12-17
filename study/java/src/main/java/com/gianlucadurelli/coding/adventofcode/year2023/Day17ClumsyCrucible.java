package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;

public class Day17ClumsyCrucible {
    private static final Map<Character, Character> DIRECTION_EXCLUSION = Map.of(
            'N', 'S',
            'S', 'N',
            'E', 'W',
            'W', 'E'
    );

    public record CrucibleConfig(int minStraight, int maxStraight) {
        public static CrucibleConfig normalCrucible() {
            return new CrucibleConfig(0, 3);
        }

        public static CrucibleConfig ultraCrucible() {
            return new CrucibleConfig(4, 10);
        }
    }

    private record Point(int row, int col, int value) {}
    private record Node(Point point, char direction, int straight) {}

    private record PathStep(Node node, int distance) {}

    public int solve(List<String> input, CrucibleConfig crucibleConfig) {
        int R = input.size();
        int C = input.get(0).length();
        int[][] matrix = parseInput(input, R, C);

        return dijkstra(matrix, pointOf(0, 0, matrix), pointOf(R - 1, C - 1, matrix), crucibleConfig);
    }

    private int dijkstra(int[][] matrix, Point start, Point end, CrucibleConfig crucibleConfig) {
        PriorityQueue<PathStep> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));
        Map<Node, Integer> distances = new HashMap<>();

        Node initialEast = new Node(start, 'E', 0);
        priorityQueue.add(new PathStep(initialEast, 0));
        distances.put(initialEast, 0);

        Node initialSouth = new Node(start, 'S', 0);
        priorityQueue.add(new PathStep(initialSouth, 0));
        distances.put(initialSouth, 0);

        while(!priorityQueue.isEmpty()) {
            PathStep step = priorityQueue.poll();

            if (step.node.point.equals(end)) {
                return step.distance;
            }

            List<Node> adjacentNodes = getNeighbors(step, matrix, crucibleConfig);

            for (Node nextNode: adjacentNodes) {
                int distCurrent = distances.get(step.node);
                int distNext = distances.getOrDefault(nextNode, Integer.MAX_VALUE);
                int weight = nextNode.point.value;
                if (distCurrent + weight < distNext) {
                    int newDistance = distCurrent + weight;
                    distances.put(nextNode, newDistance);
                    priorityQueue.add(new PathStep(nextNode, newDistance));
                }
            }
        }

        return -1;
    }

    private List<Node> getNeighbors(PathStep step, int[][] matrix, CrucibleConfig crucibleConfig) {
        int curR = step.node.point.row;
        int curC = step.node.point.col;
        int R = matrix.length;
        int C = matrix[0].length;
        Node curNode = step.node;
        Optional<Point> east = getPoint(curR, curC + 1, matrix);
        Optional<Point> west = getPoint(curR, curC - 1, matrix);
        Optional<Point> north = getPoint(curR - 1, curC, matrix);
        Optional<Point> south = getPoint(curR + 1, curC, matrix);

        List<Node> adjacentNodes = new ArrayList<>();
        getNeighborNode(east, curNode, 'E', crucibleConfig, R, C).ifPresent(adjacentNodes::add);
        getNeighborNode(west, curNode, 'W', crucibleConfig, R, C).ifPresent(adjacentNodes::add);
        getNeighborNode(north, curNode, 'N', crucibleConfig, R, C).ifPresent(adjacentNodes::add);
        getNeighborNode(south, curNode, 'S', crucibleConfig, R, C).ifPresent(adjacentNodes::add);

        return adjacentNodes;
    }

    private Optional<Node> getNeighborNode(Optional<Point> neighborPoint, Node currentNode, char direction, CrucibleConfig crucibleConfig, int R, int C) {
        char curDirection = currentNode.direction;
        int curStraight = currentNode.straight;

        int distanceFromBorder = 0;
        switch (direction) {
            case 'E' -> distanceFromBorder = C - 1 - currentNode.point.col;
            case 'W' -> distanceFromBorder = currentNode.point.col;
            case 'N' -> distanceFromBorder = currentNode.point.row;
            case 'S' -> distanceFromBorder = R - 1 - currentNode.point.row;
        }

        if (neighborPoint.isPresent()) {
            Point point = neighborPoint.get();

            if (curDirection == direction && curStraight < crucibleConfig.maxStraight) {
                return Optional.of(new Node(point, direction, curStraight + 1));
            }

            if (curDirection != direction && DIRECTION_EXCLUSION.get(curDirection) != direction && distanceFromBorder >= crucibleConfig.minStraight && curStraight >= crucibleConfig.minStraight) {
                return Optional.of(new Node(point, direction, 1));
            }
        }

        return Optional.empty();
    }

    private int[][] parseInput(List<String> input, int R, int C) {
        int [][] matrix = new int[R][C];

        for (int r = 0; r < R; r++) {
            char[] line = input.get(r).toCharArray();
            for (int c = 0; c < C; c++) {
                matrix[r][c] = line[c] - '0';
            }
        }

        return matrix;
    }

    Optional<Point> getPoint(int row, int col, int[][] matrix) {
        int R = matrix.length;
        int C = matrix[0].length;
        if ( row < 0 || row >= R || col < 0 || col >= C ) {
            return Optional.empty();
        }

        return Optional.of(pointOf(row, col, matrix));
    }

    private Point pointOf(int row, int col, int[][] matrix) {
        return new Point(row, col, matrix[row][col]);
    }
}
