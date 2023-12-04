package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ScratchCards {
    public long solve1(List<String> input) {
        long result = 0;

        for (String card: input) {
            List<List<Integer>> values = parseInput(card);
            Set<Integer> winningNumbers = new HashSet<>(values.get(0));
            Set<Integer> myNumbers = new HashSet<>(values.get(1));
            myNumbers.retainAll(winningNumbers);

            if (myNumbers.size() > 0) {
                result += (1L << (myNumbers.size() - 1));
            }
        }

        return result;
    }

    private List<List<Integer>> parseInput(String input) {
        String[] data = input.split(":");
        String[] numbers = data[1].strip().split("\\|");
        List<Integer> winningNumbers = Arrays.stream(numbers[0].strip().split(" "))
            .map(String::strip)
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .collect(Collectors.toList());

        List<Integer> myNumbers = Arrays.stream(numbers[1].strip().split(" "))
            .map(String::strip)
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .collect(Collectors.toList());

        return List.of(winningNumbers, myNumbers);
    }
}
