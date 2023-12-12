package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day12HotSprings {

    private record Entry(String brokenRecordString, char[] brokenRecord, List<Integer> numericRecord) {}

    public long solve(List<String> input) {
        List<Entry> records = parseInput(input);

        long totalArrangements = 0;
        for (Entry record: records) {
            totalArrangements += computeArrangements(record.brokenRecord, record.numericRecord, 0, 0, 0, false, false);
        }

        return totalArrangements;
    }

    private long computeArrangements(Entry record) {
        List<List<Integer>> possiblePositions = new ArrayList<>();

        String fullBroken = record.brokenRecordString.replace('?', '#');
        record.numericRecord.forEach(v -> {
            possiblePositions.add(findAllPositions(fullBroken, brokenStringOfLength(v)));
        });

        return 0;
    }

    private String brokenStringOfLength(int n) {
        return "#".repeat(Math.max(0, n));
    }

    private List<Integer> findAllPositions(String fullBrokenRecord, String value) {
        List<Integer> positions = new ArrayList<>();
        int index = 0;
        while(index != -1 && index < value.length()) {
            index = fullBrokenRecord.indexOf(value, index);
            if (index != -1) {
                positions.add(index);
                index = index + value.length() + 1;
            }
        }

        return positions;
    }

    private long computeArrangements(char[] brokenRecord, List<Integer> numericRecord, int brokenIndex, int numericIndex, int matched, boolean allMatched, boolean avoidMatch) {
        if (brokenIndex >= brokenRecord.length) {
            return allMatched ? 1 : 0;
        }

        char curChar = brokenRecord[brokenIndex];
        if (curChar == '.') {
            if (matched > 0) {
                return 0;
            } else {
                return computeArrangements(brokenRecord, numericRecord, brokenIndex + 1, numericIndex, matched, allMatched, false);
            }
        }

        if (curChar == '#') {
            if (allMatched || avoidMatch) {
                return 0;
            } else {
                matched++;
                if (matched == numericRecord.get(numericIndex)) {
                    matched = 0;
                    numericIndex++;
                    if (numericIndex == numericRecord.size()) {
                        allMatched=true;
                    }
                    avoidMatch = true;
                }
                return computeArrangements(brokenRecord, numericRecord, brokenIndex + 1, numericIndex, matched, allMatched, avoidMatch);
            }
        }

        if (curChar == '?') {
            long okValue = 0;
            if (matched == 0) {
                okValue = computeArrangements(brokenRecord, numericRecord, brokenIndex + 1, numericIndex, matched, allMatched, false);
            }

            long matchedValue = 0;
            if (!allMatched && !avoidMatch) {
                matched++;
                if (matched == numericRecord.get(numericIndex)) {
                    matched = 0;
                    numericIndex++;
                    if (numericIndex == numericRecord.size()) {
                        allMatched = true;
                    }
                    avoidMatch = true;
                }
                matchedValue = computeArrangements(brokenRecord, numericRecord, brokenIndex + 1, numericIndex, matched, allMatched, avoidMatch);
            }
            return okValue + matchedValue;
        }

        return 0;
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
}
