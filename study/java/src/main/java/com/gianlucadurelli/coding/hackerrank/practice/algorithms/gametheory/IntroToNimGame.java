package com.gianlucadurelli.coding.hackerrank.practice.algorithms.gametheory;

import java.util.*;

public class IntroToNimGame {
    static class BoardState {
        public int player;
        public int[] piles;

        public BoardState(int player, int[] piles) {
            Collections.reverse(Arrays.asList(piles));
            this.player = player;
            this.piles = piles;
        }

        boolean isFinished() {
            return Arrays.stream(piles).sum() == 0;
        }

        int getOtherPlayer() {
            return (player + 1) % 2;
        }

        BoardState pickCount(int index, int count) {
            int[] newPiles = Arrays.copyOf(piles, piles.length);
            newPiles[index] -= count;
            int otherPlayer = (player + 1) % 2;
            return new BoardState(otherPlayer, newPiles);
        }

        BoardState pickAll(int index) {
            return pickCount(index, piles[index]);
        }

        BoardState pickAllButOne(int index) {
            return pickCount(index, piles[index] - 1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BoardState that = (BoardState) o;
            return player == that.player && Objects.deepEquals(piles, that.piles);
        }

        @Override
        public int hashCode() {
            return Objects.hash(player, Arrays.hashCode(piles));
        }
    }

    public static String nimGame(List<Integer> pilesList) {
        int[] piles = new int[pilesList.size()];
        for (int p = 0; p < pilesList.size(); p++) {
            piles[p] = pilesList.get(p);
        }
        BoardState state = new BoardState(0, piles);
        Map<BoardState, Integer> winnerCache = new HashMap<>();
        int winner = findWinner(state, winnerCache);

        if (winner == 0) {
            return "First";
        }

        return "Second";
    }

    private static int findWinner(BoardState state, Map<BoardState, Integer> winnerCache) {
        if (state.isFinished()) {
            return state.getOtherPlayer();
        }

        if (winnerCache.containsKey(state)) {
            return winnerCache.get(state);
        }

        for(int pileIndex = 0; pileIndex < state.piles.length; pileIndex++) {
            int count = state.piles[pileIndex];
            int countNext = pileIndex < state.piles.length - 1 ? state.piles[pileIndex + 1] : 0;

            if (count > 0) {
                int winnerPickOne = pickOne(state, pileIndex, winnerCache);
                if (winnerPickOne == state.player) {
                    return state.player;
                }
            }

            if (countNext > 0) {
                int winnerPickOneNext = pickOne(state, pileIndex + 1, winnerCache);
                if (winnerPickOneNext == state.player) {
                    return state.player;
                }
            }

            if (count > 0) {
                break;
            }
        }

        winnerCache.put(state, state.getOtherPlayer());
        return state.getOtherPlayer();
    }

    private static int pickAll(BoardState state, int index, Map<BoardState, Integer> winnerCache) {
        return pickCount(state, index, state.piles[index], winnerCache);
    }

    private static int pickAllButOne(BoardState state, int index, Map<BoardState, Integer> winnerCache) {
        return pickCount(state, index, state.piles[index] - 1, winnerCache);
    }

    private static int pickOne(BoardState state, int index, Map<BoardState, Integer> winnerCache) {
        return pickCount(state, index, 1, winnerCache);
    }

    private static int pickCount(BoardState state, int index, int count, Map<BoardState, Integer> winnerCache) {
        BoardState newState = state.pickCount(index, count);
        return findWinner(newState, winnerCache);
    }
}
