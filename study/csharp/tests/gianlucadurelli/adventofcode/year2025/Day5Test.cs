using System.Collections.Generic;
using FluentAssertions;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.year2025;
using tests.gianlucadurelli.adventofcode.utils;

namespace tests.gianlucadurelli.adventofcode.year2025;

public class Day5Test
{
    private Day5 solver = new();
    
    [Test]
    public void Part1_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day5-test");
        int result = solver.Part1(content);
        result.Should().Be(3);
    }
    
    [Test]
    public void Part1()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day5");
        int result = solver.Part1(content);
        result.Should().Be(770);
    }

    [Test]
    public void Part2_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day5-test");
        long result = solver.Part2(content);
        result.Should().Be(14);
    }
    
    [Test]
    public void Part2()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day5");
        long result = solver.Part2(content);
        result.Should().Be(357674099117260);
    }
}