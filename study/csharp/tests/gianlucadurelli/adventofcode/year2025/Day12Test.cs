using System.Collections.Generic;
using FluentAssertions;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.year2025;
using tests.gianlucadurelli.adventofcode.utils;

namespace tests.gianlucadurelli.adventofcode.year2025;

public class Day12Test
{
    private Day12 solver = new();
    
    [Test]
    public void Part1_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day12-test");
        long result = solver.Part1(content);
        result.Should().Be(2);
    }
    
    [Test]
    public void Part1()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day12");
        long result = solver.Part1(content);
        result.Should().Be(589);
    }
}