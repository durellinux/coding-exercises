using System.Collections.Generic;
using FluentAssertions;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.utils;
using src.gianlucadurelli.adventofcode.year2025;
using tests.gianlucadurelli.adventofcode.utils;

namespace tests.gianlucadurelli.adventofcode.year2025;

public class Day9Test
{
    private Day9 solver = new();
    
    [Test]
    public void Part1_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day9-test");
        long result = solver.Part1(content);
        result.Should().Be(50);
    }
    
    [Test]
    public void Part1()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day9");
        long result = solver.Part1(content);
        result.Should().Be(4749838800);
    }
    
    [Test]
    public void Part2_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day9-test");
        long result = solver.Part2(content);
        result.Should().Be(24);
    }
    
    [Test]
    [Ignore("Takes ~30s to run")]
    public void Part2()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day9");
        long result = solver.Part2(content);
        result.Should().Be(1624057680);
    }
}