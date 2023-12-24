package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.libraries.geometry.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day24_GeometryDouble {
    private static final Geometry<Double> geometry = new GeometryDouble();

    public long solve(List<String> input, Day24NeverTellMeTheOdds.TestArea testArea) {
        List<PointWithVelocity<Double>> hailstones = parseInput(input);
        long intersectCount = 0;

        for (int h1 = 0; h1 < hailstones.size() - 1; h1++) {
            for (int h2 = h1 + 1; h2 < hailstones.size(); h2++) {
                PointWithVelocity<Double> hailstone1 = hailstones.get(h1);
                PointWithVelocity<Double> hailstone2 = hailstones.get(h2);
                Optional<Point<Double>> intersection = geometry.intersect(hailstone1, hailstone2);
//                System.out.println(intersection.map(Point::toString).orElse("No interection"));
                boolean isValidIntersection = intersection.isPresent() && isWithinBoundaries(intersection.get(), testArea);
                if (isValidIntersection) {
                    System.out.println(intersection.get());
                }
                intersectCount += isValidIntersection ? 1 : 0;
            }
        }

        return intersectCount;
    }

    private boolean isWithinBoundaries(Point<Double> p, Day24NeverTellMeTheOdds.TestArea testArea) {
        double x = p.x();
        double y = p.y();

        if (x < testArea.minValue() || x > testArea.maxValue() || y < testArea.minValue() || y > testArea.maxValue()) {
            return false;
        }

        return true;
    }

    private List<PointWithVelocity<Double>> parseInput(List<String> input) {
        List<PointWithVelocity<Double>> pointsWithVelocities = new ArrayList<>();
        for (String line: input) {
            String[] data = line.split(" @ ");
            String[] pointData = data[0].split(",");
            String[] velocityData = data[1].split(",");

            Point<Double> p0 = geometry.createPoint(
                    Long.valueOf(pointData[0].strip(), 10),
                    Long.valueOf(pointData[1].strip(), 10),
                    Long.valueOf(pointData[2].strip(), 10)
            );

            Point<Double> dp = geometry.createPoint(
                    Long.valueOf(velocityData[0].strip(), 10),
                    Long.valueOf(velocityData[1].strip(), 10),
                    Long.valueOf(velocityData[2].strip(), 10)
            );

            Double m = dp.y() / dp.x();

            Line2DMQ<Double> trajectory = geometry.createLine(m, p0);

            pointsWithVelocities.add(new PointWithVelocity<>(p0, dp, trajectory));
        }

        return pointsWithVelocities;
    }
}
