namespace src.gianlucadurelli.adventofcode.year2025;

public class Day9
{
    public long Part1(IList<string> input)
    {
        IList<Day7.Coordinate> coordinates = ParseInput(input);
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

    private IList<Day7.Coordinate> ParseInput(IList<string> input)
    {
        IList<Day7.Coordinate> coordinates = new List<Day7.Coordinate>();
        foreach (var line in input)
        {
            string[] values = line.Split(",");
            coordinates.Add(new Day7.Coordinate(int.Parse(values[1]), int.Parse(values[0])));
        }

        return coordinates;
    }
}