package com.gianlucadurelli.coding.leetcode;

//https://leetcode.com/explore/interview/card/amazon/76/array-and-strings/2971/
public class MissingNumber {

    public int missingNumber(int[] numbers) {
        int sum = 0;
        for (int v: numbers) {
            sum += v;
        }

        int max = numbers.length;
        int expected = (max * (max + 1)) / 2;
        return expected - sum;
    }
}
