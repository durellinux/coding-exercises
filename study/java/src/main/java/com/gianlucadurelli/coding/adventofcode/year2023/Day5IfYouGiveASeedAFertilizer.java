package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.libraries.math.Interval;
import com.gianlucadurelli.coding.libraries.math.LongPrecision;

import java.util.*;
import java.util.function.Function;

public class Day5IfYouGiveASeedAFertilizer {
    private record ProblemEntry(long source, long destination, long length) {}
    public record MyInterval(long start, long end) {
        public Interval asInterval() {
            return new Interval(new LongPrecision(start), new LongPrecision(end));
        }
    }
    private record MapInterval(long start, long end, long destinationStart) {
        public Interval asInterval() {
            return new Interval(new LongPrecision(start), new LongPrecision(end));
        }
    }

    public long solve(List<String> input, Function<String, List<MyInterval>> seedParser) {
        List<MyInterval> seeds = seedParser.apply(input.get(0));
        List<List<String>> almanacs = parseOtherLists(input.subList(3, input.size()));

        return solve(
                seeds,
                almanacs
        );
    }

    public long solve(
        List<MyInterval> seeds,
        List<List<String>> almanacs
    ) {
        List<MyInterval> myIntervals = seeds;
        for (List<String> almanacString: almanacs) {
            Map<Long, ProblemEntry> almanacMap = parseInput(almanacString);
            myIntervals = doMap(myIntervals, almanacMap);
        }

        return myIntervals.stream()
                .map(v -> v.start)
                .reduce(Long::min).orElse(0L);
    }

    private List<List<String>> parseOtherLists(List<String> subList) {
        List<List<String>> lists = new ArrayList<>();
        List<String> currentList = new ArrayList<>();
        int i = 0;
        while (i < subList.size()) {
            String value = subList.get(i);

            if (value.isEmpty()) {
                lists.add(currentList);
                currentList = new ArrayList<>();
                i += 2;
            } else {
                currentList.add(value);
                i += 1;
            }
        }

        lists.add(currentList);
        return lists;
    }


    public List<MyInterval> parseSeeds1(String input) {
        List<MyInterval> result = new ArrayList<>();
        String[] values = input.split(": ")[1].split(" ");

        for (String value : values) {
            long start = Long.valueOf(value.strip(), 10);
            result.add(new MyInterval(start, start));
        }

        return result;
    }

    public List<MyInterval> parseSeeds2(String input) {
        List<MyInterval> result = new ArrayList<>();
        String[] values = input.split(": ")[1].split(" ");

        for (int i = 0; i < values.length; i+=2) {
            long start = Long.valueOf(values[i].strip(), 10);
            long length = Long.valueOf(values[i+1].strip(), 10);
            result.add(new MyInterval(start, start + length - 1));
        }

        return result;
    }

    private Map<Long, ProblemEntry> parseInput(List<String> input) {
        Map<Long, ProblemEntry> thisMap = new HashMap<>();
        for (String value: input) {
            String[] data = value.split(" ");
            long destination = Long.valueOf(data[0].strip(), 10);
            long source = Long.valueOf(data[1].strip(), 10);
            long length = Long.valueOf(data[2].strip(), 10);
            thisMap.put(source, new ProblemEntry(source, destination, length));
        }

        return thisMap;
    }

    private List<MyInterval> doMap(List<MyInterval> sources, Map<Long, ProblemEntry> nextMap) {
        List<MyInterval> nextValues = new ArrayList<>();

        sources.sort(Comparator.comparingLong(i -> i.start));
        List<MapInterval> allMapIntervals = generateAllMapIntervals(nextMap);

        Iterator<MyInterval> intervalIterator = sources.iterator();
        Iterator<MapInterval> nextMapValueIterator = allMapIntervals.iterator();

        MapInterval mapValue = nextMapValueIterator.next();
        while(intervalIterator.hasNext()) {
            MyInterval myInterval = intervalIterator.next();

            while(myInterval != null) {

                while(myInterval.asInterval().isAfter(mapValue.asInterval())) {
                    mapValue = nextMapValueIterator.next();
                }

                myInterval = intersectIntervals(myInterval, mapValue, nextValues);
            }
        }

        return nextValues;
    }

    private List<MapInterval> generateAllMapIntervals(Map<Long, ProblemEntry> nextMap) {
        List<MapInterval> tmpIntervals = new ArrayList<>(nextMap.values().stream().map(v -> new MapInterval(v.source, v.source + v.length - 1, v.destination)).toList());
        tmpIntervals.sort(Comparator.comparingLong(i -> i.start));

        List<MapInterval> allMapIntervals = new ArrayList<>();
        allMapIntervals.add(new MapInterval(Long.MIN_VALUE, tmpIntervals.get(0).start - 1, Long.MIN_VALUE));
        for (MapInterval tmpInterval : tmpIntervals) {
            if (allMapIntervals.get(allMapIntervals.size() - 1).end < tmpInterval.start - 1) {
                allMapIntervals.add(new MapInterval(
                        allMapIntervals.get(allMapIntervals.size() - 1).end + 1,
                        tmpInterval.start - 1,
                        allMapIntervals.get(allMapIntervals.size() - 1).end + 1
                ));
            }
            allMapIntervals.add(tmpInterval);
        }
        allMapIntervals.add(new MapInterval(tmpIntervals.get(tmpIntervals.size() - 1).end + 1, Long.MAX_VALUE, tmpIntervals.get(tmpIntervals.size() - 1).end + 1));

        return allMapIntervals;
    }

    MyInterval intersectIntervals(MyInterval myInterval, MapInterval mapInterval, List<MyInterval> result) {
        Optional<Interval> optionalIntersection = myInterval.asInterval().intersect(mapInterval.asInterval());

        if (optionalIntersection.isEmpty()) {
            throw new UnsupportedOperationException(myInterval + " - " + mapInterval);
        }

        Interval intersection = optionalIntersection.get();
        long startDestination = mapInterval.destinationStart + (myInterval.start - mapInterval.start);
        long endDestination = startDestination + intersection.length().as(LongPrecision.class).value() - 1;
        result.add(new MyInterval(startDestination, endDestination));

        if (!intersection.equals(myInterval.asInterval())) {
            return new MyInterval(mapInterval.end + 1, myInterval.end);
        }

        return null;
    }
}
