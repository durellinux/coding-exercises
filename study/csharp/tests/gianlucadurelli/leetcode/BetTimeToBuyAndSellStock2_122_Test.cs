using NUnit.Framework;
using src.gianlucadurelli.leetcode;

namespace tests.gianlucadurelli.leetcode
{
    public class BetTimeToBuyAndSellStock2_122_Test
    {
        [Test]
        public void TestCases()
        {
            BetTimeToBuyAndSellStock2_122 solver = new BetTimeToBuyAndSellStock2_122();
            Assert.AreEqual(7, solver.MaxProfit(new[] { 7, 1, 5, 3, 6, 4 }));
            Assert.AreEqual(4, solver.MaxProfit(new[] { 1, 2, 3, 4, 5 }));
            Assert.AreEqual(0, solver.MaxProfit(new[] { 7, 6, 4, 3, 1 }));
        }
    }
}
