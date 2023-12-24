package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.libraries.geometry.*;
import com.gianlucadurelli.coding.libraries.math.Fraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day24_GeometryFraction {
    private static final Geometry<Fraction> geometry = new GeometryFractionLong();

    public long solve(List<String> input, Day24NeverTellMeTheOdds.TestArea testArea) {
        List<PointWithVelocity<Fraction>> hailstones = parseInput(input);
        long intersectCount = 0;

        for (int h1 = 0; h1 < hailstones.size() - 1; h1++) {
            for (int h2 = h1 + 1; h2 < hailstones.size(); h2++) {
                PointWithVelocity<Fraction> hailstone1 = hailstones.get(h1);
                PointWithVelocity<Fraction> hailstone2 = hailstones.get(h2);
                Optional<Point<Fraction>> intersection = geometry.intersect(hailstone1, hailstone2);
                boolean isValidIntersection = intersection.isPresent() && isWithinBoundaries(intersection.get(), testArea);
                intersectCount += isValidIntersection ? 1 : 0;
            }
        }

        return intersectCount;
    }

    public long solve2(List<String> input, Day24NeverTellMeTheOdds.TestArea testArea) {
        List<PointWithVelocity<Fraction>> hailstones = parseInput(input);

        for (int t = 0; t < hailstones.size(); t++) {
            Point<Fraction> p = hailstones.get(t).p();
            Point<Fraction> v = hailstones.get(t).v();
            System.out.println("x + vx * t" + t + " = " + p.x().asDouble() + " + (" + v.x().asDouble() + ") * t"+t);
            System.out.println("y + vy * t" + t + " = " + p.y().asDouble() + " + (" + v.y().asDouble() + ") * t"+t);
            System.out.println("z + vz * t" + t + " = " + p.z().asDouble() + " + (" + v.z().asDouble() + ") * t"+t);
        }

        return 0;
    }

    private boolean isWithinBoundaries(Point<Fraction> p, Day24NeverTellMeTheOdds.TestArea testArea) {
        double x = p.x().asDouble();
        double y = p.y().asDouble();

        if (x < testArea.minValue() || x > testArea.maxValue() || y < testArea.minValue() || y > testArea.maxValue()) {
            return false;
        }

        return true;
    }

    private List<PointWithVelocity<Fraction>> parseInput(List<String> input) {
        List<PointWithVelocity<Fraction>> pointsWithVelocities = new ArrayList<>();
        for (String line: input) {
            String[] data = line.split(" @ ");
            String[] pointData = data[0].split(",");
            String[] velocityData = data[1].split(",");

            Point<Fraction> p0 = geometry.createPoint(
                    Long.valueOf(pointData[0].strip(), 10),
                    Long.valueOf(pointData[1].strip(), 10),
                    Long.valueOf(pointData[2].strip(), 10)
            );

            Point<Fraction> dp = geometry.createPoint(
                    Long.valueOf(velocityData[0].strip(), 10),
                    Long.valueOf(velocityData[1].strip(), 10),
                    Long.valueOf(velocityData[2].strip(), 10)
            );

            Fraction m = dp.y().divideBy(dp.x());

            Line2DMQ<Fraction> trajectory = geometry.createLine(m, p0);

            pointsWithVelocities.add(new PointWithVelocity<>(p0, dp, trajectory));
        }

        return pointsWithVelocities;
    }
}
