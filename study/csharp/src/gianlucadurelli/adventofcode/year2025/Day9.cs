namespace src.gianlucadurelli.adventofcode.year2025;
using utils;
using System.Linq;

public class Day9
{
    public long Part1(IList<string> input)
    {
        IList<Coordinate> coordinates = ParseInput1(input);
        long maxArea = 0;
        foreach (var c1 in coordinates) 
        {
            foreach (var c2 in coordinates)
            {
                long currentArea = (Math.Abs(c1.Row - c2.Row) + 1) * (long) (Math.Abs(c1.Col - c2.Col) + 1);
                if (currentArea > maxArea)
                {
                    maxArea = currentArea;
                }
            }
        }
        
        return maxArea;
    }

    public long Part2(IList<string> input)
    {
        IList<Segment<Coordinate>> segments = ParseInput2(input);
        IList<Segment<Coordinate>> rowSegments = segments.Where(s => s.Start.Row == s.End.Row).ToList();
        IList<Segment<Coordinate>> colSegments = segments.Where(s => s.Start.Col == s.End.Col).ToList();
        IList<int> rowBreakPoints = rowSegments.SelectMany(s => new List<int> { s.Start.Row, s.End.Row }).ToList();
        IList<int> colBreakpoints = colSegments.SelectMany(s => new List<int> { s.Start.Col, s.End.Col }).ToList();

        return 0;
    }

    private IList<Coordinate> ParseInput1(IList<string> input)
    {
        IList<Coordinate> coordinates = new List<Coordinate>();
        foreach (var line in input)
        {
            string[] values = line.Split(",");
            coordinates.Add(new Coordinate(int.Parse(values[1]), int.Parse(values[0])));
        }

        return coordinates;
    }
    
    private IList<Segment<Coordinate>> ParseInput2(IList<string> input)
    {
        IList<Segment<Coordinate>> segments = new List<Segment<Coordinate>>();
        Coordinate? Start = null;
        Coordinate End;

        for (int lineId = 0; lineId < input.Count; lineId++)
        {
            string line = input[lineId];
            string[] values = line.Split(",");
            Coordinate coordinate = new Coordinate(int.Parse(values[1]), int.Parse(values[0]));

            if (lineId == 0)
            {
                Start = coordinate;
            }
            else if(Start != null)
            {
                End = coordinate;

                if (Start.Col != End.Col && Start.Row != End.Row)
                {
                    throw new ArgumentException("Values are not in order!");
                }

                segments.Add(new Segment<Coordinate>(Start, End));
                Start = End;
            }
        }

        return segments;
    }
}