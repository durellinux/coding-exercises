package com.gianlucadurelli.coding.adventofcode.year2023;


import java.util.*;
public class Day24NeverTellMeTheOdds {
    private static final Day24_GeometryFraction geometryFraction = new Day24_GeometryFraction();

    public record TestArea(long minValue, long maxValue) {
        public static TestArea example() {
            return new TestArea(7, 27);
        }

        public static TestArea real() {
            return new TestArea(200000000000000L, 400000000000000L);
        }
    }

    public long solve(List<String> input, TestArea testArea) {
        long solutionGeometryFraction = geometryFraction.solve(input, testArea);
        System.out.println(solutionGeometryFraction);
        return solutionGeometryFraction;
    }

    public long solve2(List<String> input, TestArea testArea) {
        long solutionGeometryFraction = geometryFraction.solve2(input, testArea);
        return solutionGeometryFraction;
    }

}