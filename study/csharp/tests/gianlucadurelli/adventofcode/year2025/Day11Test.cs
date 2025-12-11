using System.Collections.Generic;
using FluentAssertions;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.year2025;
using tests.gianlucadurelli.adventofcode.utils;

namespace tests.gianlucadurelli.adventofcode.year2025;

public class Day11Test
{
    private Day11 solver = new();
    
    [Test]
    public void Part1_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day11-test");
        long result = solver.Part1(content);
        result.Should().Be(5);
    }
    
    [Test]
    public void Part1()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day11");
        long result = solver.Part1(content);
        result.Should().Be(662);
    }

    [Test]
    public void Part2_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day11-part2-test");
        long result = solver.Part2(content);
        result.Should().Be(2);
    }
    
    [Test]
    public void Part2()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day11");
        long result = solver.Part2(content);
        result.Should().Be(429399933071120);
    }
}