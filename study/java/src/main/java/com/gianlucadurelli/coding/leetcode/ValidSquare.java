package com.gianlucadurelli.coding.leetcode;

import java.util.ArrayList;
import java.util.List;

public class ValidSquare {
	public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
		List<int[]> points = List.of(p1, p2, p3, p4);
		List<Double> lengths = new ArrayList();
		for (int i = 0; i < points.size(); i++) {
			for (int j = i + 1; j < points.size(); j++) {
				int[] px = points.get(i);
				int[] py = points.get(j);

				double length = Math.sqrt(Math.pow(px[0] - py[0], 2) + Math.pow(px[1] - py[1], 2));
				lengths.add(length);
			}
		}

		Double min = lengths.stream().min(Double::compareTo).get();
		Double max = lengths.stream().max(Double::compareTo).get();

		long minCount = lengths.stream().filter(l -> l.equals(min)).count();
		long maxCount = lengths.stream().filter(l -> l.equals(max)).count();

		return minCount == 4 && maxCount == 2;
	}
}
