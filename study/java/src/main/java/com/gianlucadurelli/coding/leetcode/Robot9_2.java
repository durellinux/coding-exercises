package com.gianlucadurelli.coding.leetcode;

import java.util.*;

public class Robot9_2 {
    public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0 || grid[0][0] == 1) {
            return -1;
        }

        return solver(grid);
    }

    public int solver(int[][] grid) {
        int endY = grid.length - 1;
        int endX = grid[0].length - 1;

        Set<List<Integer>> visited = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
        visited.add(List.of(0, 0));
        queue.add(new int[]{0, 0, 1});

        while(!queue.isEmpty()) {
            int[] current = queue.remove();
            int startY = current[0];
            int startX = current[1];
            int currentLength = current[2];

            if (startX == endX && startY == endY) {
                return currentLength;
            }

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    int nextX = startX + x;
                    int nextY = startY + y;

                    List<Integer> next = List.of(nextY, nextX);
                    if (visited.contains(next)) {
                        continue;
                    }

                    if (nextX >= 0 && nextX <= endX && nextY >= 0 && nextY <= endY && grid[nextY][nextX] != 1) {
                        visited.add(next);
                        queue.add(new int[]{nextY, nextX, currentLength + 1});
                    }
                }
            }
        }

        return -1;
    }
}
