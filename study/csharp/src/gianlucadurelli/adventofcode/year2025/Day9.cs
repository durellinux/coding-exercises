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

    /*
     * The whole area can be decomposed in smaller rectangles (created by the intersection of all the lines in the figure, drawn to infinity).
     * These rectangles are all inside or all outside of the polygon.
     * Hence, for each rectangle, we need to check only one of its point to determine whether the rectangle is in or out.
     *
     * Loops over all the pair of coordinates, if all the inner rectangles are valid, then check the area and update the max.
     */
    public long Part2(IList<string> input)
    {
        IList<Segment<Coordinate>> segments = ParseInput2(input);
        IList<Segment<Coordinate>> rowSegments = segments
            .Where(s => s.Start.Row == s.End.Row)
            .OrderBy(s => s.Start.Row)
            .Select(s => s.Start.Col < s.End.Col ? s : new Segment<Coordinate>(s.End, s.Start))
            .ToList();
        IList<Segment<Coordinate>> colSegments = segments
            .Where(s => s.Start.Col == s.End.Col)
            .OrderBy(s => s.Start.Col)
            .Select(s => s.Start.Row < s.End.Row ? s : new Segment<Coordinate>(s.End, s.Start))
            .ToList();
        List<int> rowBreakPoints = segments
            .SelectMany(s => new List<int> { s.Start.Row, s.End.Row })
            .ToHashSet()
            .OrderBy(v => v)
            .ToList();
        List<int> colBreakpoints = segments
            .SelectMany(s => new List<int> { s.Start.Col, s.End.Col })
            .ToHashSet()
            .OrderBy(v => v)
            .ToList();
        IList<Coordinate> coordinates = segments
            .SelectMany(s => new List<Coordinate> { s.Start, s.End })
            .ToHashSet()
            .ToList();

        // Cache for fast lookup to check when an inner rectangle is inside the polygon
        var rectangleCache = new Dictionary<(Coordinate, Coordinate, Coordinate), bool>();
        
        long maxArea = 0;
        for (int c1Id = 0; c1Id < coordinates.Count - 1; c1Id++) {
            for (int c2Id = c1Id + 1; c2Id < coordinates.Count; c2Id++)
            {
                Coordinate c1 = coordinates[c1Id];
                Coordinate c2 = coordinates[c2Id];

                // Check if all the inner rectangles between the two coordinates are inside the polygon
                var isValid = true;
                foreach (var rectangle in GetRectangles(c1, c2, rowBreakPoints, colBreakpoints))
                {
                    var rectangleValid = rectangleCache.ContainsKey(rectangle)
                        ? rectangleCache[rectangle]
                        : IsInside(rectangle.Item3, rowSegments, colSegments);
                    rectangleCache[rectangle] = rectangleValid;
                    isValid = isValid && rectangleValid;
                    if (!isValid)
                    {
                        break;
                    }
                }

                if (!isValid)
                {
                    continue;
                }

                long currentArea = (Math.Abs(c1.Row - c2.Row) + 1) * (long) (Math.Abs(c1.Col - c2.Col) + 1);
                if (currentArea > maxArea)
                {
                    maxArea = currentArea;
                }
            }
        }
        
        return maxArea;
    }

    /* Check if a point is inside the polygon.
     * A point is inside if:
     * - is on one of the segments
     * - drawing a horizontal line from the point to the 0 vertical axis, this line crosses an odd number of segments
     *   it might cross vertexes, if it crosses one vertex, it will then cross another, hence the vertexes should be counted only 1
     */
    private bool IsInside(Coordinate coordinate, IList<Segment<Coordinate>> rowSegments,
        IList<Segment<Coordinate>> colSegments)
    {
        if (rowSegments
            .Any(s => s.Start.Row == coordinate.Row && s.Start.Col <= coordinate.Col && s.End.Col >= coordinate.Col))
        {
            return true;
        }

        if (colSegments
            .Any(s => s.Start.Col == coordinate.Col && s.Start.Row <= coordinate.Row && s.End.Row >= coordinate.Row))
        {
            return true;
        }

        int segmentsCrossed = 0;
        int vertexCrossed = 0;

        foreach (var colSegment in colSegments)
        {
            if (colSegment.Start.Col >= coordinate.Col)
            {
                break;
            }

            if (colSegment.Start == coordinate || colSegment.End == coordinate)
            {
                vertexCrossed++;
            } else if (colSegment.Start.Row <= coordinate.Row && colSegment.End.Row >= coordinate.Row)
            {
                segmentsCrossed++;
            }
        }
        
        return (segmentsCrossed + vertexCrossed / 2) % 2 == 1;
    }

    /*
     * Build all the inner rectangles between c1 and c2.
     * The rectangle is all inside or all outside of the polygon.
     * For each one of them selects a representative coordinate that needs to be checked.
     */
    private IEnumerable<(Coordinate, Coordinate, Coordinate)> GetRectangles(Coordinate c1, Coordinate c2, List<int> rowBreakPoints, List<int> colBreakpoints)
    {
        int colStartPos = colBreakpoints.BinarySearch(c1.Col);
        int colEndPos = colBreakpoints.BinarySearch(c2.Col);
        int rowStartPos = rowBreakPoints.BinarySearch(c1.Row);
        int rowEndPos = rowBreakPoints.BinarySearch(c2.Row);

        if (colEndPos < colStartPos)
        {
            (colStartPos, colEndPos) = (colEndPos, colStartPos);
        }

        if (rowEndPos < rowStartPos)
        {
            (rowStartPos, rowEndPos) = (rowEndPos, rowStartPos);
        }


        if (colStartPos != colEndPos && rowStartPos != rowEndPos)
        {
            for (int rPos1 = rowStartPos; rPos1 <= rowEndPos - 1; rPos1++)
            {
                for (int cPos1 = colStartPos; cPos1 <= colEndPos - 1; cPos1++)
                {
                    Coordinate start = new Coordinate(rowBreakPoints[rPos1], colBreakpoints[cPos1]);
                    Coordinate end = new Coordinate(rowBreakPoints[rPos1 + 1], colBreakpoints[cPos1 + 1]);
                    Coordinate representative = new Coordinate(rowBreakPoints[rPos1] + 1, colBreakpoints[cPos1] + 1);
                    yield return (start, end, representative);
                }
            }
        } else if (colStartPos == colEndPos)
        {
            for (int rPos1 = rowStartPos; rPos1 <= rowEndPos - 1; rPos1++)
            {
                Coordinate start = new Coordinate(rowBreakPoints[rPos1], colBreakpoints[colStartPos]);
                Coordinate end = new Coordinate(rowBreakPoints[rPos1 + 1], colBreakpoints[colStartPos]);
                Coordinate representative = new Coordinate(rowBreakPoints[rPos1] + 1, colStartPos);
                yield return (start, end, representative);
            }
        }
        else if (rowStartPos == rowEndPos)
        {
            for (int cPos1 = colStartPos; cPos1 <= colEndPos - 1; cPos1++)
            {
                Coordinate start = new Coordinate(rowBreakPoints[rowStartPos], colBreakpoints[cPos1]);
                Coordinate end = new Coordinate(rowBreakPoints[rowStartPos], colBreakpoints[cPos1 + 1]);
                Coordinate representative = new Coordinate(rowStartPos, colBreakpoints[cPos1] + 1);
                yield return (start, end, representative);
            }
        }
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
        Coordinate? start = null;

        input.Add(input[0]);
        for (int lineId = 0; lineId < input.Count; lineId++)
        {
            string line = input[lineId];
            string[] values = line.Split(",");
            Coordinate coordinate = new Coordinate(int.Parse(values[1]), int.Parse(values[0]));

            if (lineId == 0)
            {
                start = coordinate;
            }
            else if(start != null)
            {
                var end = coordinate;
                if (start.Col != end.Col && start.Row != end.Row)
                {
                    throw new ArgumentException("Values are not in order!");
                }

                segments.Add(new Segment<Coordinate>(start, end));
                start = end;
            }
        }

        return segments;
    }
}