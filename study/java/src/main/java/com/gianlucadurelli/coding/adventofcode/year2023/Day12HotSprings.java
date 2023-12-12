package com.gianlucadurelli.coding.adventofcode.year2023;

import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;

import java.util.*;

public class Day12HotSprings {

    private record Entry(String brokenRecordString, char[] brokenRecord, List<Integer> numericRecord) {}
    private record MemoizationKey(int currentIndex, int currentSegment) {}

    public long solve(List<String> input) {
        return solve2(input, 1);
    }

    public long solve2(List<String> input, int expansionFactor) {
        List<Entry> records = parseInput(input);

        long totalArrangements = 0;
        for (Entry record: records) {
            long thisArrangements = computeArrangements(expandRecord(record, expansionFactor));
            totalArrangements += thisArrangements;
        }

        return totalArrangements;
    }

    private List<Entry> parseInput(List<String> input) {
        List<Entry> records = new ArrayList<>();
        for (String value: input) {
            String[] data = value.split(" ");
            String brokenRecord = data[0];
            List<Integer> numericRecord = Arrays.stream(data[1].split(",")).map(Integer::valueOf).toList();
            records.add(new Entry(brokenRecord, brokenRecord.toCharArray(), numericRecord));
        }

        return records;
    }

    private Entry expandRecord(Entry record, int expansionFactor) {
        StringBuilder brokenRecordExpanded = new StringBuilder(".");
        for (int i = 0; i < expansionFactor - 1; i++) {
            brokenRecordExpanded.append(record.brokenRecordString).append("?");
        }
        brokenRecordExpanded.append(record.brokenRecordString).append(".");

        List<Integer> numericRecordExpanded = new ArrayList<>();
        for (int i = 0; i < expansionFactor; i++) {
            numericRecordExpanded.addAll(record.numericRecord);
        }

        return new Entry(brokenRecordExpanded.toString(), brokenRecordExpanded.toString().toCharArray(), numericRecordExpanded);
    }

    private long computeArrangements(Entry record) {
        List<Set<Integer>> possiblePositions = new ArrayList<>();
        List<Integer> skipLength = new ArrayList<>();

        record.numericRecord.forEach(v -> {
            String brokenStringToMatch = brokenStringOfLength(v);
            possiblePositions.add(findAllPositions(record.brokenRecordString, brokenStringToMatch));
            skipLength.add(brokenStringToMatch.length());
        });
        Set<Integer> dotPositions = getDotPositions(record.brokenRecord);

        Map<MemoizationKey, Long> cache = new HashMap<>();
        return computeArrangements(possiblePositions, dotPositions, skipLength, 0, 0, record.brokenRecordString.length(), cache);
    }

    private Set<Integer> getDotPositions(char[] brokenRecord) {
        Set<Integer> dotPositions = new HashSet<>();

        for (int i = 0; i < brokenRecord.length; i++) {
            if (brokenRecord[i] != '#') {
                dotPositions.add(i);
            }
        }

        return dotPositions;
    }

    private String brokenStringOfLength(int n) {
        return "." + ("#".repeat(Math.max(0, n))) + ".";
    }

    private Set<Integer> findAllPositions(String brokenRecordString, String value) {
        Set<Integer> positions = new HashSet<>();
        char[] brokenRecord = brokenRecordString.toCharArray();
        char[] valueChars = value.toCharArray();

        for (int index = 0; index < brokenRecord.length; index++) {
            if (isValidPosition(index, brokenRecord, valueChars)) {
                positions.add(index);
            }
        }

        return positions;
    }

    private boolean isValidPosition(int index, char[] brokenRecord, char[] value) {
        for (int i = 0; i < value.length; i++) {
            int brokenRecordIndex = i + index;

            if (brokenRecordIndex >= brokenRecord.length) {
                return false;
            }

            char v = value[i];
            char b = brokenRecord[brokenRecordIndex];

            if ((v == '.' && b == '#') || (v == '#' && b == '.')) {
                return false;
            }
        }

        return true;
    }

    public long computeArrangements(List<Set<Integer>> startingPositions, Set<Integer> dotPositions, List<Integer> skipLength, int currentIndex, int currentSegment, int targetIndex, Map<MemoizationKey, Long> cache) {
        if (currentIndex == targetIndex) {
            return currentSegment == startingPositions.size() ? 1 : 0;
        }

        MemoizationKey cacheKey = new MemoizationKey(currentIndex, currentSegment);

        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        long withThisAsDot = 0;
        if (dotPositions.contains(currentIndex)) {
            withThisAsDot = computeArrangements(startingPositions, dotPositions, skipLength, currentIndex + 1, currentSegment, targetIndex, cache);
        }

        long withThisAsMatch = 0;
        if (currentSegment < startingPositions.size() && startingPositions.get(currentSegment).contains(currentIndex)) {
            withThisAsMatch = computeArrangements(startingPositions, dotPositions, skipLength, currentIndex + skipLength.get(currentSegment) - 1, currentSegment + 1, targetIndex, cache);
        }

        long solution = withThisAsDot + withThisAsMatch;
        cache.put(cacheKey, solution);

        return solution;
    }
}
