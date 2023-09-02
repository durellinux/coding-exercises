package com.gianlucadurelli.coding.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/?envType=study-plan-v2&envId=top-interview-150
public class FindIndexOfFirstOccurrence {
    public int strStr(String haystack, String needle) {
        Queue<Integer> startPositions = new LinkedList<>();
        for(int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                startPositions.add(i);
            }
        }

        while(!startPositions.isEmpty()) {
            int currentStart = startPositions.remove();
            int currentChar = 0;
            boolean equals = true;
            while(currentStart + currentChar < haystack.length() && currentChar < needle.length() && equals) {
                if (haystack.charAt(currentStart + currentChar) != needle.charAt(currentChar)) {
                    equals = false;
                }

                currentChar++;
            }

            if (equals && currentChar == needle.length()) {
                return currentStart;
            }
        }

        return -1;
    }

    public int strStrV1(String haystack, String needle) {
        return haystack.indexOf(needle);
    }
}
