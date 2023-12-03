package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;

// https://adventofcode.com/2023/day/3
// - List<Integer> numbers: Numbers founded in the input
// - Integer[][] pointers: for each position of the input contains -1 if the position is not part to any number,
//   the index of the corresponding number in numbers list otherwise.
public class GearRatios {

    public int solve1(List<String> input) {
        if (input == null || input.size() == 0) {
            return 0;
        }

        int R = input.size();
        int C = input.get(0).length();

        char[][] matrix = convertToMatrix(input);
        Integer[][] pointers = new Integer[R][C];
        List<Integer> numbers = new ArrayList<>();
        generateIntermediateRepresentation(matrix, pointers, numbers);

        int result = 0;
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (isSymbol(matrix[r][c])) {
                    Set<Integer> connected = getConnectedNumbers(pointers, r, c);
                    result += connected.stream()
                            .map(numbers::get)
                            .reduce(Integer::sum)
                            .orElse(0);
                }
            }
        }

        return result;
    }

    public int solve2(List<String> input) {
        if (input == null || input.size() == 0) {
            return 0;
        }

        int R = input.size();
        int C = input.get(0).length();

        char[][] matrix = convertToMatrix(input);
        Integer[][] pointers = new Integer[R][C];
        List<Integer> numbers = new ArrayList<>();
        generateIntermediateRepresentation(matrix, pointers, numbers);

        int result = 0;
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (matrix[r][c] == '*') {
                    Set<Integer> connected = getConnectedNumbers(pointers, r, c);
                    if (connected.size() == 2) {
                        result += connected.stream()
                                .map(numbers::get)
                                .reduce((a, b) -> a * b)
                                .orElse(0);
                    }
                }
            }
        }

        return result;
    }

    private void generateIntermediateRepresentation(char[][] matrix, Integer[][] pointers, List<Integer> numbers) {
        int R = matrix.length;
        int C = matrix[0].length;

        for (int r = 0; r < R; r++) {
            List<String> number = new ArrayList<>();
            for (int c = 0; c < C; c++) {
                pointers[r][c] = -1;

                if (isNumber(matrix[r][c])) {
                    number.add(Character.toString(matrix[r][c]));
                    pointers[r][c] = numbers.size();
                } else if (number.size() > 0) {
                    String numberString = number.stream().reduce("", (a, b) -> a = a + b);
                    numbers.add(Integer.valueOf(numberString, 10));
                    number = new ArrayList<>();
                }
            }

            if (number.size() > 0) {
                String numberString = number.stream().reduce("", (a, b) -> a = a + b);
                numbers.add(Integer.valueOf(numberString, 10));
            }
        }
    }

    private Set<Integer> getConnectedNumbers(Integer[][] pointers, int r, int c) {
        Set<Integer> connected = new HashSet<>();

        for (int dr = -1; dr <= 1; dr++) {
            for(int dc = -1; dc <= 1; dc++) {
                Optional<Integer> val = getValue(pointers, r + dr, c + dc);
                if (val.isPresent() && val.get() != -1) {
                    connected.add(val.get());
                }
            }
        }

        return connected;
    }

    private <T> Optional<T> getValue(T[][] matrix, int r, int c) {
        if (r >= 0 && r < matrix.length && c >= 0 && c < matrix[0].length) {
            return Optional.ofNullable(matrix[r][c]);
        }

        return Optional.empty();
    }

    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isSymbol(char c) {
        return !isNumber(c) && c != '.';
    }

    private char[][] convertToMatrix(List<String> input) {
        char[][] matrix = new char[input.size()][input.get(0).length()];

        for (int r = 0; r < input.size(); r++) {
            System.arraycopy(input.get(r).toCharArray(), 0, matrix[r], 0, input.get(r).length());
        }

        return matrix;
    }
}
