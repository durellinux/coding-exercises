package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.libraries.geometry.*;
import com.gianlucadurelli.coding.libraries.math.DoublePrecision;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day24_GeometryDoublePrecision {
    private static final Geometry<DoublePrecision> geometry = new GeometryDoublePrecision();

    public long solve(List<String> input, Day24NeverTellMeTheOdds.TestArea testArea) {
        List<PointWithVelocity<DoublePrecision>> hailstones = parseInput(input);
        long intersectCount = 0;

        for (int h1 = 0; h1 < hailstones.size() - 1; h1++) {
            for (int h2 = h1 + 1; h2 < hailstones.size(); h2++) {
                PointWithVelocity<DoublePrecision> hailstone1 = hailstones.get(h1);
                PointWithVelocity<DoublePrecision> hailstone2 = hailstones.get(h2);
                Optional<Point<DoublePrecision>> intersection = geometry.intersect(hailstone1, hailstone2);
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

    private boolean isWithinBoundaries(Point<DoublePrecision> p, Day24NeverTellMeTheOdds.TestArea testArea) {
        double x = p.x().asDouble();
        double y = p.y().asDouble();

        if (x < testArea.minValue() || x > testArea.maxValue() || y < testArea.minValue() || y > testArea.maxValue()) {
            return false;
        }

        return true;
    }

    private List<PointWithVelocity<DoublePrecision>> parseInput(List<String> input) {
        List<PointWithVelocity<DoublePrecision>> pointsWithVelocities = new ArrayList<>();
        for (String line: input) {
            String[] data = line.split(" @ ");
            String[] pointData = data[0].split(",");
            String[] velocityData = data[1].split(",");

            Point<DoublePrecision> p0 = geometry.createPoint(
                    Long.valueOf(pointData[0].strip(), 10),
                    Long.valueOf(pointData[1].strip(), 10),
                    Long.valueOf(pointData[2].strip(), 10)
            );

            Point<DoublePrecision> dp = geometry.createPoint(
                    Long.valueOf(velocityData[0].strip(), 10),
                    Long.valueOf(velocityData[1].strip(), 10),
                    Long.valueOf(velocityData[2].strip(), 10)
            );

            DoublePrecision m = dp.y().divideBy(dp.x());

            Line2DMQ<DoublePrecision> trajectory = geometry.createLine(m, p0);

            pointsWithVelocities.add(new PointWithVelocity<>(p0, dp, trajectory));
        }

        return pointsWithVelocities;
    }
}
