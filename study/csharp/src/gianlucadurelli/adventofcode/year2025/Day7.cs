namespace src.gianlucadurelli.adventofcode.year2025;

public class Day7
{
    public record Coordinate(int Row, int Col);
    
    public int Part1(IList<string> input)
    {
        var (start, splitters, rows, cols) = ParseInput(input);
        var (splittersHit, timelines) = Simulate(start, splitters, rows, cols);
        return splittersHit;
    }

    public long Part2(IList<string> input)
    {
        var (start, splitters, rows, cols) = ParseInput(input);
        var (splittersHit, timelines) = Simulate(start, splitters, rows, cols);
        return timelines;
    }

    private (int, long) Simulate(Coordinate start, ISet<Coordinate> splitters, int rows, int cols)
    {
        Queue<Coordinate> toVisit = new Queue<Coordinate>();
        toVisit.Enqueue(start);

        Dictionary<Coordinate, long> quantumTimelines = new Dictionary<Coordinate, long>();
        quantumTimelines.Add(start, 1);
        long timelines = 0;
        int splittersHit = 0;
        while (toVisit.Count > 0)
        {
            Coordinate ray = toVisit.Dequeue();
            if (ray.Row == rows)
            {
                timelines += quantumTimelines[ray];
            }

            Coordinate newPosition = ray with { Row = ray.Row + 1 };
            IList<Coordinate> newRays = new List<Coordinate>();
            if (splitters.Contains(newPosition))
            {
                splittersHit++;
                newRays.Add(newPosition with {Col = newPosition.Col - 1});
                newRays.Add(newPosition with {Col = newPosition.Col + 1});
            }
            else
            {
                newRays.Add(newPosition);
            }

            foreach (Coordinate newRay in newRays)
            {
                if (newRay.Row <= rows && newRay.Col >= 0 && newRay.Col <= cols)
                {
                    if (quantumTimelines.ContainsKey(newRay))
                    {
                        quantumTimelines[newRay] += quantumTimelines[ray];
                    }
                    else
                    {
                        quantumTimelines[newRay] = quantumTimelines[ray];
                        toVisit.Enqueue(newRay);
                    }
                }
            }
        }

        return (splittersHit, timelines);
    }

    public (Coordinate, ISet<Coordinate>, int, int) ParseInput(IList<string> input)
    {
        Coordinate? start = null;
        ISet<Coordinate> splitters = new HashSet<Coordinate>();
        for (int row = 0; row < input.Count; row++)
        {
            char[] line = input[row].ToCharArray();
            for (int col = 0; col < line.Length; col++)
            {
                char value = line[col];
                if (value == 'S')
                {
                    start = new Coordinate(row, col);
                } else if (value == '^')
                {
                    splitters.Add(new Coordinate(row, col));
                }
            }
        }

        if (start == null)
        {
            throw new Exception("Invalid input");
        }

        return (start, splitters, input.Count, input[0].Length);
    }
}