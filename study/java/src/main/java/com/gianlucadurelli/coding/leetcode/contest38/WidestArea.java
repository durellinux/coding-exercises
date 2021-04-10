package com.gianlucadurelli.coding.leetcode.contest38;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WidestArea {
	public int maxWidthOfVerticalArea(int[][] points) {
		List<Integer> xValues = new ArrayList<>();

		for (int[] point: points) {
			xValues.add(point[0]);
		}

		int result = 0;
		Collections.sort(xValues);
		for(int i = 0; i < xValues.size() - 1; i++) {
			int tmp = xValues.get(i + 1) - xValues.get(i);
			if (tmp > result) {
				result = tmp;
			}
		}

		return result;
	}
}
