package com.gianlucadurelli.coding.leetcode;

import java.util.*;

// https://leetcode.com/problems/substring-with-concatenation-of-all-words/?envType=study-plan-v2&envId=top-interview-150
public class SubstringWithConcatenationOfAllWords {

    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> solution = new ArrayList<>();
        int wordLength = words[0].length();
        int validStringLenght = wordLength * words.length;

        Map<String, Integer> referenceCount = new HashMap<>();
        for (String string: words) {
            int count = referenceCount.getOrDefault(string, 0) + 1;
            referenceCount.put(string, count);
        }

        for(int i = 0; i <= s.length() - validStringLenght; i++) {
            String substring = s.substring(i, i + validStringLenght);
            if (isValidPermutation(substring, referenceCount, wordLength)) {
                solution.add(i);
            }
        }

        return solution;
    }

    private boolean isValidPermutation(String substring, Map<String, Integer> referenceCount, int wordLength) {
        Map<String, Integer> foundCount = new HashMap<>();

        for (int i = 0; i < substring.length(); i += wordLength) {
            String word = substring.substring(i, i + wordLength);
            if (!referenceCount.containsKey(word)) {
                return false;
            }

            int newCount = foundCount.getOrDefault(word, 0) + 1;
            if (newCount > referenceCount.get(word)) {
                return false;
            }

            foundCount.put(word, newCount);
        }

        return true;
    }

    int foundWords;
    Map<String, Integer> foundCount = new HashMap<>();
    Deque<String> window = new LinkedList<>();
    Map<String, Integer> referenceCount = new HashMap<>();

    public List<Integer> findSubstring2(String s, String[] words) {
        if (words.length == 0) {
            return Collections.emptyList();
        }

        for (String string: words) {
            int count = referenceCount.getOrDefault(string, 0) + 1;
            referenceCount.put(string, count);
        }

        int stringLength = words[0].length();

        List<Integer> solution = new ArrayList<>();

        int startIndex = -1;
        int i = 0;
        while(i <= s.length() - stringLength) {
            String word = s.substring(i, i + stringLength);
            if (!referenceCount.containsKey(word)) {
                startIndex = -1;
                foundCount.clear();
                foundWords = 0;
                window.clear();
                i++;
            } else {
                if (foundWords == 0) {
                    startIndex = i;
                }

                int newFoundCount = foundCount.getOrDefault(word, 0) + 1;

                if (newFoundCount > referenceCount.get(word)) {
                    int removedWords = removeWord(word);
                    startIndex += removedWords * stringLength;
                }

                addWord(word);
                if (foundWords == words.length) {
                    solution.add(startIndex);
                }

                i += stringLength;
            }
        }

        return solution;
    }

    private int removeWord(String word) {
        int removedWords = 1;
        String w = window.remove();
        foundWords--;
        int newCount = foundCount.get(w) - 1;
        foundCount.put(w, newCount);
        while(!w.equals(word)) {
            removedWords++;
            w = window.remove();
            foundWords--;
            newCount = foundCount.get(w) - 1;
            foundCount.put(w, newCount);
        }

        return removedWords;
    }

    private void addWord(String word) {
        foundWords++;
        int newCount = foundCount.getOrDefault(word, 0) + 1;
        foundCount.put(word, newCount);
        window.add(word);
    }
}
