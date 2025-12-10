using System.Collections.Generic;
using FluentAssertions;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.year2025;
using tests.gianlucadurelli.adventofcode.utils;

namespace tests.gianlucadurelli.adventofcode.year2025;

public class Day10Test
{
    private Day10 solver = new();
    
    [Test]
    public void Part1_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day10-test");
        long result = solver.Part1(content);
        result.Should().Be(7);
    }

    [Test]
    public void Part1()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day10");
        long result = solver.Part1(content);
        result.Should().Be(432);
    }
    
    [Test]
    public void Part2_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day10-test");
        long result = solver.Part2(content);
        result.Should().Be(33);
    }
    
    [Test]
    public void Part2()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day10");
        long result = solver.Part2(content);
        result.Should().Be(18011);
    }
}