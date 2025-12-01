using System.Collections.Generic;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.year2025;
using tests.gianlucadurelli.adventofcode.utils;
using FluentAssertions;


namespace tests.gianlucadurelli.adventofcode.year2025;

public class Day1Test
{
    private Day1 solver = new();

    [Test]
    public void Day1Part1_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day1-test");
        int result = solver.Part1(content);
        result.Should().Be(3);
    }

    [Test]
    public void Day1Part1()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day1");
        int result = solver.Part1(content);
        result.Should().Be(1034);
    }

    [Test]
    public void Day1Part2_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day1-test");
        int result = solver.Part2(content);
        result.Should().Be(6);
    }
    
    [Test]
    public void Day1Part2()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day1");
        int result = solver.Part2(content);
        result.Should().Be(6166);
    }
}