package com.gianlucadurelli.coding.hackerrank.certifications;

import java.util.ArrayList;
import java.util.List;

public class MaxSubarray {
    public static long maxSubarrayValue(List<Integer> arr) {
        List<Integer> evenSum = new ArrayList<>();
        List<Integer> oddSum = new ArrayList<>();
        List<Long> value = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            evenSum.add(0);
            oddSum.add(0);

            int val = arr.get(i);

            if (i == 0) {
                evenSum.set(i, val);
            } else if (i == 1) {
                oddSum.set(i, val);
                evenSum.set(i, evenSum.get(0));
            } else if (i % 2 == 0) {
                evenSum.set(i, evenSum.get(i - 2) + val);
                oddSum.set(i, oddSum.get(i - 1));
            } else {
                oddSum.set(i, oddSum.get(i - 2) + val);
                evenSum.set(i, evenSum.get(i - 1));
            }

            long diff = evenSum.get(i) - oddSum.get(i);
            value.add(diff * diff);
        }

        Long min = Math.min(value.stream().min(Long::compareTo).orElse(0L), 0);
        Long max = value.stream().max(Long::compareTo).orElse(0L);

        return max - min;
    }
}
