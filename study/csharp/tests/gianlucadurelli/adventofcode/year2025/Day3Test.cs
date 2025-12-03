using System.Collections.Generic;
using System.Linq;
using FluentAssertions;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.year2025;
using tests.gianlucadurelli.adventofcode.utils;

namespace tests.gianlucadurelli.adventofcode.year2025;

public class Day3Test
{
    private Day3 solver = new();
    
    [Test]
    public void Part1_Example()
    {
        IList<string> input = FileUtils.ReadAocInputLines(2025, "day3-test");
        long result = solver.Part1(input.ToList());
        result.Should().Be(357);
    }
    
    [Test]
    public void Part1()
    {
        IList<string> input = FileUtils.ReadAocInputLines(2025, "day3");
        long result = solver.Part1(input.ToList());
        result.Should().Be(17244);
    }
    
    [Test]
    public void Part2_Example()
    {
        IList<string> input = FileUtils.ReadAocInputLines(2025, "day3-test");
        long result = solver.Part2(input.ToList());
        result.Should().Be(3121910778619);
    }

    [Test]
    public void Part2()
    {
        IList<string> input = FileUtils.ReadAocInputLines(2025, "day3");
        long result = solver.Part2(input.ToList());
        result.Should().Be(171435596092638);
    }
}