package com.gianlucadurelli.coding.leetcode;

import java.util.HashSet;
import java.util.Set;

// https://leetcode.com/problems/valid-sudoku/?envType=study-plan-v2&envId=top-interview-150
public class ValidSudoku {
    public boolean isValidSudoku(char[][] board) {

        for (char[] chars : board) {
            Set<Character> values = new HashSet<>();
            for (int c = 0; c < board.length; c++) {
                char v = chars[c];
                if (v != '.' && values.contains(v)) {
                    return false;
                }

                values.add(v);
            }
        }

        for (int c = 0; c < board.length; c++) {
            Set<Character> values = new HashSet<>();
            for (int r = 0; r < board.length; r++) {
                char v = board[r][c];
                if (v != '.' && values.contains(v)) {
                    return false;
                }

                values.add(v);
            }
        }

        for (int r = 0; r < board.length; r += 3) {
            for (int c = 0; c < board.length; c += 3) {
                Set<Character> values = new HashSet<>();

                for (int dr = 0; dr < 3; dr++) {
                    for (int dc = 0; dc < 3; dc++) {
                        char v = board[r + dr][c + dc];
                        if (v != '.' && values.contains(v)) {
                            return false;
                        }

                        values.add(v);
                    }
                }
            }
        }

        return true;
    }
}
