package com.gianlucadurelli.coding.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpiralMatrix {
    public List<Integer> spiralOrder(int[][] matrix) {
        int R = matrix.length;
        if (R == 0) {
            return Collections.emptyList();
        }

        int C = matrix[0].length;
        if (C == 0) {
            return Collections.emptyList();
        }

        Integer[][] clone = new Integer[R][C];
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                clone[r][c] = matrix[r][c];
            }
        }

        List<Integer> result = new ArrayList<>();
        char direction = 'r';
        int r = 0;
        int c = 0;
        while (result.size() != R * C) {
            result.add(clone[r][c]);
            clone[r][c] = null;
            int dx = direction == 'r' ? 1 : direction == 'l' ? -1 : 0;
            int dy = direction == 'd' ? 1 : direction == 'u' ? -1 : 0;

            if (c + dx == C || c + dx == -1 || r + dy == R || r + dy == -1 || clone[r + dy][c + dx] == null) {
                direction = nextDirection(direction);
            }

            dx = direction == 'r' ? 1 : direction == 'l' ? -1 : 0;
            dy = direction == 'd' ? 1 : direction == 'u' ? -1 : 0;

            r = r + dy;
            c = c + dx;
        }

        return result;
    }

    private char nextDirection(char direction) {
        switch (direction) {
            case 'r': return 'd';
            case 'd': return 'l';
            case 'l': return 'u';
            case 'u': return 'r';
        }

        throw new RuntimeException("Unhandled case");
    }

}
