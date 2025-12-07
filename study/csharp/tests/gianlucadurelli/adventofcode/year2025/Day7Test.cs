using System.Collections.Generic;
using FluentAssertions;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.year2025;
using tests.gianlucadurelli.adventofcode.utils;

namespace tests.gianlucadurelli.adventofcode.year2025;

public class Day7Test
{
    private Day7 solver = new();

    [Test]
    public void Part1_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day7-test");
        long result = solver.Part1(content);
        result.Should().Be(21);
    }

    [Test]
    public void Part1()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day7");
        long result = solver.Part1(content);
        result.Should().Be(1703);
    }
    
    [Test]
    public void Part2_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day7-test");
        long result = solver.Part2(content);
        result.Should().Be(40);
    }
    
    [Test]
    public void Part2()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day7");
        long result = solver.Part2(content);
        result.Should().Be(171692855075500);
    }
}