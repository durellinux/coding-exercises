package com.gianlucadurelli.coding.leetcode;

// https://leetcode.com/problems/length-of-last-word/?envType=study-plan-v2&envId=top-interview-150
public class LengthOfLastWord {
    public int lengthOfLastWord(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int lastWordLength = 0;
        int currentWordLength = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                currentWordLength++;
            } else {
                if (currentWordLength > 0) {
                    lastWordLength = currentWordLength;
                    currentWordLength = 0;
                }
            }
        }

        if (currentWordLength > 0) {
            return currentWordLength;
        } else {
            return lastWordLength;
        }
    }
}
