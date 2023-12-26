package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.libraries.geometry.GeometryLib;
import com.gianlucadurelli.coding.libraries.geometry.Line2DMQ;
import com.gianlucadurelli.coding.libraries.geometry.Point;
import com.gianlucadurelli.coding.libraries.geometry.PointWithVelocity;
import com.gianlucadurelli.coding.libraries.math.Fraction;
import com.gianlucadurelli.coding.libraries.math.NumberFactory;

import java.util.*;
public class Day24NeverTellMeTheOdds {
    private static final GeometryLib geometry = new GeometryLib();

    public record TestArea(long minValue, long maxValue) {
        public static TestArea example() {
            return new TestArea(7, 27);
        }

        public static TestArea real() {
            return new TestArea(200000000000000L, 400000000000000L);
        }
    }

    public long solve(List<String> input, TestArea testArea) {
        List<PointWithVelocity> hailstones = parseInput(input);
        long intersectCount = 0;

        for (int h1 = 0; h1 < hailstones.size() - 1; h1++) {
            for (int h2 = h1 + 1; h2 < hailstones.size(); h2++) {
                PointWithVelocity hailstone1 = hailstones.get(h1);
                PointWithVelocity hailstone2 = hailstones.get(h2);
                Optional<Point> intersection = geometry.intersect(hailstone1, hailstone2);
                boolean isValidIntersection = intersection.isPresent() && isWithinBoundaries(intersection.get(), testArea);
                intersectCount += isValidIntersection ? 1 : 0;
            }
        }

        return intersectCount;
    }

    public long solve2(List<String> input, TestArea testArea) {
        List<PointWithVelocity> hailstones = parseInput(input);

        for (int t = 0; t < hailstones.size(); t++) {
            Point p = hailstones.get(t).p();
            Point v = hailstones.get(t).v();
            System.out.println("x + vx * t" + t + " = " + p.x().asDouble() + " + (" + v.x().asDouble() + ") * t"+t);
            System.out.println("y + vy * t" + t + " = " + p.y().asDouble() + " + (" + v.y().asDouble() + ") * t"+t);
            System.out.println("z + vz * t" + t + " = " + p.z().asDouble() + " + (" + v.z().asDouble() + ") * t"+t);
        }

        return 0;
    }

    private boolean isWithinBoundaries(Point p, Day24NeverTellMeTheOdds.TestArea testArea) {
        double x = p.x().asDouble();
        double y = p.y().asDouble();

        if (x < testArea.minValue() || x > testArea.maxValue() || y < testArea.minValue() || y > testArea.maxValue()) {
            return false;
        }

        return true;
    }

    private List<PointWithVelocity> parseInput(List<String> input) {
        List<PointWithVelocity> pointsWithVelocities = new ArrayList<>();
        for (String line: input) {
            String[] data = line.split(" @ ");
            String[] pointData = data[0].split(",");
            String[] velocityData = data[1].split(",");

            Point p0 = geometry.createPoint(
                    NumberFactory.create(Long.valueOf(pointData[0].strip(), 10), Fraction.class),
                    NumberFactory.create(Long.valueOf(pointData[1].strip(), 10), Fraction.class),
                    NumberFactory.create(Long.valueOf(pointData[2].strip(), 10), Fraction.class)
            );

            Point dp = geometry.createPoint(
                    NumberFactory.create(Long.valueOf(velocityData[0].strip(), 10), Fraction.class),
                    NumberFactory.create(Long.valueOf(velocityData[1].strip(), 10), Fraction.class),
                    NumberFactory.create(Long.valueOf(velocityData[2].strip(), 10), Fraction.class)
            );

            Fraction dpy = dp.y().as();
            Fraction m = dpy.divideBy(dp.x());

            Line2DMQ trajectory = geometry.createLine(m, p0);

            pointsWithVelocities.add(new PointWithVelocity(p0, dp, trajectory));
        }

        return pointsWithVelocities;
    }

}