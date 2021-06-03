package com.gianlucadurelli.coding.google.codejamio.a2021;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

//3
//4
//10 20 10 25
//5
//7 7 7 7 7
//2
//100 1

public class ImpartialTreatsTest {

    @Test
    public void sampleTest() {
        int t1 = ImpartialTreats.solve(List.of(10, 20, 10, 25));
        Assertions.assertThat(t1).isEqualTo(7);

        int t2 = ImpartialTreats.solve(List.of(7, 7, 7, 7, 7));
        Assertions.assertThat(t2).isEqualTo(5);

        int t3 = ImpartialTreats.solve(List.of(100, 1));
        Assertions.assertThat(t3).isEqualTo(3);
    }
}