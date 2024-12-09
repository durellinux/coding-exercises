package com.gianlucadurelli.coding.hackerrank.practice.algorithms.gametheory;

import com.google.gson.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class IntroToNimGameTest {

    @Test
    public void example1() {
        String solution = IntroToNimGame.nimGame(List.of(1, 1));
        Assertions.assertThat(solution).isEqualTo("Second");
    }

    @Test
    public void example2() {
        String solution = IntroToNimGame.nimGame(List.of(2, 1, 4));
        Assertions.assertThat(solution).isEqualTo("First");
    }

    @Test
    public void big1() {
        String solution = IntroToNimGame.nimGame(List.of(4, 5, 0, 5, 8, 3, 8, 8, 3, 2, 2, 2, 8, 3, 0, 9, 4, 8, 9, 8, 6, 0, 6, 5, 9, 7, 6, 2, 7, 2, 9, 3, 9, 1, 9, 9, 7, 9, 7, 0, 4, 1, 2, 2, 4, 2, 3, 8, 3, 3, 8, 1, 3, 4, 6, 4, 3, 4, 8, 1, 9, 9, 4, 8, 1, 5, 8, 0, 7, 5, 0, 1, 9, 3, 3, 5, 7, 6, 6, 2, 1, 4, 5, 7, 9, 3, 3, 2, 0, 2, 5, 9, 3, 2, 7, 4, 9, 7));
        Assertions.assertThat(solution).isEqualTo("First");
    }
}