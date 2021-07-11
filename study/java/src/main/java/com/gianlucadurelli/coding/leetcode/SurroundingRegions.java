package com.gianlucadurelli.coding.leetcode;

import java.util.*;

// https://leetcode.com/problems/surrounded-regions/solution/
public class SurroundingRegions {
    public void Solve(char[][] board) {
        Set<List<Integer>> visited = new HashSet<>();

        if (board.length == 0 || board[0].length == 0) {
            return;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                List<Integer> coord = List.of(i, j);
                if (board[i][j] == 'O' && !visited.contains(coord)) {
                    visited.add(coord);
                    bfs(board, i, j, visited);
                }
            }
        }
    }

    private void bfs(char[][] board, int i, int j, Set<List<Integer>> visited) {
        List<List<Integer>> moves = List.of(
                List.of(1, 0), List.of(-1, 0), List.of(0, 1), List.of(0, -1)
        );
        List<List<Integer>> connected = new LinkedList<List<Integer>>();

        Queue<List<Integer>> toVisit = new LinkedList<List<Integer>>();
        toVisit.add(List.of(i, j));

        boolean valid = true;
        while(!toVisit.isEmpty()) {
            List<Integer> coord = toVisit.remove();
            int i1 = coord.get(0);
            int j1 = coord.get(1);
            connected.add(coord);

            for (List<Integer> m: moves) {
                int i2 = i1 + m.get(0);
                int j2 = j1 + m.get(1);
                if (i2 < 0 || i2 >= board.length || j2 < 0 || j2 >= board[0].length) {
                    valid = false;
                }

                List<Integer> coord2 = List.of(i2, j2);
                if (board[i2][j2] == 'O' && !visited.contains(coord2)) {
                    visited.add(coord2);
                    toVisit.add(coord2);
                }
            }
        }

        if (valid) {
            for (List<Integer> c: connected) {
                board[c.get(0)][c.get(1)] = 'X';
            }
        }
    }
}
