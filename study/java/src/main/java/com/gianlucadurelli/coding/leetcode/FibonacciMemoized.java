package com.gianlucadurelli.coding.leetcode;

import java.util.HashMap;
import java.util.Map;

public class FibonacciMemoized {
    public int fib(int n) {
        Map<Integer, Integer> cache = new HashMap<>();
        return fib(n, cache);
    }

    private int fib(int i, Map<Integer, Integer> memoized) {
        if (i == 0) {
            return 0;
        }

        if (i == 1) {
            return 1;
        }

        if (memoized.containsKey(i)) {
            return memoized.get(i);
        }

        int result = fib(i - 2) + fib(i - 1);
        memoized.put(i, result);
        return result;
    }
}
