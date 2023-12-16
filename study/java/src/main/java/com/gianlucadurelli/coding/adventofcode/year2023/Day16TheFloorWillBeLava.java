package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;

public class Day16TheFloorWillBeLava {
    private record Laser(int row, int col, char direction) {}

    private record Point(int row, int col) {}

    public long solve(List<String> input) {
        char[][] matrix = parseInput(input);

        return simulate(matrix, new Laser(0, -1, 'E'));
    }

    public long solve2(List<String> input) {
        char[][] matrix = parseInput(input);

        long maxEnergized = 0;

        for (int r = 0; r < matrix.length; r++) {
            long east = simulate(matrix, new Laser(r, -1, 'E'));
            long west = simulate(matrix, new Laser(r, matrix.length, 'W'));
            maxEnergized = Math.max(maxEnergized, Math.max(east, west));
        }

        for (int c = 0; c < matrix[0].length; c++) {
            long south = simulate(matrix, new Laser(-1, c, 'S'));
            long north = simulate(matrix, new Laser(matrix[0].length, c, 'N'));
            maxEnergized = Math.max(maxEnergized, Math.max(north, south));
        }

        return maxEnergized;
    }

    private char[][] parseInput(List<String> input) {
        int R = input.size();
        int C = input.get(0).length();

        char[][] matrix = new char[R][C];

        for (int r = 0; r < R; r++) {
            char[] line = input.get(r).toCharArray();
            for (int c = 0; c < C; c++) {
                matrix[r][c] = line[c];
            }
        }

        return matrix;
    }

    private long simulate(char[][] matrix, Laser startingLaser) {
        Deque<Laser> lasers = new LinkedList<>();
        lasers.add(startingLaser);

        Set<Point> energizedPoints = new HashSet<>();

        Set<Laser> visited = new HashSet<>();

        while (!lasers.isEmpty()) {
            Laser laser = lasers.poll();

            int dx = 0;
            int dy = 0;

            switch (laser.direction) {
                case 'N' -> dx = -1;
                case 'S' -> dx = 1;
                case 'E' -> dy = 1;
                case 'W' -> dy = -1;
            }

            int x = laser.row + dx;
            int y = laser.col + dy;

//            System.out.println("Testing " + laser);

            Optional<Character> value = getValue(matrix, x, y);
            if (value.isPresent()) {
                List<Laser> newLasers = new ArrayList<>();
                energizedPoints.add(new Point(x, y));
                if (laser.direction == 'N' && (value.get().equals('/') || value.get().equals('-'))) {
                    newLasers.add(new Laser(x, y, 'E'));
                }
                if (laser.direction == 'N' && (value.get().equals('\\') || value.get().equals('-'))) {
                    newLasers.add(new Laser(x, y, 'W'));
                }
                if (laser.direction == 'N' && (value.get().equals('|') || value.get().equals('.'))) {
                    lasers.addFirst(new Laser(x, y, laser.direction()));
                }

                if (laser.direction == 'S' && (value.get().equals('/') || value.get().equals('-'))) {
                    newLasers.add(new Laser(x, y, 'W'));
                }
                if (laser.direction == 'S' && (value.get().equals('\\') || value.get().equals('-'))) {
                    newLasers.add(new Laser(x, y, 'E'));
                }
                if (laser.direction == 'S' && (value.get().equals('|') || value.get().equals('.'))) {
                    lasers.addFirst(new Laser(x, y, laser.direction()));
                }

                if (laser.direction == 'E' && (value.get().equals('/') || value.get().equals('|'))) {
                    newLasers.add(new Laser(x, y, 'N'));
                }
                if (laser.direction == 'E' && (value.get().equals('\\') || value.get().equals('|'))) {
                    newLasers.add(new Laser(x, y, 'S'));
                }
                if (laser.direction == 'E' && (value.get().equals('-') || value.get().equals('.'))) {
                    lasers.addFirst(new Laser(x, y, laser.direction()));
                }

                if (laser.direction == 'W' && (value.get().equals('/') || value.get().equals('|'))) {
                    newLasers.add(new Laser(x, y, 'S'));
                }
                if (laser.direction == 'W' && (value.get().equals('\\') || value.get().equals('|'))) {
                    newLasers.add(new Laser(x, y, 'N'));
                }
                if (laser.direction == 'W' && (value.get().equals('-') || value.get().equals('.'))) {
                    lasers.addFirst(new Laser(x, y, laser.direction()));
                }

                for (Laser newLaser: newLasers) {
                    if (!visited.contains(newLaser)) {
                        visited.add(newLaser);
                        lasers.add(newLaser);
                    }
                }

            }
        }

        return energizedPoints.size();
    }

    private Optional<Character> getValue(char[][] matrix, int r, int c) {
        if (r < 0 || r >= matrix.length || c < 0 || c >= matrix[0].length) {
            return Optional.empty();
        }

        return Optional.of(matrix[r][c]);
    }
}
