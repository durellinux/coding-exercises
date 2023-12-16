package com.gianlucadurelli.coding.adventofcode.year2022;

import java.util.*;
import java.util.stream.Collectors;

public class Day24BlizzardBasin {

    private record Blizzard(int row, int col, char direction) {}

    private record Point(int row, int col) {}

    private record SimulationStep(Point current, long step) {}

    public long solve(List<String> input, int trips) {
        char[][] basin = parseInput(input);
        int R = basin.length;
        int C = basin[0].length;

        Set<Blizzard> blizzards = new HashSet<>();
        for (int r = 0; r < basin.length; r++) {
            for (int c = 0; c < basin[r].length; c++) {
                if (basin[r][c] == '<' || basin[r][c] == '^' || basin[r][c] == '>' || basin[r][c] == 'v') {
                    blizzards.add(new Blizzard(r, c, basin[r][c]));
                }
            }
        }

        Point startPosition = null;
        Point endPosition = null;

        for (int c = 0; c < basin[0].length; c++) {
            if (basin[0][c] == '.') {
                startPosition = new Point(0, c);
            }

            if (basin[basin.length - 1][c] == '.') {
                endPosition = new Point(basin.length - 1, c);
            }
        }

        Map<Long, Set<Blizzard>> blizzardConfigurations = new HashMap<>();
        Map<Long, Set<Point>> blizzardPositions = new HashMap<>();
        blizzardConfigurations.put(0L, blizzards);
        blizzardPositions.put(0L, computeBlizzardPositions(blizzards));

        long time = 0;
        for (int i = 0; i < trips; i++) {
            time = navigate(startPosition, endPosition, R, C, time, blizzardConfigurations, blizzardPositions);

            System.out.println("Navigating from " + startPosition + " to " + endPosition + " in " + time + " - Configurations: " + blizzardConfigurations.size());

            Point tmp = startPosition;
            startPosition = endPosition;
            endPosition = tmp;
        }

        return time;
    }

    private long navigate(Point startPosition, Point endPosition, int R, int C, long startStep, Map<Long, Set<Blizzard>> blizzardConfigurations, Map<Long, Set<Point>> blizzardPositions) {
        Queue<SimulationStep> simulations = new LinkedList<>();

        long blizzardStates = lcm(R, C);

        Map<Long, Set<Point>> memoizedSolutions = new HashMap<>();
        memoizedSolutions.put(startStep % blizzardStates, new HashSet<>());
        memoizedSolutions.get(startStep % blizzardStates).add(startPosition);

        simulations.add(new SimulationStep(startPosition, startStep));

        while(!simulations.isEmpty()) {
            SimulationStep simulation = simulations.poll();

            if (simulation.current.equals(endPosition)) {
                return simulation.step;
            }

            long nextStep = simulation.step + 1;
            long currentHashIndex = simulation.step % blizzardStates;
            long nextHashIndex = nextStep % blizzardStates;
            Set<Blizzard> currentConfig = blizzardConfigurations.get(currentHashIndex);
            computeValueInCache(blizzardConfigurations, blizzardPositions, R, C, currentConfig, nextStep);
            Set<Point> blizzardsPositions = blizzardPositions.get(nextHashIndex);

            Optional<Point> wait = isValidMove(simulation.current.row, simulation.current.col, R, C, blizzardsPositions, startPosition, endPosition);
            Optional<Point> north = isValidMove(simulation.current.row - 1, simulation.current.col, R, C, blizzardsPositions, startPosition, endPosition);
            Optional<Point> south = isValidMove(simulation.current.row + 1, simulation.current.col, R, C, blizzardsPositions, startPosition, endPosition);
            Optional<Point> east = isValidMove(simulation.current.row, simulation.current.col + 1, R, C, blizzardsPositions, startPosition, endPosition);
            Optional<Point> west = isValidMove(simulation.current.row, simulation.current.col - 1, R, C, blizzardsPositions, startPosition, endPosition);

            List<SimulationStep> nextSteps = new ArrayList<>();
            wait.ifPresent(p -> nextSteps.add(new SimulationStep(p, nextStep)));
            north.ifPresent(p -> nextSteps.add(new SimulationStep(p, nextStep)));
            south.ifPresent(p -> nextSteps.add(new SimulationStep(p, nextStep)));
            east.ifPresent(p -> nextSteps.add(new SimulationStep(p, nextStep)));
            west.ifPresent(p -> nextSteps.add(new SimulationStep(p, nextStep)));

            Set<Point> visitedPoints = memoizedSolutions.getOrDefault(nextHashIndex, new HashSet<>());
            for (SimulationStep simulationStep: nextSteps) {
                if (simulationStep.current.equals(endPosition)) {
                    return simulationStep.step;
                }

                if (!visitedPoints.contains(simulationStep.current)) {
                    visitedPoints.add(simulationStep.current);
                    memoizedSolutions.put(nextHashIndex, visitedPoints);
                    simulations.add(simulationStep);
                }
            }
        }

        return -1;
    }

    private void computeValueInCache(Map<Long, Set<Blizzard>> blizzardConfigurations, Map<Long, Set<Point>> blizzardPositions, int R, int C, Set<Blizzard> initialState, long step) {
        long blizzardStates = lcm(R, C);

        long hashedIndex = step % blizzardStates;
        if (!blizzardPositions.containsKey(hashedIndex)) {
            Set<Blizzard> nextConfig = simulateBlizzardMovement(initialState, R, C);
            blizzardConfigurations.put(hashedIndex, nextConfig);
            blizzardPositions.put(hashedIndex, computeBlizzardPositions(nextConfig));
        }
    }

    private Set<Point> computeBlizzardPositions(Set<Blizzard> blizzards) {
        return blizzards.stream().map(b -> new Point(b.row, b.col)).collect(Collectors.toSet());
    }

    private Set<Blizzard> simulateBlizzardMovement(Set<Blizzard> blizzards, int R, int C) {
        Set<Blizzard> newBlizzards = new HashSet<>();


        for (Blizzard blizzard: blizzards) {
            int dx = 0;
            int dy = 0;

            switch (blizzard.direction) {
                case '>' -> dy = 1;
                case '<' -> dy = -1;
                case 'v' -> dx = 1;
                case '^' -> dx = -1;
            }

            int r = blizzard.row + dx;
            int c = blizzard.col + dy;

            if (r == 0) {
                r = R - 2;
            }

            if (r == R - 1) {
                r = 1;
            }

            if (c == 0) {
                c = C - 2;
            }

            if (c == C - 1) {
                c = 1;
            }

            newBlizzards.add(new Blizzard(r, c, blizzard.direction));
        }

        return newBlizzards;
    }

    private char[][] parseInput(List<String> input) {
        int R = input.size();
        int C = input.get(0).length();
        char[][] matrix = new char[R][C];

        for(int r = 0; r < R; r++ ) {
            char[] line = input.get(r).toCharArray();
            System.arraycopy(line, 0, matrix[r], 0, C);
        }

        return matrix;
    }

    private Optional<Point> isValidMove(int r, int c, int R, int C, Set<Point> blizzardPositions, Point startPosition, Point endPosition) {
        if (r < 0 || ( r == startPosition.row && c != startPosition.col ) || r > R - 1 || ( r == endPosition.row && c != endPosition.col) || c <= 0 || c >= C - 1) {
            return Optional.empty();
        }

        Point p = new Point(r, c);
        if (blizzardPositions.contains(p)) {
            return Optional.empty();
        }

        return Optional.of(p);
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
}
