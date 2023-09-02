package com.gianlucadurelli.coding.leetcode;

// https://leetcode.com/problems/reverse-words-in-a-string/?envType=study-plan-v2&envId=top-interview-150
public class ReverseWords {
    public String reverseWords(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        StringBuilder reversePhrase = new StringBuilder();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                word.append(s.charAt(i));
            } else {
                if (word.length() > 0) {
                    reversePhrase.insert(0, word.toString());
                    reversePhrase.insert(0, " ");
                    word = new StringBuilder();
                }
            }
        }

        if (word.length() > 0) {
            reversePhrase.insert(0, word.toString());
            reversePhrase.insert(0, " ");
        }

        String result = reversePhrase.toString();
        if (result.length() > 0) {
            return result.substring(1);
        }

        return result;
    }
}
