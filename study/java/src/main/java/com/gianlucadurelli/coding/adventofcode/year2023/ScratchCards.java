package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.ArrayList;
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

    public long solve2(List<String> input) {
        List<Integer> matchesPerCard = new ArrayList<>();

        for (String card: input) {
            List<List<Integer>> values = parseInput(card);
            Set<Integer> winningNumbers = new HashSet<>(values.get(0));
            Set<Integer> myNumbers = new HashSet<>(values.get(1));
            myNumbers.retainAll(winningNumbers);

            matchesPerCard.add(myNumbers.size());
        }


        Long[] numberOfCards = new Long[matchesPerCard.size()];

        for(int c = 0; c < matchesPerCard.size(); c++) {
            numberOfCards[c] = 1L;
        }

        for(int c = 0; c < matchesPerCard.size(); c++) {
            int matches = matchesPerCard.get(c);
            if (matches > 0) {
                for (int c1 = 1; c1 < matches + 1; c1++) {
                    numberOfCards[c + c1] += numberOfCards[c];
                }
            }
        }

        long result = 0;
        for (Long cards: numberOfCards) {
            result += cards;
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
