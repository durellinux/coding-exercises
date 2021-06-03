package com.gianlucadurelli.coding.hackerrank.neurodiversity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiversePopulationPairs {
    public static long computePairs(Integer v1, Integer v2, Map<Integer, Integer> occurrences) {

        Integer v1Occurences = occurrences.get(v1);
        Integer v2Occurrences = occurrences.getOrDefault(v2, 0);

        if (v1Occurences > 0 && v2Occurrences > 0 ) {
            if (v1.equals(v2)) {
                return v1Occurences - 1;
            }
            return v2Occurrences;
        }

        return 0;

    }

    public static long interestingPairs(List<Integer> arr, int sumValue) {
        Map<Integer, Integer> occurrences = new HashMap<>();

        for(Integer v: arr) {
            if (!occurrences.containsKey(v)) {
                occurrences.put(v, 0);
            }

            occurrences.put(v, occurrences.get(v) + 1);
        }

        long pairs = 0;

        for(Integer v: arr) {
            int minValue = Math.toIntExact(Math.abs(v) * 2L);

            if (sumValue == minValue) {
                for (int i = -minValue/2 +1; i <= minValue/2-1; i++) {
                    pairs += computePairs(v, i, occurrences);
                }
            }

            if (sumValue >= minValue) {
                if (sumValue % 2 == 0) {
                    int otherValue = sumValue / 2;
                    pairs += computePairs(v, otherValue, occurrences);
                }
            }
        }

        return  pairs/2;
    }
}
