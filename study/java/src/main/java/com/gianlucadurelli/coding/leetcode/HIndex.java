package com.gianlucadurelli.coding.leetcode;

import java.util.Arrays;

public class HIndex {
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        reverse(citations);

        int h = 0;
        while(h < citations.length && citations[h] >= h + 1) {
            h++;
        }

        return h;
    }

    private void reverse(int[] array) {
        int start = 0;
        int end = array.length - 1;
        while (start < end) {
            int tmp = array[end];
            array[end] = array[start];
            array[start] = tmp;

            start++;
            end--;
        }
    }
}
