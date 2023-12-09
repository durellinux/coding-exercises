package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day9MirageMaintenance {

    public long solve1(List<String> inputs) {
        long result = 0;

        for (String input: inputs) {
            List<Integer> values = parseInput(input);
            result += solveOne(values);
        }

        return result;
    }

    public long solve2(List<String> inputs) {
        long result = 0;

        for (String input: inputs) {
            List<Integer> values = new ArrayList<>(parseInput(input));
            Collections.reverse(values);
            result += solveOne(values);
        }

        return result;
    }

    private List<Integer> parseInput(String input) {
        return Arrays.stream(input.split(" ")).map(String::strip).map(Integer::valueOf).toList();
    }

    private long solveOne(List<Integer> values) {
        List<Integer> lastValues = new ArrayList<>();
        List<Integer> tmp = values;
        while (!done(tmp)) {
            List<Integer> newValues = new ArrayList<>();
            for (int i = 1; i < tmp.size(); i++) {
                newValues.add(tmp.get(i) - tmp.get(i-1));
            }

            lastValues.add(tmp.get(tmp.size() - 1));
            tmp = newValues;
        }

        return lastValues.stream().reduce(Integer::sum).orElse(0);
    }

    private boolean done(List<Integer> values) {
        return values.stream().allMatch(v -> v.equals(0));
    }
}
