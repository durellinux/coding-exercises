package com.gianlucadurelli.coding.hackerrank.practice.challenges;

import java.util.List;

// https://www.hackerrank.com/challenges/2d-array
public class Challenge2DArray {
    public static int hourglassSum(List<List<Integer>> arr) {
        Integer maxSum = null;
        int SIZE = 6;
        // Write your code here
        for (int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if (i - 1 >= 0 && i + 1 < SIZE && j - 1 >= 0 && j + 1 < SIZE) {
                    int sum = 0;
                    for(int dx = -1; dx <= 1; dx++) {
                        int x = j + dx;
                        int yUp = i - 1;
                        int yDown = i + 1;

                        sum += arr.get(yUp).get(x);
                        sum += arr.get(yDown).get(x);
                    }

                    sum += arr.get(i).get(j);

                    if (maxSum == null || sum > maxSum) {
                        maxSum = sum;
                    }
                }
            }
        }

        return maxSum;
    }
}
