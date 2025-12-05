namespace src.gianlucadurelli.adventofcode.year2025;

public record Interval(long Min, long Max);

public class Day5
{
    public int Part1(IList<string> input)
    {
        var (intervals, ids) = ParseInput(input);
        ISet<Interval> mergedIntervals = MergeIntervals(intervals);

        int freshProducts = 0;
        foreach (var id in ids)
        {
            freshProducts += IsFresh(id, mergedIntervals) ? 1 : 0;
        }

        return freshProducts;
    }

    public long Part2(IList<string> input)
    {
        long totalSize = 0;
        var (intervals, ids) = ParseInput(input);
        ISet<Interval> mergedIntervals = MergeIntervals(intervals);
        foreach (Interval interval in mergedIntervals)
        {
            totalSize += interval.Max - interval.Min + 1;
        }
        
        return totalSize;
    }

    private (IList<Interval> intervals, IList<long> ids) ParseInput(IList<string> input) {
        IList<Interval> intervals = new List<Interval>();
        IList<long> ids = new List<long>();

        bool isParsingIntervals = true;
        foreach (string line in input)
        {
            if (line.Trim().Length == 0)
            {
                isParsingIntervals = false;
            }
            else if (isParsingIntervals)
            {
                string[] parts = line.Split("-");
                intervals.Add(new(long.Parse(parts[0]),  long.Parse(parts[1])));
            }
            else
            {
                ids.Add(long.Parse(line));
            }
        }

        return (intervals, ids);
    }

    private bool IsFresh(long value, ISet<Interval> intervals)
    {
        foreach (Interval interval in intervals)
        {
            if (IsInInterval(value, interval))
            {
                return true;
            }
        }
        
        return false;
    }

    private bool IsInInterval(long value, Interval interval)
    {
        return value >= interval.Min && value <= interval.Max;
    }

    private ISet<Interval> MergeIntervals(IList<Interval> intervals)
    {
        ISet<Interval> intervalsToMerge = intervals.ToHashSet();
        ISet<Interval> mergedIntervals = new HashSet<Interval>();
        while (intervalsToMerge.Count > 0)
        {
            foreach (Interval interval in intervalsToMerge)
            {
                Interval? toMergeWith = TryMerge(interval, intervalsToMerge);
                if (toMergeWith != null)
                {
                    Interval newInterval = Merge(interval, toMergeWith);
                    intervalsToMerge.Remove(toMergeWith);
                    intervalsToMerge.Remove(interval);
                    intervalsToMerge.Add(newInterval);
                }
                else
                {
                    mergedIntervals.Add(interval);
                    intervalsToMerge.Remove(interval);
                }
                break;
            }
        }
        
        return mergedIntervals;
    }

    private Interval Merge(Interval interval1, Interval interval2)
    {
        return new Interval(Math.Min(interval1.Min, interval2.Min), Math.Max(interval1.Max, interval2.Max));
    }

    private Interval? TryMerge(Interval interval, ISet<Interval> intervals)
    {
        foreach (var interval2 in intervals)
        {
            if (IsOverlapping(interval, interval2))
            {
                return interval2;
            }
        }
        return null;
    }

    private bool IsOverlapping(Interval interval1, Interval interval2)
    {
        return !interval1.Equals(interval2) && interval1.Max >= interval2.Min && interval2.Max >= interval1.Min;
    }
}