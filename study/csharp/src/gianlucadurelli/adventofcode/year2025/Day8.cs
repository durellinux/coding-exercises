using src.gianlucadurelli.datastructures.dsu;

namespace src.gianlucadurelli.adventofcode.year2025;
using System.Linq;

public class Day8
{
    public record Coordinate3D(int X, int Y, int Z);

    public long Part1(IList<string> input, int connections)
    {
        IList<Coordinate3D> boxes = ParseInput(input);
        IList<(Coordinate3D, Coordinate3D)> allPairs = new List<(Coordinate3D, Coordinate3D)>();
        
        for (int id1 = 0; id1 < boxes.Count - 1; id1++)
        {
            for (int id2 = id1 + 1; id2 < boxes.Count; id2++)
            {
                allPairs.Add((boxes[id1], boxes[id2]));
            }
        }

        IList<(Coordinate3D, Coordinate3D)> sortedPairs = allPairs.
            OrderBy(v => EuclideanDistance(v.Item1, v.Item2))
            .ToList();

        DsuDummy<Coordinate3D> dsu = new();

        for (int i = 0; i < connections; i++)
        {
            var (box1, box2) = sortedPairs[i];
            long set1 = dsu.Find(box1);
            long set2 = dsu.Find(box2);
            dsu.Merge(set1, set2);
        }

        Dictionary<long, int> setsSize = new Dictionary<long, int>();
        foreach (var box in boxes)
        {
            long set = dsu.Find(box);
            setsSize[set] = dsu.Get(set).Count;
        }

        long result = 1;
        int count = 0;
        foreach (var size in setsSize.Values.OrderBy(v => v).Reverse())
        {
            result *= size;
            count++;
            if (count == 3)
            {
                break;
            }
        }

        return result;
    }
    
    public long Part2(IList<string> input)
    {
        IList<Coordinate3D> boxes = ParseInput(input);
        IList<(Coordinate3D, Coordinate3D)> allPairs = new List<(Coordinate3D, Coordinate3D)>();
        
        for (int id1 = 0; id1 < boxes.Count - 1; id1++)
        {
            for (int id2 = id1 + 1; id2 < boxes.Count; id2++)
            {
                allPairs.Add((boxes[id1], boxes[id2]));
            }
        }

        IList<(Coordinate3D, Coordinate3D)> sortedPairs = allPairs.
            OrderBy(v => EuclideanDistance(v.Item1, v.Item2))
            .ToList();

        Dsu<Coordinate3D> dsu = new();

        foreach (var pair in sortedPairs)
        {
            var (box1, box2) = pair;
            Coordinate3D set1 = dsu.Find(box1);
            Coordinate3D set2 = dsu.Find(box2);
            dsu.Merge(set1, set2);

            if (dsu.GetSize(box1).Equals(boxes.Count))
            {
                return box1.X * (long) box2.X;
            }
        }

        return -1;
    }

    private IList<Coordinate3D> ParseInput(IList<string> input)
    {
        IList<Coordinate3D> boxes = new List<Coordinate3D>();
        foreach (string line in input)
        {
            int[] values = line.Split(",").Select(int.Parse).ToArray();
            boxes.Add(new Coordinate3D(values[0], values[1], values[2]));
        }

        return boxes;
    }

    private double EuclideanDistance(Coordinate3D p1, Coordinate3D p2)
    {
        return Math.Sqrt(Math.Pow(p1.X - p2.X, 2) + Math.Pow(p1.Y - p2.Y, 2) + Math.Pow(p1.Z - p2.Z, 2));
    }
}