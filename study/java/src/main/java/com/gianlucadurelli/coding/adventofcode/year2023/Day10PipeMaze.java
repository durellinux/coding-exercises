package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;

public class Day10PipeMaze {

    private record Point(int x, int y) {}
    private record BFSStep(Point point, int length) {}

    private final Set<String> northConnections = Set.of("S", "|", "L", "J");
    private final Set<String> southConnections = Set.of("S", "|", "7", "F");
    private final Set<String> westConnections = Set.of("S", "-", "J", "7");
    private final Set<String> eastConnections = Set.of("S", "-", "F", "L");

    public int solve1(List<String> input) {
        Map<Point, Set<Point>> graph = new HashMap<>();
        List<List<String>> matrix = new ArrayList<>();
        Point startingPoint = parseInput(input, graph, matrix);

        List<Point> spConnections = graph.get(startingPoint).stream().toList();
        graph.get(spConnections.get(0)).remove(startingPoint);
        graph.get(startingPoint).remove(spConnections.get(1));

        return detectLoop(startingPoint, graph).size() / 2;
    }

    // startReplace is the pipe for starting point, hardcoded by looking at input
    public int solve2(List<String> input, String startReplace) {
        Map<Point, Set<Point>> graph = new HashMap<>();
        List<List<String>> matrix = new ArrayList<>();
        Point startingPoint = parseInput(input, graph, matrix);

        List<Point> spConnections = graph.get(startingPoint).stream().toList();
        graph.get(spConnections.get(0)).remove(startingPoint);
        graph.get(startingPoint).remove(spConnections.get(1));

        List<Point> loop = detectLoop(startingPoint, graph);
        Set<Point> loopPoints = new HashSet<>(loop);

        for (int r = 0; r < matrix.size(); r++) {
            for (int c = 0; c < matrix.get(0).size(); c++) {
                Point p = new Point(r, c);
                if (!loopPoints.contains(p)) {
                    matrix.get(r).set(c, ".");
                }
            }
        }

        matrix.get(startingPoint.x).set(startingPoint.y, startReplace);

        Set<String> horizontalConnections = new HashSet<>(eastConnections);
        horizontalConnections.addAll(westConnections);

        Set<String> verticalConnections = new HashSet<>(northConnections);
        verticalConnections.addAll(southConnections);

        matrix = expandMatrix(matrix, horizontalConnections, Set.of("|", "7", "F"), "|");
        matrix = transpose(matrix);
        matrix = expandMatrix(matrix, verticalConnections, Set.of("-", "F", "L"), "-");
        matrix = transpose(matrix);

        List<List<String>> finalMatrix = matrix;

        Set<Point> groundPoints = new HashSet<>();
        for (int r = 0; r < matrix.size(); r++) {
            for (int c = 0; c < matrix.get(0).size(); c++) {
                if (matrix.get(r).get(c).equals(".")) {
                    groundPoints.add(new Point(r, c));
                }
            }
        }

        int nestSize = 0;
        Set<Point> visitedGround = new HashSet<>();
        for (Point p: groundPoints) {
            if (!visitedGround.contains(p)) {
                Set<Point> connectedGround = new HashSet<>();
                boolean isInsideLoop = detectConnectedGround(p, connectedGround, finalMatrix);
                if (isInsideLoop) {
                    nestSize += connectedGround.size();
                    connectedGround.forEach(g -> finalMatrix.get(g.x).set(g.y, "I"));
                } else {
                    connectedGround.forEach(g -> finalMatrix.get(g.x).set(g.y, "O"));
                }

                visitedGround.addAll(connectedGround);
            }
        }

        return nestSize;
    }

    private List<List<String>> expandMatrix(List<List<String>> matrix, Set<String> horizontalsTokens, Set<String> verticalTokens, String filler) {
        List<List<String>> expanded = new ArrayList<>();

        for (int r = 0; r < matrix.size() - 1; r++) {
            boolean shouldExpand = false;
            for (int c = 0; c < matrix.get(0).size(); c++) {
                String top = matrix.get(r).get(c);
                String bottom = matrix.get(r + 1).get(c);

                if (horizontalsTokens.contains(top) && horizontalsTokens.contains(bottom)) {
                    shouldExpand = true;
                    break;
                }
            }

            List<String> copiedLine = new ArrayList<>(matrix.get(r));
            expanded.add(copiedLine);

            if (shouldExpand) {
                List<String> expandedLine = new ArrayList<>();
                for (int c = 0; c < matrix.get(0).size(); c++) {
                    String value = matrix.get(r).get(c);
                    if (verticalTokens.contains(value)) {
                        expandedLine.add(filler);
                    } else {
                        expandedLine.add(" ");
                    }
                }
                expanded.add(expandedLine);
            }
        }
        expanded.add(new ArrayList<>(matrix.get(matrix.size() - 1)));

        return expanded;
    }

    private List<List<String>> transpose(List<List<String>> matrix) {
        List<List<String>> transposed = new ArrayList<>();
        for (int c = 0; c < matrix.get(0).size(); c++) {
            List<String> row = new ArrayList<>();
            for (List<String> strings : matrix) {
                row.add(strings.get(c));
            }
            transposed.add(row);
        }

        return transposed;
    }

    private void printMatrix(List<List<String>> matrix) {
        for(List<String> line: matrix) {
            System.out.println(line);
        }

        System.out.println();
        System.out.println();
    }

    private boolean detectConnectedGround(Point startingPoint, Set<Point> connectedGround, List<List<String>> matrix) {
        boolean isInLoop = true;

        Set<Point> visitedPoints = new HashSet<>();
        Deque<Point> toVisit = new LinkedList<>();

        visitedPoints.add(startingPoint);
        toVisit.add(startingPoint);
        connectedGround.add(startingPoint);

        Set<String> toExplore = Set.of(".", " ");

        while(!toVisit.isEmpty()) {
            Point p = toVisit.pop();

            String val = matrix.get(p.x).get(p.y);
            if (val.equals(".")) {
                connectedGround.add(p);
            }

            Optional<String> east = getValue(p.x, p.y + 1, matrix);
            Optional<String> north = getValue(p.x - 1, p.y, matrix);
            Optional<String> west = getValue(p.x, p.y - 1, matrix);
            Optional<String> south = getValue(p.x + 1, p.y, matrix);

            if (east.isEmpty() || north.isEmpty() || west.isEmpty() || south.isEmpty()) {
                isInLoop = false;
            }

            if (east.isPresent() && toExplore.contains(east.get())) {
                Point newPoint = new Point(p.x, p.y + 1);
                if (!visitedPoints.contains(newPoint)) {
                    visitedPoints.add(newPoint);
                    toVisit.add(newPoint);
                }
            }

            if (west.isPresent() && toExplore.contains(west.get())) {
                Point newPoint = new Point(p.x, p.y - 1);
                if (!visitedPoints.contains(newPoint)) {
                    visitedPoints.add(newPoint);
                    toVisit.add(newPoint);
                }
            }

            if (north.isPresent() && toExplore.contains(north.get())) {
                Point newPoint = new Point(p.x - 1, p.y);
                if (!visitedPoints.contains(newPoint)) {
                    visitedPoints.add(newPoint);
                    toVisit.add(newPoint);
                }
            }

            if (south.isPresent() && toExplore.contains(south.get())) {
                Point newPoint = new Point(p.x + 1, p.y);
                if (!visitedPoints.contains(newPoint)) {
                    visitedPoints.add(newPoint);
                    toVisit.add(newPoint);
                }
            }
        }

        return isInLoop;
    }

    private List<Point> detectLoop(Point startingPoint, Map<Point, Set<Point>> graph) {
        Set<Point> visited = new HashSet<>();
        Deque<BFSStep> toVisit = new LinkedList<>();

        List<Point> loop = new ArrayList<>();

        toVisit.add(new BFSStep(startingPoint, 0));
        visited.add(startingPoint);
        loop.add(startingPoint);
        while(!toVisit.isEmpty()) {
            BFSStep p = toVisit.pop();

            for(Point p2: graph.get(p.point)) {
                if (p2.equals(startingPoint)) {
                    return loop;
                }

                if (!visited.contains(p2)) {
                    visited.add(p2);
                    loop.add(p2);
                    toVisit.add(new BFSStep(p2, p.length + 1));
                }
            }
        }

        return loop;
    }

    private Point parseInput(List<String> input, Map<Point, Set<Point>> graph, List<List<String>> matrix) {
        Point startingPoint = null;
        for(String v: input) {
            String[] data = v.split("");
            List<String> line = new ArrayList<>(Arrays.stream(data).toList());
            matrix.add(line);
        }

        int rows = matrix.size();
        int cols = matrix.get(0).size();

        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                if (matrix.get(r).get(c).equals("S")) {
                    startingPoint = new Point(r, c);
                }

                Point here = new Point(r, c);
                Optional<Point> northConnection = isConnectedNorth(r, c, matrix);
                northConnection.ifPresent(there -> addConnection(here, there, graph));

                Optional<Point> eastConnection = isConnectedEast(r, c, matrix);
                eastConnection.ifPresent(there -> addConnection(here, there, graph));

                Optional<Point> southConnection = isConnectedSouth(r, c, matrix);
                southConnection.ifPresent(there -> addConnection(here, there, graph));

                Optional<Point> westConnection = isConnectedWest(r, c, matrix);
                westConnection.ifPresent(there -> addConnection(here, there, graph));
            }
        }

        return startingPoint;
    }

    private void addConnection(Point p1, Point p2, Map<Point, Set<Point>> graph) {
        Set<Point> p1Connections = graph.getOrDefault(p1, new HashSet<>());
        Set<Point> p2Connections = graph.getOrDefault(p2, new HashSet<>());

        p1Connections.add(p2);
        p2Connections.add(p1);

        graph.put(p1, p1Connections);
        graph.put(p2, p2Connections);
    }

    private Optional<Point> isConnectedNorth(int x1, int y1, List<List<String>> matrix) {
        String thisValue = getValue(x1, y1, matrix).orElseThrow();
        Optional<String> northValue = getValue(x1 - 1, y1, matrix);

        if (northValue.isEmpty()) {
            return Optional.empty();
        }
        String val = northValue.get();

        if (northConnections.contains(thisValue) && southConnections.contains(val)) {
            return Optional.of(new Point(x1 - 1, y1));
        }

        return Optional.empty();
    }

    private Optional<Point> isConnectedSouth(int x1, int x2, List<List<String>> matrix) {
        String thisValue = getValue(x1, x2, matrix).orElseThrow();
        Optional<String> northValue = getValue(x1 + 1, x2, matrix);

        if (northValue.isEmpty()) {
            return Optional.empty();
        }

        String val = northValue.get();

        if (southConnections.contains(thisValue) && northConnections.contains(val)) {
            return Optional.of(new Point(x1 + 1, x2));
        }

        return Optional.empty();
    }

    private Optional<Point> isConnectedEast(int x1, int x2, List<List<String>> matrix) {
        String thisValue = getValue(x1, x2, matrix).orElseThrow();
        Optional<String> northValue = getValue(x1, x2 + 1, matrix);

        if (northValue.isEmpty()) {
            return Optional.empty();
        }

        String val = northValue.get();

        if(eastConnections.contains(thisValue) && westConnections.contains(val)) {
            return Optional.of(new Point(x1, x2 + 1));
        }

        return Optional.empty();
    }

    private Optional<Point> isConnectedWest(int x1, int x2, List<List<String>> matrix) {
        String thisValue = getValue(x1, x2, matrix).orElseThrow();
        Optional<String> northValue = getValue(x1, x2 - 1, matrix);

        if (northValue.isEmpty()) {
            return Optional.empty();
        }

        String val = northValue.get();

        if(westConnections.contains(thisValue) && eastConnections.contains(val)) {
            return Optional.of(new Point(x1, x2 - 1));
        }

        return Optional.empty();
    }

    private Optional<String> getValue(int r, int c, List<List<String>> matrix) {
        if (r >= 0 && r < matrix.size() && c >= 0 && c < matrix.get(0).size()) {
            return Optional.of(matrix.get(r).get(c));
        }

        return Optional.empty();
    }
}
