using FluentAssertions;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.utils;

namespace tests.gianlucadurelli.adventofcode.utils;

[TestFixture]
[TestOf(typeof(MultiCounter))]
public class MultiCounterTest
{

    [Test]
    public void TestLogic()
    {
        var multiCounter = new MultiCounter(8, 4);
        multiCounter.Increment(0);
        multiCounter.Increment(0);
        multiCounter.Increment(0);
        multiCounter.Increment(1);
        multiCounter.Increment(1);
        multiCounter.Increment(2);

        multiCounter.GetCounter(0).Should().Be(3);
        multiCounter.GetCounter(1).Should().Be(2);
        multiCounter.GetCounter(2).Should().Be(1);
        multiCounter.GetCounter(3).Should().Be(0);
    }
}