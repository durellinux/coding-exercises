using System.Collections.Generic;
using FluentAssertions;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.year2025;
using tests.gianlucadurelli.adventofcode.utils;

namespace tests.gianlucadurelli.adventofcode.year2025;

public class Day8Test
{
    private Day8 solver = new();
    
    [Test]
    public void Part1_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day8-test");
        long result = solver.Part1(content, 10);
        result.Should().Be(40);
    }
    
    [Test]
    public void Part1()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day8");
        long result = solver.Part1(content, 1000);
        result.Should().Be(90036);
    }
    
    [Test]
    public void Part2_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day8-test");
        long result = solver.Part2(content);
        result.Should().Be(25272);
    }
    
    [Test]
    public void Part2()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day8");
        long result = solver.Part2(content);
        result.Should().Be(6083499488);
    }
}