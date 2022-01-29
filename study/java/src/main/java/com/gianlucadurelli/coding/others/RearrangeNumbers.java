package com.gianlucadurelli.coding.others;


//-------------------------------[ T#2 ]------------------------------------------
//		Write a function accepting a list of non negative integers,
//		and returning their largest possible combined number
//		as a string. For example
//		given [50, 2, 1, 9] it returns "95021"    (9 + 50 + 2 + 1)
//		given [5, 50, 56]   it returns "56550"    (56 + 5 + 50)
//		given 420, 42, 423] it returns "42423420" (42 + 423 + 420)
//--------------------------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

public class RearrangeNumbers {

	public String combineNumbersRecursive(List<Integer> numbers) {
		return combineNumbersRecursiveSolver(numbers, "");
	}

	private String combineNumbersRecursiveSolver(List<Integer> numbers, String value) {

		if (numbers.isEmpty()) {
			return value;
		}

		List<String> possibleValues = new ArrayList<>();
		for (Integer n: numbers) {
			List<Integer> otherList = new ArrayList<>(numbers);
			otherList.remove(n);
			possibleValues.add(combineNumbersRecursiveSolver(otherList, value + n));
		}

		return "" + possibleValues.stream().mapToLong(Long::valueOf).max().getAsLong();
	}

	public String combineNumbersSorting(List<Integer> numbers) {
		List<Integer> sorted = new ArrayList<>(numbers);

		sorted.sort(
				(a, b) -> {
					if (a == 0) {
						return 1;
					}

					if (b == 0) {
						return -1;
					}

					int max = a > b ? a : b;
					int min = a > b ? b : a;

					int digitsMax = String.valueOf(max).length();
					int digitsMin = String.valueOf(min).length();

					int iterations = digitsMax - digitsMin;
					int i = 0;

					do {
						int maxWindow1 = (int) (max % Math.pow(10, digitsMax - i));
						int digitsFromMaxBegin = i + digitsMin;
						int digitsToDrop = digitsMax - digitsFromMaxBegin;
						int maxWindowed = (int) (maxWindow1 / Math.pow(10, digitsToDrop));

						if (maxWindowed > min) {
							return min == b ? -1 : 1;
						} else if (min > maxWindowed) {
							return min == b ? 1 : -1;
						}

						i++;
					} while (i <= iterations);

					return 0;
				}
		);

		String result = "";
		for (Integer v: sorted) {
			result += v;
		}

		return result;
	}
}
