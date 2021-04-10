package com.gianlucadurelli.coding.leetcode;

public class ImageSmother {
	public int[][] solve(int[][] M) {

		int R = M.length;
		if (R == 0) {
			return M;
		}
		int C = M[0].length;
		if (C == 0) {
			return M;
		}

		int[][] result = new int[R][C];

		for (int i = 0; i < R; i ++) {
			for (int j = 0; j < C; j++) {
				int sum = 0;
				int values = 0;
				for (int m = -1; m < 2; m ++) {
					for (int n = -1; n < 2; n++) {
						boolean inRow = !(i + m < 0 || i + m >= R);
						boolean inCol = !(j + n < 0 || j + n >= C);

						if (inCol && inRow) {
							values += 1;
							sum += M[i+m][j+n];
						}
					}
				}
				result[i][j] = sum / values;
			}
		}

		return result;
	}
}
