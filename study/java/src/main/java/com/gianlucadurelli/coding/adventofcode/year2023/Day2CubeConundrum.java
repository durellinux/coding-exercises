package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://adventofcode.com/2023/day/2
public class Day2CubeConundrum {
    public int solve1(List<String> input, Map<String, Integer> configuration) {
        int result = 0;

        for (String game: input) {
            String[] data = game.strip().split(":");
            String[] gameNumberStr = data[0].strip().split(" ");
            int gameNumber = Integer.valueOf(gameNumberStr[1].strip(), 10);
            String[] gameSetsString = data[1].strip().split(";");

            if (isGamePossible(gameSetsString, configuration)) {
                result += gameNumber;
            }
        }

        return result;
    }

    private boolean isGamePossible(String[] gameSetsString, Map<String, Integer> configuration) {
        for (String setString: gameSetsString) {
            String[] colorsPickString = setString.strip().split(",");
            for (String colorPickString: colorsPickString) {
                String[] colorValues = colorPickString.strip().split(" ");
                int value = Integer.valueOf(colorValues[0].strip(), 10);
                String color = colorValues[1].strip();
                if (value > configuration.getOrDefault(color, 0)) {
                    return false;
                }
            }
        }

        return true;
    }

    public int solve2(List<String> input) {
        int result = 0;

        for (String game: input) {
            String[] data = game.strip().split(":");
            String[] gameNumberStr = data[0].strip().split(" ");
            int gameNumber = Integer.valueOf(gameNumberStr[1].strip(), 10);
            String[] gameSetsString = data[1].strip().split(";");

            Map<String, Integer> configuration = minConfiguration(gameSetsString);
            int powerSet = configuration.values().stream().reduce(1, (Integer a, Integer b) -> a * b);
            result += powerSet;
        }

        return result;
    }

    private Map<String, Integer> minConfiguration(String[] gameSetsString) {
        Map<String, Integer> configuration = new HashMap<>();

        for (String setString: gameSetsString) {
            String[] colorsPickString = setString.strip().split(",");
            for (String colorPickString: colorsPickString) {
                String[] colorValues = colorPickString.strip().split(" ");
                int value = Integer.valueOf(colorValues[0].strip(), 10);
                String color = colorValues[1].strip();
                if (value > configuration.getOrDefault(color, 0)) {
                    configuration.put(color, value);
                }
            }
        }

        return configuration;
    }
}
