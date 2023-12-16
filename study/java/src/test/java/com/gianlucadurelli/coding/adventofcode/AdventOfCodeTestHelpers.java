package com.gianlucadurelli.coding.adventofcode;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AdventOfCodeTestHelpers {

    @SneakyThrows
    private static List<String> getInput(String fileName) {
        System.out.println("Loading " + fileName);

        List<String> input = new ArrayList<>();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(fileName);
        assert inputStream != null;
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        for (String line; (line = reader.readLine()) != null;) {
            input.add(line);
        }

        return input;
    }

    public static List<String> loadWithSuffix(Class clazz, String suffix) {
        String[] packageParts = clazz.getName().split("\\.");
        String fileName = packageParts[packageParts.length - 1] + suffix;

        return getInput(fileName);
    }

    public static List<String> loadInput(Class clazz) {
        return loadWithSuffix(clazz, ".txt");
    }

    public static List<String> loadTestInput(Class clazz) {
        return loadWithSuffix(clazz,"-test.txt");
    }
}
