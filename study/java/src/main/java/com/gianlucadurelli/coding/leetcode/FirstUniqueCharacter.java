package com.gianlucadurelli.coding.leetcode;

// https://leetcode.com/explore/interview/card/amazon/76/array-and-strings/480/
public class FirstUniqueCharacter {
    public int firstUniqChar(String s) {
        int[] firstPos = new int[256];
        int[] count = new int[256];

        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
            firstPos[i] = -1;
        }

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            count[c]++;
            if (count[c] == 1) {
                firstPos[c] = i;
            }
        }

        int minUnique = -1;
        for (int i = 0; i < count.length; i++) {
            if(count[i] == 1) {
                if (minUnique == -1 || minUnique > firstPos[i]) {
                    minUnique = firstPos[i];
                }
            }
        }

        return minUnique;
    }
}
