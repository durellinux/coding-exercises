using System.Text.RegularExpressions;

namespace src.gianlucadurelli.adventofcode.year2025;

public class Day2
{
    private static Regex PART_1_REGEX = new("^(\\d+)\\1{1}$");
    private static Regex PART_2_REGEX = new("^(\\d+)\\1{1,}$");
    
    public long Part1(IList<String> content)
    {
        return Solve(content, PART_1_REGEX);
    }

    public long Part2(IList<string> content)
    {
        return Solve(content, PART_2_REGEX);
    }

    private long Solve(IList<string> content, Regex regex)
    {
        long result = 0;
        foreach (string range in content)
        {
            foreach (long wrongId in AnalyzeRange(range, regex))
            {
                result += wrongId;
            }
        }
        
        return result;
    }

    private IList<long> AnalyzeRange(String range, Regex regex)
    {
        string[] extremes = range.Split("-");
        string startStr = extremes[0];
        string endStr = extremes[1];
        long start = Int64.Parse(startStr);
        long end = Int64.Parse(endStr);

        List<long> wrongIds = new List<long>();

        for (long value = start; value <= end; value++)
        {
            Match m = regex.Match(value.ToString());
            if (m.Success)
            {
                wrongIds.Add(value);
            }
        }
        
        return wrongIds.ToList();
    }
}