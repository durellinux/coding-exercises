package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;

public class IfYouGiveASeedAFertilizer {
    private record ProblemEntry(long source, long destination, long length) {}
    private record Interval(long start, long end) {}
    private record MapInterval(long start, long end, long destinationStart) {}

    public long solve(
        List<Interval> seeds,
        List<String> seedsToSoilsInput,
        List<String> soilToFertilizerInput,
        List<String> fertilizerToWaterInput,
        List<String> waterToLightInput,
        List<String> lightToTemperatureInput,
        List<String> temperatureToHumidityInput,
        List<String> humidityToLocationInput
    ) {
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
        List<Interval> locations = doMap(humidities, humidityToLocation);

        return locations.stream()
                .map(v -> v.start)
                .reduce(Long::min).orElse(0L);
    }

    public List<Interval> parseSeeds1(String input) {
        List<Interval> result = new ArrayList<>();
        String[] values = input.split(" ");

        for (String value : values) {
            long start = Long.valueOf(value.strip(), 10);
            result.add(new Interval(start, start));
        }

        return result;
    }

    public List<Interval> parseSeeds2(String input) {
        List<Interval> result = new ArrayList<>();
        String[] values = input.split(" ");

        for (int i = 0; i < values.length; i+=2) {
            long start = Long.valueOf(values[i].strip(), 10);
            long length = Long.valueOf(values[i+1].strip(), 10);
            result.add(new Interval(start, start + length - 1));
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

    private List<Interval> doMap(List<Interval> sources, Map<Long, ProblemEntry> nextMap) {
        List<Interval> nextValues = new ArrayList<>();
        sources.sort(Comparator.comparingLong(i -> i.start));

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

        Iterator<Interval> intervalIterator = sources.iterator();
        Iterator<MapInterval> nextMapValueIterator = allMapIntervals.iterator();

        MapInterval mapValue = nextMapValueIterator.next();
        while(intervalIterator.hasNext()) {
            Interval interval = intervalIterator.next();

            while(interval != null) {

                while(interval.start > mapValue.end) {
                    mapValue = nextMapValueIterator.next();
                }

                interval = generateIntervals(interval, mapValue, nextValues);
            }
        }

        return nextValues;
    }

    Interval generateIntervals(Interval interval, MapInterval mapInterval, List<Interval> result) {
        if (interval.start >= mapInterval.start && interval.end <= mapInterval.end) {
            long startDestination = mapInterval.destinationStart + (interval.start - mapInterval.start);
            long endDestination = startDestination + (interval.end - interval.start);
            result.add(new Interval(startDestination, endDestination));
            return null;
        } else if (interval.start <= mapInterval.end && interval.end > mapInterval.end) {
            long startDestination = mapInterval.destinationStart + (interval.start - mapInterval.start);
            long endDestination = startDestination + (mapInterval.end - interval.start);
            result.add(new Interval(startDestination, endDestination));
            return new Interval(mapInterval.end + 1, interval.end);
        } else {
            throw new UnsupportedOperationException(interval + " - " + mapInterval);
        }
    }
}
