package com.gianlucadurelli.coding.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RomanToInteger {
    private final Map<String, Integer> lookupTable = new HashMap<>();
    private final Set<String> precedences = Set.of("IV", "IX", "XL", "XC", "CD", "CM");

    public int romanToInt(String s) {
        int value = 0;
        int last = 0;

        for(int i = s.length() - 1; i >= 0; i--) {
            int num = 0;
            switch (s.charAt(i)) {
                case 'I': num = 1; break;
                case 'V': num = 5; break;
                case 'X': num = 10; break;
                case 'L': num = 50; break;
                case 'C': num = 100; break;
                case 'D': num = 500; break;
                case 'M': num = 1000; break;
            }

            if (num < last) {
                value -= num;
            } else {
                value += num;
            }

            last = num;
        }

        return value;
    }

    public int romanToIntV2(String s) {
        Map<String, Integer> valueMap = new HashMap<>();
        valueMap.put("I", 1);
        valueMap.put("V", 5);
        valueMap.put("X", 10);
        valueMap.put("L", 50);
        valueMap.put("C", 100);
        valueMap.put("D", 500);
        valueMap.put("M", 1000);
        valueMap.put("IV", 4);
        valueMap.put("IX", 9);
        valueMap.put("XL", 40);
        valueMap.put("XC", 90);
        valueMap.put("CD", 400);
        valueMap.put("CM", 900);

        int value = 0;
        String currentBlock = "" + s.charAt(0);

        for (int i = 1; i < s.length(); i++) {
            if (currentBlock.length() == 1 && precedences.contains(currentBlock + s.charAt(i))) {
                value += valueMap.get(currentBlock + s.charAt(i));
                currentBlock = "";
            } else {
                value += valueMap.getOrDefault(currentBlock, 0);
                currentBlock = "" + s.charAt(i);
            }
        }

        value += valueMap.getOrDefault(currentBlock, 0);
        return value;
    }


    public int romanToIntV1(String s) {
        Map<String, Integer> table = getLookupTable();

        if (!table.containsKey(s)) {
            throw new IllegalArgumentException("Value is not in range [1, 3999]: " + s);
        }

        return table.get(s);
    }

    private Map<String, Integer> getLookupTable() {
        if (lookupTable.isEmpty()) {
            initializeLookupTable();
        }

        return lookupTable;
    }

    private void initializeLookupTable() {
        Map<Integer, String> intToRomanTable = new HashMap<>();
        intToRomanTable.put(1, "I");
        intToRomanTable.put(2, "II");
        intToRomanTable.put(3, "III");
        intToRomanTable.put(4, "IV");
        intToRomanTable.put(5, "V");
        intToRomanTable.put(6, "VI");
        intToRomanTable.put(7, "VII");
        intToRomanTable.put(8, "VIII");
        intToRomanTable.put(9, "IX");
        intToRomanTable.put(10, "X");
        intToRomanTable.put(20, "XX");
        intToRomanTable.put(30, "XXX");
        intToRomanTable.put(40, "XL");
        intToRomanTable.put(50, "L");
        intToRomanTable.put(60, "LX");
        intToRomanTable.put(70, "LXX");
        intToRomanTable.put(80, "LXXX");
        intToRomanTable.put(90, "XC");
        intToRomanTable.put(100, "C");
        intToRomanTable.put(200, "CC");
        intToRomanTable.put(300, "CCC");
        intToRomanTable.put(400, "CD");
        intToRomanTable.put(500, "D");
        intToRomanTable.put(600, "DC");
        intToRomanTable.put(700, "DCC");
        intToRomanTable.put(800, "DCCC");
        intToRomanTable.put(900, "CM");
        intToRomanTable.put(1000, "M");
        intToRomanTable.put(2000, "MM");
        intToRomanTable.put(3000, "MMM");

        for (int i = 1; i < 4000; i++) {
            int u = i % 10;
            int d = i / 10 % 10 * 10;
            int c = i / 100 % 10 * 100;
            int m = i / 1000 % 10 * 1000;

            String romanNumber = intToRomanTable.getOrDefault(m, "")
                    + intToRomanTable.getOrDefault(c, "")
                    + intToRomanTable.getOrDefault(d, "")
                    + intToRomanTable.getOrDefault(u, "");

            lookupTable.put(romanNumber, i);
        }
    }
}
