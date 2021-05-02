package com.gianlucadurelli.coding.hackerrank.neurodiversity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiverseGroupingMapContest {
    Map<Integer, Map<Boolean, Long>> memoized = new HashMap();

    public static int numberOfPartitions(List<Integer> arr) {
        // Write your code here
        return Math.toIntExact((long) (numberOfPartitions(arr, 0, 0, false) % (Math.pow(10, 9) + 7)));
    }

    public static long numberOfPartitions(List<Integer> arr, int elem, long sum, boolean mustBeEven) {
        if (elem == arr.size()) {
            return 0;
        }

        long nextSum = sum + arr.get(elem);
        boolean sumEven = nextSum % 2 == 0;

        long solutionsFromHere = sumEven == mustBeEven ? 1 : 0;
        if (sumEven == mustBeEven) {
            solutionsFromHere += numberOfPartitions(arr, elem + 1, 0, !mustBeEven);
        }

        solutionsFromHere += numberOfPartitions(arr, elem + 1, nextSum, mustBeEven);

        return solutionsFromHere;
    }

}
