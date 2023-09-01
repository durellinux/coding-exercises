package com.gianlucadurelli.coding.leetcode;

import java.util.Arrays;

// https://leetcode.com/problems/gas-station/?envType=study-plan-v2&envId=top-interview-150
public class CanCompleteCircuit {

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int[] gains = new int[gas.length];

        for (int i = 0; i < gas.length; i++) {
            gains[i] = gas[i] - cost[i];
        }

        int totalSum = 0;
        int minGas = 0;

        for (int gain: gains) {
            totalSum += gain;
            if (totalSum < minGas) {
                minGas = totalSum;
            }
        }

        if (totalSum < 0) {
            return -1;
        }

        if (minGas >= 0) {
            return 0;
        }

        for (int i = 0; i < gains.length; i++) {
            minGas -= gains[i];
            if (minGas >= 0) {
                return i + 1;
            }
        }

        return -1;
    }

    public int canCompleteCircuitV1(int[] gas, int[] cost) {
        int[] gains = new int[gas.length];

        for (int i = 0; i < gas.length; i++) {
            gains[i] = gas[i] - cost[i];
        }


        for (int i = 0; i < gains.length; i++) {
            int totalSum = 0;
            int minGas = 0;

            for (int s = i; s < gains.length; s++) {
                int position = s % gains.length;
                totalSum += gains[position];
                if (totalSum < minGas) {
                    minGas = totalSum;
                }
            }

            if (minGas >= 0) {
                return i;
            }
        }

        return -1;
    }
}
