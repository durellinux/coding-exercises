package com.gianlucadurelli.coding.hackerrank.practice.algorithms.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Solution for: https://www.hackerrank.com/challenges/luck-balance/problem
 */
public class LuckBalance {
    public static int luckBalance(int k, int[][] contests) {
        Arrays.sort(contests, (o1, o2) -> -Integer.compare(o1[0], o2[0]));

        int luck = 0;
        int lostImportantContests = 0;

        for (int[] c: contests) {
            if (c[1] == 0) {
                luck += c[0];
            } else if(lostImportantContests < k) {
                luck += c[0];
                lostImportantContests++;
            } else {
                luck -= c[0];
            }
        }

        return luck;
    }

}
