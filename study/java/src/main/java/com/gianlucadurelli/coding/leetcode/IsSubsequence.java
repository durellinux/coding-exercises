package com.gianlucadurelli.coding.leetcode;

import javax.swing.text.html.Option;
import java.util.Optional;

// https://leetcode.com/problems/is-subsequence/description/?envType=study-plan-v2&envId=top-interview-150
public class IsSubsequence {
    public boolean isSubsequence(String s, String t) {
        int startPosition = 0;
        char[] textChars = t.toCharArray();
        for (char c: s.toCharArray()) {
            Optional<Integer> currentPosition = findLetterFrom(textChars, c, startPosition);
            if (currentPosition.isEmpty()) {
                return false;
            }

            startPosition = currentPosition.get() + 1;
        }

        return true;
    }

    private Optional<Integer> findLetterFrom(char[] textChars, char c, int startPosition) {
        if (startPosition > textChars.length - 1) {
            return Optional.empty();
        }

        for (int i = startPosition; i < textChars.length; i++) {
            if (textChars[i] == c) {
                return Optional.of(i);
            }
        }

        return Optional.empty();
    }
}
