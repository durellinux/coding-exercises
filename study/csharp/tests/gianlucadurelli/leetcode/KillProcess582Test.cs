using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using NUnit.Framework;
using src.gianlucadurelli.leetcode;

namespace tests.gianlucadurelli.leetcode;

public class KillProcess582Test
{
    [Test]
    public void Test()
    {
        KillProcess582 solver = new KillProcess582();
        Assert.AreEqual(solver.KillProcess(new int[]{1, 3, 10, 5}, new int[]{3, 0, 5, 3}, 5), new int[]{5, 10});
        Assert.AreEqual(solver.KillProcess(new int[]{1}, new int[]{0}, 1), new int[]{1});
    }
}