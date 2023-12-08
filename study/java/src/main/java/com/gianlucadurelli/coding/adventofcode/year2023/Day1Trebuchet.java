package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// https://adventofcode.com/2023/day/1
public class Day1Trebuchet {
    private static final Map<String, Integer> stringValueMap = Map.of(
        "one", 1,
        "two", 2,
        "three", 3,
        "four", 4,
        "five", 5,
        "six", 6,
        "seven", 7,
        "eight", 8,
        "nine", 9
    );

    private static final Map<String, Integer> digitValueMap = Map.of(
        "0", 0,
        "1", 1,
        "2", 2,
        "3", 3,
        "4", 4,
        "5", 5,
        "6", 6,
        "7", 7,
        "8", 8,
        "9", 9
    );

    public int solve1(List<String> input) {
        int sum = 0;

        for (String value: input) {
            sum += parseString(value, digitValueMap);
        }

        return sum;
    }

    public int solve2(List<String> input) {
        Map<String, Integer> fullMap = new HashMap<>();
        fullMap.putAll(stringValueMap);
        fullMap.putAll(digitValueMap);

        int sum = 0;

        for (String value: input) {
            sum += parseString(value, fullMap);
        }

        return sum;
    }

    private int parseString(String value, Map<String, Integer> lookupTable) {
        Integer firstDigit = null;
        Integer secondDigit = null;

        for (int i = 0; i < value.length(); i++) {
            Optional<Integer> matchvalue = matchString(value, i, lookupTable);
            if (matchvalue.isPresent()) {
                if (firstDigit == null) {
                    firstDigit = matchvalue.get();
                }

                secondDigit = matchvalue.get();
            }
        }

        if (firstDigit == null) {
            return 0;
        }

        return firstDigit * 10 + secondDigit;
    }

    private Optional<Integer> matchString(String input, int index, Map<String, Integer> lookupTable) {
        for (String value: lookupTable.keySet()) {
            if (input.regionMatches(index, value, 0, value.length())) {
                return Optional.of(lookupTable.get(value));
            }
        }

        return Optional.empty();
    }
}
