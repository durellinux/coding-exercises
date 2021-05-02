package com.gianlucadurelli.coding.google.codejamio.a2021;

import java.util.List;
import java.util.stream.Collectors;

public class ImpartialTreats {
    public static int solve(List<Integer> input) {
        List<Integer> animalSizes = input.stream().sorted().collect(Collectors.toList());

        int curV = 1;
        int lastSize = animalSizes.get(0);
        int curCost = 1;

        for (int i = 1; i < animalSizes.size(); i++) {
            int curSize = animalSizes.get(i);
            if (curSize != lastSize) {
                lastSize = curSize;
                curV += 1;
            }

            curCost += curV;
        }

        return curCost;
    }
}
