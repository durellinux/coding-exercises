package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.List;

public class WaitForIt {

    public Long solve1(List<Long> times, List<Long> distances) {
        long result = 1;

        for (int r = 0; r < times.size(); r++) {
            long T = times.get(r);
            long D = distances.get(r);

            double x1d = (T - Math.sqrt(T * T - 4 * D)) / 2;
            double x2d = (T + Math.sqrt(T * T - 4 * D)) / 2;

            double xMin = Math.min(x1d, x2d);
            double xMax = Math.max(x1d, x2d);

            long x1 = Double.valueOf(Math.floor(xMin)).longValue() + 1;
            long x2 = Double.valueOf(Math.ceil(xMax)).longValue() - 1;

            result *= (x2 - x1 + 1);
        }

        return result;
    }
}
