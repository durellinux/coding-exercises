package com.gianlucadurelli.coding.leetcode;

import java.util.Arrays;

// https://leetcode.com/problems/koko-eating-bananas/
public class KokoEatingBananas {
    public int minEatingSpeed(int[] piles, int h) {
        return findRate(piles, h, 1, getMaxRate(piles));
    }

    private int findRate(int[] piles, int h, int min, int max) {
        while(min != max) {
            int k = (min + max) / 2;
            if (canEat(piles, h, k)) {
                max = k;
            } else {
                min = k + 1;
            }
        }

        return min;
    }

    private int getMaxRate(int[] piles) {
        int max = 0;
        for (int value: piles) {
            max = Math.max(max, value);
        }
        return max;
    }

    private boolean canEat(int[] piles, int h, int k) {
        long currentH = 0;
        for (int i = 0; i < piles.length && currentH <= h; i++) {
            int intPart = piles[i] / k;
            int maybe1Hour = piles[i] % k != 0 ? 1: 0;
            currentH += intPart + maybe1Hour;
        }

        return currentH <= h;
    }
}
