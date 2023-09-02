package com.gianlucadurelli.coding.leetcode;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        int minLength = Arrays.stream(strs).map(String::length).min(Integer::compareTo).orElse(0);

        int maxCommon = 0;
        boolean foundDifference = false;
        for (int i = 0; i < minLength && !foundDifference; i++) {
            char currentChar = strs[0].charAt(i);
            for(int s = 1; s < strs.length; s++) {
                if (strs[s].charAt(i) != currentChar) {
                    foundDifference = true;
                    break;
                }
            }

            if (!foundDifference) {
                maxCommon++;
            }
        }

        return strs[0].substring(0, maxCommon);
    }
}
