package com.gianlucadurelli.coding.others;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RearrangeNumbersTest {
	List<List<Integer>> testData = new ArrayList<>();
	List<String> goldenData = new ArrayList<>();


	@Before
	public void setup() {
		testData.add(List.of(50, 2, 1, 9));
		goldenData.add("95021");

		testData.add(List.of(5, 50, 56));
		goldenData.add("56550");

		testData.add(List.of(420, 42, 423));
		goldenData.add("42423420");

		testData.add(List.of(6, 67, 638, 0, 67));
		goldenData.add("676766380");

		testData.add(List.of());
		goldenData.add("");

		testData.add(List.of(554, 55));
		goldenData.add("55554");
	}


	@Test
	public void rearrangeNumbersRecursiveTest() {
		RearrangeNumbers rearrangeNumbers = new RearrangeNumbers();

		for (int i=0; i<testData.size(); i++) {
			List<Integer> data = testData.get(i);
			String golden = goldenData.get(i);

			String solution = rearrangeNumbers.combineNumbersRecursive(data);
			Assertions.assertThat(solution).isEqualTo(golden);
		}
	}

	@Test
	public void rearrangeNumbersSortingTest() {
		RearrangeNumbers rearrangeNumbers = new RearrangeNumbers();

		for (int i=0; i<testData.size(); i++) {
			List<Integer> data = testData.get(i);
			String golden = goldenData.get(i);

			String solution = rearrangeNumbers.combineNumbersRecursive(data);
			Assertions.assertThat(solution).isEqualTo(golden);
		}
	}
}