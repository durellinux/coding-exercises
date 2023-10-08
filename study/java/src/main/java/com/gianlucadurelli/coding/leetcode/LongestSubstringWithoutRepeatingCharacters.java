package com.gianlucadurelli.coding.leetcode;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

// https://leetcode.com/problems/longest-substring-without-repeating-characters/?envType=study-plan-v2&envId=top-interview-150
public class LongestSubstringWithoutRepeatingCharacters {
    private static class Helper {
        Deque<Character> queue;
        Set<Character> characters;

        public Helper() {
            this.queue = new LinkedList<>();
            this.characters = new HashSet<>();
        }

        void add(Character c) {
            queue.add(c);
            characters.add(c);
        }

        boolean contains(Character c) {
            return characters.contains(c);
        }

        void remove(Character c) {
            Character value = queue.remove();
            characters.remove(value);
            while(value != c) {
                value = queue.remove();
                characters.remove(value);
            }
        }
    }
    public int lengthOfLongestSubstring(String s) {
        Helper helper = new Helper();
        int maxLength = 0;

        for(Character c: s.toCharArray()) {
            if (!helper.contains(c)) {
                helper.add(c);
            } else {
                if (helper.queue.size() > maxLength) {
                    maxLength = helper.queue.size();
                }

                helper.remove(c);
                helper.add(c);
            }
        }

        if (helper.queue.size() > maxLength) {
            maxLength = helper.queue.size();
        }

        return maxLength;
    }
}
