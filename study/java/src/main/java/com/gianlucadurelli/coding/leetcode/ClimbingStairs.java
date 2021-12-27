package com.gianlucadurelli.coding.leetcode;

import java.util.ArrayList;
import java.util.List;

public class ClimbingStairs {
    public int climbStairs(int n) {
        List<Integer> cache = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            cache.add(0);
        }

        return solve(n, cache);
    }

    private int solve(int i, List<Integer> cache) {

        if (i < 0) {
            return 0;
        }

        if (cache.get(i) > 0) {
            return cache.get(i);
        }

        if (i == 0) {
            cache.set(0, 1);
            return 1;
        }


        int result = solve(i - 1, cache) + solve(i - 2, cache);
        cache.set(i, result);

        return result;
    }
}
