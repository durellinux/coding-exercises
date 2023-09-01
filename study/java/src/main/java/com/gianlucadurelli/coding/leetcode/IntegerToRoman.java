package com.gianlucadurelli.coding.leetcode;

public class IntegerToRoman {
    public String intToRoman(int num) {
        int step = 0;
        StringBuilder result = new StringBuilder();

        while(num != 0) {
            int value = num % 10;

            String valueString = "";
            switch (step) {
                case 0: valueString = convert(value, "", "I", "V", "X"); break;
                case 1: valueString = convert(value, "", "X", "L", "C"); break;
                case 2: valueString = convert(value, "", "C", "D", "M"); break;
                case 3: valueString = convert(value, "", "M", "", ""); break;
            }

            result.insert(0, valueString);

            num = num / 10;
            step++;
        }

        return result.toString();
    }

    private String convert(int value, String zero, String one, String five, String ten) {
        switch (value) {
            case 0: return zero;
            case 1: return one;
            case 2: return one + one;
            case 3: return one + one + one;
            case 4: return one + five;
            case 5: return five;
            case 6: return five + one;
            case 7: return five + one + one;
            case 8: return five + one + one + one;
            case 9: return one + ten;
        }

        return "";
    }
}
