using System.Collections.Generic;
using FluentAssertions;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.year2025;
using tests.gianlucadurelli.adventofcode.utils;

namespace tests.gianlucadurelli.adventofcode.year2025;

public class Day4Test
{
    private Day4 solver = new();
    
    [Test]
    public void Part1_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day4-test");
        int result = solver.Part1(content);
        result.Should().Be(13);
    }
    
    [Test]
    public void Part1()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day4");
        int result = solver.Part1(content);
        result.Should().Be(1486);
    }
    
    [Test]
    public void Part2_Example()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day4-test");
        int result = solver.Part2(content);
        result.Should().Be(43);
    }
    
    [Test]
    public void Part2()
    {
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day4");
        int result = solver.Part2(content);
        result.Should().Be(9024);
    }

    [Test]
    public void PartBonus()
    {
        // How many iterations does it take to clean this input?
        // https://everybody-codes.b-cdn.net/aoc-2025-d4-bonus.txt
        IList<string> content = FileUtils.ReadAocInputLines(2025, "day4-bonus");
        int result = solver.PartBonus(content);
        result.Should().Be(719);
    }
}