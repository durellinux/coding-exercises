package com.gianlucadurelli.coding.leetcode;

import com.gianlucadurelli.coding.leetcode.Robot9_2;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Robot9_2Test {

    @Test
    public void test() {
        int[][] grid = new int[][]{
                new int[]{0, 0, 0, 1},
                new int[]{0, 1, 0, 1},
                new int[]{0, 0, 0, 1},
                new int[]{1, 0, 0, 0}
        };

        int[][] grid2 = new int[][]{
                new int[]{0, 0, 0},
                new int[]{1, 1, 0},
                new int[]{1, 1, 0}
        };

        int[][] grid3 = new int[][]{
                new int[]{0,0,1,0,1,1},
                new int[]{1,0,0,1,0,0},
                new int[]{0,1,0,1,0,0},
                new int[]{1,0,1,0,0,0},
                new int[]{0,1,0,1,0,0},
                new int[]{0,0,0,0,0,0}
        };

        Robot9_2 solver = new Robot9_2();
        Assertions.assertThat(solver.solver(grid)).isEqualTo(5);
        Assertions.assertThat(solver.solver(grid2)).isEqualTo(4);
        Assertions.assertThat(solver.solver(grid3)).isEqualTo(6);

    }
}