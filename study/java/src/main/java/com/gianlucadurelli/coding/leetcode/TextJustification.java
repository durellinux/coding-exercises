package com.gianlucadurelli.coding.leetcode;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/text-justification/?envType=study-plan-v2&envId=top-interview-150
public class TextJustification {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> currentRow = new ArrayList<>();
        int currentRowLength = 0;

        List<String> result = new ArrayList<>();
        for(String s: words) {
            if (currentRow.isEmpty()) {
                currentRow.add(s);
                currentRowLength += s.length();
            } else if (currentRowLength + 1 + s.length() <= maxWidth) {
                currentRow.add(s);
                currentRowLength += 1 + s.length();
            } else {
                result.add(justify(currentRow, maxWidth));
                currentRow = new ArrayList<>();
                currentRow.add(s);
                currentRowLength = s.length();
            }
        }

        result.add(justifyLast(currentRow, maxWidth));
        return result;
    }

    private String justify(List<String> words, int maxWidth) {
        int wordsSize = words.stream().map(String::length).reduce(Integer::sum).orElse(0);
        int spacesCount = Math.max(words.size() - 1, 1);
        int spaces = maxWidth - wordsSize;

        int spacesPerBlock = spaces / spacesCount;
        int spacesToDistribute = spaces % spacesCount;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size() - 1; i++) {
            sb.append(words.get(i));
            int paddingSize = spacesPerBlock;
            if (spacesToDistribute > 0) {
                paddingSize++;
                spacesToDistribute--;
            }
            sb.append(padding(paddingSize));
        }

        sb.append(words.get(words.size() - 1));
        sb.append(padding(maxWidth - sb.length()));

        return sb.toString();
    }

    private String justifyLast(List<String> words, int maxWidth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size() - 1; i++) {
            sb.append(words.get(i));
            sb.append(" ");
        }

        sb.append(words.get(words.size() - 1));
        sb.append(padding(maxWidth - sb.length()));

        return sb.toString();
    }

    private String padding(int size) {
        return " ".repeat(Math.max(0, size));
    }
}
