package com.gianlucadurelli.coding.adventofcode.year2023;

import com.gianlucadurelli.coding.libraries.geometry.TrueDouble.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day24_TrueDouble {
    public long solve(List<String> input, Day24NeverTellMeTheOdds.TestArea testArea) {
        List<PointWithVelocity> hailstones = parseInput(input);
        long intersectCount = 0;

        for (int h1 = 0; h1 < hailstones.size() - 1; h1++) {
            for (int h2 = h1 + 1; h2 < hailstones.size(); h2++) {
                PointWithVelocity hailstone1 = hailstones.get(h1);
                PointWithVelocity hailstone2 = hailstones.get(h2);
                Optional<Point> intersection = hailstone1.intersect(hailstone2);
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

    private boolean isWithinBoundaries(Point p, Day24NeverTellMeTheOdds.TestArea testArea) {
        double x = p.x();
        double y = p.y();

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

            Point p0 = Point.fromLong(
                    Long.valueOf(pointData[0].strip(), 10),
                    Long.valueOf(pointData[1].strip(), 10),
                    Long.valueOf(pointData[2].strip(), 10)
            );

            Point dp = Point.fromLong(
                    Long.valueOf(velocityData[0].strip(), 10),
                    Long.valueOf(velocityData[1].strip(), 10),
                    Long.valueOf(velocityData[2].strip(), 10)
            );

            Double m = dp.y() / dp.x();

            Line2DMQ trajectory = Line2DMQ.from(m, p0);

            pointsWithVelocities.add(new PointWithVelocity(p0, dp, trajectory));
        }

        return pointsWithVelocities;
    }
}
