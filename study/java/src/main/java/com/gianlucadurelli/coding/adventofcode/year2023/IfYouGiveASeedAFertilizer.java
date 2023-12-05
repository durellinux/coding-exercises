package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IfYouGiveASeedAFertilizer {

    private record ProblemEntry(long source, long destination, long length) {}
    private record Interval(long start, long end) {}
    private record Point(long value, boolean isInput) {}

    public long solve1(
        String seedsInput,
        List<String> seedsToSoilsInput,
        List<String> soilToFertilizerInput,
        List<String> fertilizerToWaterInput,
        List<String> waterToLightInput,
        List<String> lightToTemperatureInput,
        List<String> temperatureToHumidityInput,
        List<String> humidityToLocationInput
    ) {
        List<Interval> seeds = parseSeeds1(seedsInput);
        Map<Long, ProblemEntry> seedsToSoils = parseInput(seedsToSoilsInput);
        Map<Long, ProblemEntry> soilToFertilizer = parseInput(soilToFertilizerInput);
        Map<Long, ProblemEntry> fertilizerToWater = parseInput(fertilizerToWaterInput);
        Map<Long, ProblemEntry> waterToLight = parseInput(waterToLightInput);
        Map<Long, ProblemEntry> lightToTemperature = parseInput(lightToTemperatureInput);
        Map<Long, ProblemEntry> temperatureToHumidity = parseInput(temperatureToHumidityInput);
        Map<Long, ProblemEntry> humidityToLocation = parseInput(humidityToLocationInput);

        List<Interval> soils = doMap(seeds, seedsToSoils);
        List<Interval> fertilizers = doMap(soils, soilToFertilizer);
        List<Interval> waters = doMap(fertilizers, fertilizerToWater);
        List<Interval> lights = doMap(waters, waterToLight);
        List<Interval> temperatures = doMap(lights, lightToTemperature);
        List<Interval> humidities = doMap(temperatures, temperatureToHumidity);
        List<Long> locations = doMap(humidities, humidityToLocation);

        return locations.stream().reduce(Long::min).orElse(0L);
    }

    public long solve2(
        String seedsInput,
        List<String> seedsToSoilsInput,
        List<String> soilToFertilizerInput,
        List<String> fertilizerToWaterInput,
        List<String> waterToLightInput,
        List<String> lightToTemperatureInput,
        List<String> temperatureToHumidityInput,
        List<String> humidityToLocationInput
    ) {
        List<Interval> seeds = parseSeeds2(seedsInput);
        Map<Long, ProblemEntry> seedsToSoils = parseInput(seedsToSoilsInput);
        Map<Long, ProblemEntry> soilToFertilizer = parseInput(soilToFertilizerInput);
        Map<Long, ProblemEntry> fertilizerToWater = parseInput(fertilizerToWaterInput);
        Map<Long, ProblemEntry> waterToLight = parseInput(waterToLightInput);
        Map<Long, ProblemEntry> lightToTemperature = parseInput(lightToTemperatureInput);
        Map<Long, ProblemEntry> temperatureToHumidity = parseInput(temperatureToHumidityInput);
        Map<Long, ProblemEntry> humidityToLocation = parseInput(humidityToLocationInput);

        List<Interval> soils = doMap(seeds, seedsToSoils);
        List<Interval> fertilizers = doMap(soils, soilToFertilizer);
        List<Interval> waters = doMap(fertilizers, fertilizerToWater);
        List<Interval> lights = doMap(waters, waterToLight);
        List<Interval> temperatures = doMap(lights, lightToTemperature);
        List<Interval> humidities = doMap(temperatures, temperatureToHumidity);
        List<Long> locations = doMap(humidities, humidityToLocation);

        return locations.stream().reduce(Long::min).orElse(0L);
    }

    private List<Interval> parseSeeds1(String input) {
        List<Interval> result = new ArrayList<>();
        String[] values = input.split(" ");

        for (int i = 0; i < values.length; i++) {
            long start = Long.valueOf(values[i].strip(), 10);
            result.add(new Interval(start, start));
        }

        return result;
    }

    private List<Interval> parseSeeds2(String input) {
        List<Interval> result = new ArrayList<>();
        String[] values = input.split(" ");

        for (int i = 0; i < values.length; i+=2) {
            long start = Long.valueOf(values[i].strip(), 10);
            long end = Long.valueOf(values[i+1].strip(), 10);
            result.add(new Interval(start, end));
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

    private List<Long> doMap(List<Interval> sources, Map<Long, ProblemEntry> nextMap) {
//        List<Long> orderedSources = nextMap.keySet().stream().sorted().toList();
        List<Interval> nextValues = new ArrayList<>();
        sources.sort((i1, i2) -> Math.toIntExact(i1.start - i2.start));

        Map<Long, Interval> intervalMap = new HashMap<>();
        for (Interval i: sources) {
            intervalMap.put(i.start, i);
        }

        List<Long> nextMapPoints = new ArrayList<>();
        nextMapPoints.addAll(nextMap.keySet());
        nextMapPoints.sort(Long::compareTo);

        Iterator<Long> nextMapValueIterator = nextMapPoints.iterator();
        Iterator<Interval> intervalIterator = sources.iterator();

        Long lastMapValue = Long.MIN_VALUE;
        Long currentMapValue = nextMapValueIterator.next();

        while(intervalIterator.hasNext()) {
            Interval interval = intervalIterator.next();

            while(interval != null) {
                interval = generateIntervals(interval, lastMapValue, currentMapValue, nextMap, nextValues);

                if (interval != null) {
                    lastMapValue = currentMapValue;
                    currentMapValue = nextMapValueIterator.hasNext() ? nextMapValueIterator.next() : Long.MAX_VALUE;
                }
            }
        }

        for (Point p: points) {
            if (p.isInput == false) {
                lastMapValue = p.value;
            } else {
                if (lastMapValue == null) {
                    Interval i = intervalMap.get(p.value);
                }
            }
        }

        long prev = 0;
        long next = orderedSources.get(0);
        if (sources.get(0).start < orderedSources.get(0)) {

        }

        for (Interval value: sources) {
            Integer minValPosition = null;
            for (int index = 0; index < orderedSources.size(); index++) {
                if (orderedSources.get(index) < value) {
                    minValPosition = index;
                }
            }

            if (minValPosition == null) {
                nextValues.add(value);
            } else {
                Long nextSource = orderedSources.get(minValPosition);
                long nextLength = nextMap.get(nextSource).length;
                long nextDestination = nextMap.get(nextSource).destination;

                if (value - nextSource > nextLength - 1) {
                    nextValues.add(value);
                } else {
                    nextValues.add(nextDestination + value - nextSource);
                }
            }
        }

        return nextValues;
    }

    Interval generateIntervals(Interval interval, long prev, long next, Map<Long, ProblemEntry> nextMap, List<Interval> result) {
        if (interval.end < prev) {

        } else if (interval.start < prev && prev <= interval.end && interval.end < next ) {

        } else if (interval.start == prev && interval.end == next) {

        } else if (prev < interval.start && interval.start < next && next < interval.end) {

        } else if (interval.start == next) {

        } else if (prev <= interval.start && interval.end < next) {

        } else {
            throw new UnsupportedOperationException();
        }

        if (prev != null && intervalEnd <= prev) {
            long nextLength = nextMap.get(prev).length;
            long nextDestination = nextMap.get(prev).destination;

            if (value - nextSource > nextLength - 1) {
                nextValues.add(value);
            } else {
                nextValues.add(nextDestination + value - nextSource);
            }
        }
    }
}
