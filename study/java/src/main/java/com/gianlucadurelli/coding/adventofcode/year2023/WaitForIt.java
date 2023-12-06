package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.List;

public class WaitForIt {

    public Long solve1(List<Long> times, List<Long> distances) {
        long result = 1;

        for (int r = 0; r < times.size(); r++) {
            long recordTime = times.get(r);
            long recordDistance = distances.get(r);

            int winningCombinations = 0;
            for (int t = 1; t < recordTime; t++) {
                long movingTime = recordTime - t;
                long newDistance = (long) t * movingTime;

                if (newDistance > recordDistance) {
                    winningCombinations++;
                }
            }

            result *= winningCombinations;
        }

        return result;
    }
}
