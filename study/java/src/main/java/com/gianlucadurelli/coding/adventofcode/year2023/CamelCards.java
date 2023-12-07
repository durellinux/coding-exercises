package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;

public class CamelCards {
    List<Character> symbols1 = List.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
    List<Character> symbols2 = List.of('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A');

    enum HandType {
        FIVE_OF_A_KIND(6),
        FOUR_OF_A_KIND(5),
        FULL_HOUSE(4),
        THREE_OF_A_KIND(3),
        TWO_PAIR(2),
        ONE_PAIR(1),
        HIGH_CARD(0);

        final int val;

        HandType(int val) {
            this.val = val;
        }
    }

    private record CamelCardsData(String hand, HandType type, int bid) {}

    public long solve(List<String> input, int version) {
        long result = 0;

        List<CamelCardsData> data = new ArrayList<>(parseInput(input, version));
        data.sort((a, b) -> this.handComparator(a, b, version));

        int hands = input.size();
        for(int i = 1; i <= hands; i++) {
            result = result + (long) data.get(i - 1).bid * i;
        }

        return result;
    }

    private List<CamelCardsData> parseInput(List<String> input, int version) {
        return input.stream().map(
                i -> {
                    String[] vals = i.split(" ");
                    HandType type = version == 1 ? computeHandType1(vals[0]) : computeHandType2(vals[0]);
                    return new CamelCardsData(vals[0], type, Integer.valueOf(vals[1], 10));
                }
        ).toList();
    }

    private HandType computeHandType1(String hand) {
        Map<Character, Integer> occurrences = new HashMap<>();
        Map<Integer, Integer> equalityMap = new HashMap<>();

        for (char c: hand.toCharArray()) {
            occurrences.put(c, occurrences.getOrDefault(c, 0) + 1);
        }

        for (int value: occurrences.values()) {
            equalityMap.put(value, equalityMap.getOrDefault(value, 0) + 1);
        }

        return getHandType(equalityMap);
    }

    private HandType computeHandType2(String hand) {
        Map<Character, Integer> occurrences = new HashMap<>();
        Map<Integer, Integer> equalityMap = new HashMap<>();

        int jokers = 0;

        for (char c: hand.toCharArray()) {
            if (c != 'J') {
                occurrences.put(c, occurrences.getOrDefault(c, 0) + 1);
            } else {
                jokers++;
            }
        }

        char mostOccurrences = 'A';
        for (char c: occurrences.keySet()) {
            if (occurrences.getOrDefault(c, 0) > occurrences.getOrDefault(mostOccurrences, 0)) {
                mostOccurrences = c;
            }
        }
        occurrences.put(mostOccurrences, occurrences.getOrDefault(mostOccurrences, 0) + jokers);

        for (int value: occurrences.values()) {
            equalityMap.put(value, equalityMap.getOrDefault(value, 0) + 1);
        }

        return getHandType(equalityMap);
    }



    private int tieBreaker(String hand1, String hand2, List<Character> symbols) {
        char[] chars1 = hand1.toCharArray();
        char[] chars2 = hand2.toCharArray();

        for (int i = 0; i < chars1.length; i++) {
            if (symbols.indexOf(chars1[i]) < symbols.indexOf(chars2[i])) {
                return -1;
            } else if (symbols.indexOf(chars1[i]) > symbols.indexOf(chars2[i])) {
                return 1;
            }
        }

        return 0;
    }

    private HandType getHandType(Map<Integer, Integer> equalityMap) {
        HandType type = HandType.HIGH_CARD;
        if (equalityMap.getOrDefault(5, 0) == 1) {
            type = HandType.FIVE_OF_A_KIND;
        } else if (equalityMap.getOrDefault(4, 0) == 1) {
            type = HandType.FOUR_OF_A_KIND;
        } else if (equalityMap.getOrDefault(3, 0) == 1 && equalityMap.getOrDefault(2, 0) == 1) {
            type = HandType.FULL_HOUSE;
        } else if (equalityMap.getOrDefault(3, 0) == 1) {
            type = HandType.THREE_OF_A_KIND;
        } else if (equalityMap.getOrDefault(2, 0) == 2) {
            type = HandType.TWO_PAIR;
        } else if (equalityMap.getOrDefault(2, 0) == 1) {
            type = HandType.ONE_PAIR;
        }

        return type;
    }

    private int handComparator(CamelCardsData a, CamelCardsData b, int version) {
        List<Character> symbols = version == 1 ? symbols1 : symbols2;
        int scoreComparison = Integer.compare(a.type.val, b.type.val);
        if (scoreComparison != 0) {
            return scoreComparison;
        }

        return tieBreaker(a.hand, b.hand, symbols);
    }
}
