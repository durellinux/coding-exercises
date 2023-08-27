package com.gianlucadurelli.coding.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class BestTimeBuyStock2Test {

    private static final BestTimeBuyStock2 solver = new BestTimeBuyStock2();

    @Test
    public void example1() {
        int[] prices = {7,1,5,3,6,4};
        int result = solver.maxProfit(prices);

        Assertions.assertThat(result).isEqualTo(7);
    }

    @Test
    public void example2() {
        int[] prices = {1,2,3,4,5};
        int result = solver.maxProfit(prices);

        Assertions.assertThat(result).isEqualTo(4);
    }

    @Test
    public void example4() {
        int[] prices = {7,6,4,3,1};
        int result = solver.maxProfit(prices);

        Assertions.assertThat(result).isEqualTo(0);
    }



}