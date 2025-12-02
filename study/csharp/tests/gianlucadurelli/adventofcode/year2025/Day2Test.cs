using System.Linq;
using FluentAssertions;
using NUnit.Framework;
using src.gianlucadurelli.adventofcode.year2025;
using tests.gianlucadurelli.adventofcode.utils;

namespace tests.gianlucadurelli.adventofcode.year2025;

[TestFixture]
[TestOf(typeof(Day2))]
public class Day2Test
{
    private Day2 solver = new();

    [Test]
    public void Part1_Example()
    {
        string input = FileUtils.ReadAocInput(2025, "day2-test");
        long result = solver.Part1(input.Split(",").ToList());
        result.Should().Be(1227775554);
    }
    
    [Test]
    public void Part1()
    {
        string input = FileUtils.ReadAocInput(2025, "day2");
        long result = solver.Part1(input.Split(",").ToList());
        result.Should().Be(34826702005);
    }
    
    [Test]
    public void Part2_Example()
    {
        string input = FileUtils.ReadAocInput(2025, "day2-test");
        long result = solver.Part2(input.Split(",").ToList());
        result.Should().Be(4174379265);
    }
    
    [Test]
    public void Part2()
    {
        string input = FileUtils.ReadAocInput(2025, "day2");
        long result = solver.Part2(input.Split(",").ToList());
        result.Should().Be(43287141963);
    }
}