package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IfYouGiveASeedAFertilizer {

    private record ProblemEntry(long source, long destination, long length) {}

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
        List<Long> seeds = Arrays.stream(seedsInput.split(" ")).map(Long::valueOf).collect(Collectors.toList());
        Map<Long, ProblemEntry> seedsToSoils = parseInput(seedsToSoilsInput);
        Map<Long, ProblemEntry> soilToFertilizer = parseInput(soilToFertilizerInput);
        Map<Long, ProblemEntry> fertilizerToWater = parseInput(fertilizerToWaterInput);
        Map<Long, ProblemEntry> waterToLight = parseInput(waterToLightInput);
        Map<Long, ProblemEntry> lightToTemperature = parseInput(lightToTemperatureInput);
        Map<Long, ProblemEntry> temperatureToHumidity = parseInput(temperatureToHumidityInput);
        Map<Long, ProblemEntry> humidityToLocation = parseInput(humidityToLocationInput);

        List<Long> soils = doMap(seeds, seedsToSoils);
        List<Long> fertilizers = doMap(soils, soilToFertilizer);
        List<Long> waters = doMap(fertilizers, fertilizerToWater);
        List<Long> lights = doMap(waters, waterToLight);
        List<Long> temperatures = doMap(lights, lightToTemperature);
        List<Long> humidities = doMap(temperatures, temperatureToHumidity);
        List<Long> locations = doMap(humidities, humidityToLocation);

        return locations.stream().reduce(Long::min).orElse(0L);
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

    private List<Long> doMap(List<Long> sources, Map<Long, ProblemEntry> nextMap) {
        List<Long> orderedSources = nextMap.keySet().stream().sorted().toList();
        List<Long> nextValues = new ArrayList<>();

        for (Long value: sources) {
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
}
