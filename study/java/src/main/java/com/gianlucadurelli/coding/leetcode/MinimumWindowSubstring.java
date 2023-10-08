package com.gianlucadurelli.coding.leetcode;

import java.util.*;

public class MinimumWindowSubstring {
    private static class Window {
        private final Deque<Character> queue;
        private final Map<Character, Integer> charCount;
        private final Map<Character, Integer> referenceCount;
        private final Set<Character> matchedChars;
        private final Set<Character> charsToMatch;

        public Window(String target) {
            charsToMatch = new HashSet<>();
            referenceCount = new HashMap<>();
            for (Character c: target.toCharArray()) {
                int count = referenceCount.getOrDefault(c, 0) + 1;
                referenceCount.put(c, count);
                charsToMatch.add(c);
            }

            queue = new LinkedList<>();
            charCount = new HashMap<>();
            matchedChars = new HashSet<>();
        }

        public void add(Character c) {
            queue.add(c);
            if (referenceCount.containsKey(c)) {
                int newCount = charCount.getOrDefault(c, 0) + 1;
                charCount.put(c, newCount);
                if (newCount >= referenceCount.get(c)) {
                    matchedChars.add(c);
                }
            }

            minimize();
        }

        public boolean foundAllChars() {
            return matchedChars.size() == charsToMatch.size();
        }

        private void minimize() {
            Character c = queue.peek();
            while (c != null) {
                int refCount = referenceCount.getOrDefault(c, 0);
                int foundCount = charCount.getOrDefault(c, 0);

                if (refCount == 0 || foundCount > refCount) {
                    remove();
                    c = queue.peek();
                } else {
                    break;
                }
            }


        }

        private void remove() {
            Character c = queue.remove();
            if (charCount.containsKey(c)) {
                int newCount = charCount.get(c) - 1;
                if (newCount > 0) {
                    charCount.put(c, newCount);
                } else {
                    charCount.remove(c);
                }
            }
        }

        public int size() {
            return queue.size();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Character c: queue) {
                sb.append(c);
            }

            return sb.toString();
        }
    }

    public String minWindow(String s, String t) {
        String solution = "";
        Window window = new Window(t);

        for (Character c: s.toCharArray()) {
            window.add(c);
            if (window.foundAllChars()) {
                if (solution.equals("") || window.size() < solution.length()) {
                    solution = window.toString();
                }
            }
        }

        return solution;
    }
}
