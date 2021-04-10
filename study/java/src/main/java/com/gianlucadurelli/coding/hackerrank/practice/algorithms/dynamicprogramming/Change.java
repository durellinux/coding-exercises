package com.gianlucadurelli.coding.hackerrank.practice.algorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

public class Change {
	private Map<Long, Long> coins = new HashMap<>();
	private long value;

	public Change() {}
	public Change(Change other) {
		this.coins = new HashMap<>(other.coins);
		this.value = other.value;
	}

	public long getValue() {
		return value;
	}

	public void addCoin(long coin) {
		value += coin;
		if (!coins.containsKey(coin)) {
			coins.put(coin, 0L);
		}

		coins.put(coin, coins.get(coin) + 1L);
	}
}