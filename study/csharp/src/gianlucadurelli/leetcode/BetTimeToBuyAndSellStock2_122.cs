namespace src.gianlucadurelli.leetcode
{
    public class BetTimeToBuyAndSellStock2_122
    {
        public int MaxProfit(int[] prices)
        {
            int profit = 0;
            int currentBuy = prices[0];
            for (int p = 1; p < prices.Length; p++)
            {
                int sellPrice = prices[p];

                if (sellPrice > currentBuy)
                {
                    profit += sellPrice - currentBuy;
                }

                currentBuy = sellPrice;
            }

            return profit;
        }
    }
}