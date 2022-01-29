package com.gianlucadurelli.coding.leetcode;

// https://leetcode.com/explore/interview/card/facebook/52/trees-and-graphs/274/
public class NumberOfIslands {
	private static final char MARK = 'X';
	private static final char WATER = '0';
	private static final char LAND = '1';

	public int numIslands(char[][] grid) {
		int m = grid.length;
		int n = grid[0].length;

		int islands = 0;

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == LAND) {
					islands++;
					exploreAndMark(grid, i, j, m, n);
				}
			}
		}

		return islands;
	}

	private void exploreAndMark(char[][] grid, int i, int j, int m, int n) {
		if (getElement(grid, i, j, m, n) == LAND) {
			grid[i][j] = MARK;
			exploreAndMark(grid, i+1, j, m, n);
			exploreAndMark(grid, i-1, j, m, n);
			exploreAndMark(grid, i, j+1, m, n);
			exploreAndMark(grid, i, j-1, m, n);
		}
	}

	private char getElement(char [][] grid, int i, int j, int m, int n) {
		if(i < 0 || j < 0 || i >= m || j >= n) {
			return WATER;
		}

		return grid[i][j];
	}

}
