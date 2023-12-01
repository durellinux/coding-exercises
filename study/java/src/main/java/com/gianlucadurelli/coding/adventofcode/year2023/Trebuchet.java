package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.List;

// https://adventofcode.com/2023/day/1
public class Trebuchet {
    public int solve(List<String> input) {
        int sum = 0;

        for (String value: input) {
            sum += parseString(value);
        }

        return sum;
    }

    private int parseString(String value) {
        Integer firstDigit = null;
        Integer secondDigit = null;

        for (char c: value.toCharArray()) {
            if (c >= '0' && c <='9') {
                if (firstDigit == null) {
                    firstDigit = c - '0';
                }

                secondDigit = c - '0';
            }
        }

        if (firstDigit == null) {
            return 0;
        }

        return firstDigit * 10 + secondDigit;
    }
}
