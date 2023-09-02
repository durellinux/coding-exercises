package com.gianlucadurelli.coding.leetcode;

import java.util.Arrays;

// https://leetcode.com/problems/zigzag-conversion/?envType=study-plan-v2&envId=top-interview-150
public class ZigZagConversion {
    public String convert(String s, int numRows) {
        int[] row = new int[s.length()];
        Arrays.fill(row, -1);

        int currentRow = -1;
        boolean goingUp = true;
        for (int i = 0; i < s.length(); i++) {
            if (goingUp) {
                currentRow++;
                currentRow = Math.min(currentRow, numRows - 1);
                row[i] = currentRow;
                if (currentRow == numRows - 1) {
                    goingUp = false;
                }
            } else {
                currentRow--;
                currentRow = Math.max(currentRow, 0);
                row[i] = currentRow;
                if (currentRow == 0) {
                    goingUp = true;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            for (int r = 0; r < row.length; r++) {
                if (row[r] == i) {
                    sb.append(s.charAt(r));
                }
            }
        }

        return sb.toString();
    }
}
