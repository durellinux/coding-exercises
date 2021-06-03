package com.gianlucadurelli.coding.hackerrank.neurodiversity;

import java.util.ArrayList;
import java.util.List;

public class DiversityDayMarathon {
    public static List<Integer> getDirections(int i, int n) {
        List<Integer> solution = new ArrayList<>();

        int v = i;
        while(v != 0) {
            solution.add(v % 2);
            v = v >> 1;
        }

        for (int cn = solution.size(); cn < n; cn++) {
            solution.add(0);
        }

        return solution;
    }

    public static int computeFinalPosition(List<Integer> dist, List<Integer> directions) {
        int finalPos = 0;

        for(int i = 0; i < dist.size(); i++) {
            if(directions.get(i) == 0) {
                finalPos += dist.get(i);
            } else {
                finalPos -= dist.get(i);
            }
        }

        return finalPos;
    }

    public static int maxClockwiseRuns(int circumference, List<Integer> dist) {
        int maxClockwise = -1;

        for (int i = 0; i < Math.pow(2, dist.size()); i++) {
            List<Integer> directions = getDirections(i, dist.size());
            int finalPosition = computeFinalPosition(dist, directions);
            if (finalPosition % circumference == 0) {
                int clockWiseRounds = Math.toIntExact(directions.stream().filter(d -> d == 0).count());
                if (clockWiseRounds > maxClockwise) {
                    maxClockwise = clockWiseRounds;
                }
            }
        }

        return maxClockwise;
    }
}
