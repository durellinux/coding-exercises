package com.gianlucadurelli.coding.leetcode;

// https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/description/?envType=study-plan-v2&envId=top-interview-150
public class BestTimeBuyStock2 {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int buyPrice = prices[0];
        int lastPrice = prices[0];
        int maxProfit = 0;

        for(int i = 1; i < prices.length; i++) {
            if (prices[i] < lastPrice) {
                maxProfit += Math.max(lastPrice - buyPrice, 0);
                buyPrice = prices[i];
            } else if (i == prices.length - 1) {
                maxProfit += Math.max(prices[i] - buyPrice, 0);
            }

            lastPrice = prices[i];
        }

        return maxProfit;
    }
}
