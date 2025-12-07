using System.Collections.Generic;
using FluentAssertions;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.year2025;
using tests.gianlucadurelli.adventofcode.utils;

namespace tests.gianlucadurelli.adventofcode.year2025;

public class Day6Test
{
    private Day6 solver = new();
    
    [Test]
    public void Part1_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day6-test");
        long result = solver.Part1(content);
        result.Should().Be(4277556);
    }

    [Test]
    public void Part1()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day6");
        long result = solver.Part1(content);
        result.Should().Be(4719804927602);
    }

    [Test]
    public void Part2_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day6-test");
        long result = solver.Part2(content);
        result.Should().Be(3263827);
    }
    
    [Test]
    public void Part2()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day6");
        long result = solver.Part2(content);
        result.Should().Be(9608327000261);
    }
}