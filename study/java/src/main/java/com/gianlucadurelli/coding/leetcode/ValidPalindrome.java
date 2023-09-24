package com.gianlucadurelli.coding.leetcode;

public class ValidPalindrome {
    public boolean isPalindrome(String s) {

        if (s == null || s.isEmpty()) {
            return true;
        }

        int l = 0;
        int r = s.length() - 1;

        String lower = s.toLowerCase();

        if (isInvalidCharacter(lower.charAt(l))) {
            l = advanceL(lower, l + 1);
        }

        if (isInvalidCharacter(lower.charAt(r))) {
            r = advanceR(lower, r - 1);
        }

        while (l < r) {
            if (lower.charAt(l) != lower.charAt(r)) {
                return false;
            }

            l = advanceL(lower, l + 1);
            r = advanceR(lower, r - 1);
        }

        return true;
    }

    private int advanceL(String s, int start) {
        int i = start;

        while(i < s.length() && isInvalidCharacter(s.charAt(i))) {
            i++;
        }

        return i;
    }

    private int advanceR(String s, int start) {
        int i = start;

        while (i >= 0 && isInvalidCharacter(s.charAt(i))) {
            i--;
        }

        return i;
    }

    private boolean isInvalidCharacter(Character c) {
        return !((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9'));
    }
}
