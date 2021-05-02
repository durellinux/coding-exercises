package com.gianlucadurelli.coding.google.codejamio.a2021;

import java.util.HashMap;

public class IrrefutableOutcome {

    public static HashMap<String, HashMap<String, Integer>> memoized;

    public static HashMap<String, Integer> solve(String board) {
        memoized = new HashMap<>();
        return recursiveSolve(board, "I");
    }

    public static String otherPlayer(String player) {
        return player.equals("I") ? "O" : "I";
    }

    public static boolean canMoveLeft(String board, String player) {
        return board.charAt(0) == player.charAt(0);
    }

    public static boolean canMoveRight(String board, String player) {
        return board.charAt(board.length() - 1) == player.charAt(0);
    }

    public static HashMap<String, Integer> recursiveSolve(String board, String player) {
        String memoizedSolution = board + "-" + player;
        if (memoized.containsKey(memoizedSolution)) {
            return memoized.get(memoizedSolution);
        }


        if (board.isEmpty()) {
            HashMap<String, Integer> result = new HashMap<>();
            result.put(otherPlayer(player), 1);
            memoized.put(board+"-"+player, result);
            return result;
        }

        if (!canMoveLeft(board, player) && !canMoveRight(board, player)) {
            HashMap<String, Integer> result = new HashMap<>();
            result.put(otherPlayer(player), 1 + board.length());
            memoized.put(board+"-"+player, result);
            return result;
        }

        HashMap<String, Integer> moveLeft = new HashMap<>();
        HashMap<String, Integer> moveRight = new HashMap<>();

        if (canMoveLeft(board, player)) {
            moveLeft = recursiveSolve(board.substring(1), otherPlayer(player));
        }

        if (canMoveRight(board, player)) {
            moveRight = recursiveSolve(board.substring(0, board.length() - 1), otherPlayer(player));
        }

        if (!canMoveRight(board, player)) {
            memoized.put(board+"-"+player, moveLeft);
            return moveLeft;
        }

        if (!canMoveLeft(board, player)) {
            memoized.put(board+"-"+player, moveRight);
            return moveRight;
        }

        int valueLeft = moveLeft.getOrDefault(player, 0);
        int valueRight = moveRight.getOrDefault(player, 0);

        if (valueLeft > valueRight) {
            memoized.put(board+"-"+player, moveLeft);
            return moveLeft;
        } else if (valueRight > valueLeft) {
            memoized.put(board+"-"+player, moveRight);
            return moveRight;
        } else {
            String other = otherPlayer(player);
            int valueOtherLeft = moveLeft.getOrDefault(player, 0);
            int valueOtherRight = moveRight.getOrDefault(player, 0);

            if (valueOtherLeft < valueOtherRight) {
                memoized.put(board+"-"+player, moveLeft);
                return moveLeft;
            } else if (valueOtherRight < valueOtherLeft) {
                memoized.put(board+"-"+player, moveRight);
                return moveRight;
            } else {
                memoized.put(board+"-"+player, moveLeft);
                return moveLeft;
            }
        }
    }

}
