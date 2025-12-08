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

        Dictionary<Coordinate3D, int> boxToCircuit = new Dictionary<Coordinate3D, int>();
        Dictionary<int, ISet<Coordinate3D>> circuitToBox = new Dictionary<int, ISet<Coordinate3D>>();

        int nextCircuitId = 1;
        for (int i = 0; i < connections; i++)
        {
            var (box1, box2) = sortedPairs[i];
            bool box1InCircuit = boxToCircuit.ContainsKey(box1);
            bool box2InCircuit = boxToCircuit.ContainsKey(box2);

            if (!box1InCircuit && !box2InCircuit)
            {
                int circuitId = nextCircuitId;
                nextCircuitId++;
                boxToCircuit.Add(box1, circuitId);
                boxToCircuit.Add(box2, circuitId);
                ISet<Coordinate3D> group = new HashSet<Coordinate3D>();
                group.Add(box1);
                group.Add(box2);
                circuitToBox.Add(circuitId, group);
            } else if (box1InCircuit && !box2InCircuit)
            {
                int circuitId = boxToCircuit[box1];
                boxToCircuit.Add(box2, circuitId);
                circuitToBox[circuitId].Add(box2);
            } else if (!box1InCircuit && box2InCircuit)
            {
                int circuitId = boxToCircuit[box2];
                boxToCircuit.Add(box1, circuitId);
                circuitToBox[circuitId].Add(box1);
            }
            else
            {
                int circuitId1 = boxToCircuit[box1];
                int circuitId2 = boxToCircuit[box2];
                if (circuitId1 == circuitId2)
                {
                    continue;
                }

                ISet<Coordinate3D> circuit1 = circuitToBox[circuitId1];
                ISet<Coordinate3D> circuit2 = circuitToBox[circuitId2];
                

                if (circuit1.Count <= circuit2.Count)
                {
                    circuit2.UnionWith(circuit1);
                    circuitToBox.Remove(circuitId1);
                    foreach (var box in circuit1)
                    {
                        boxToCircuit[box] = circuitId2;
                    }
                }
                else
                {
                    circuit1.UnionWith(circuit2);
                    circuitToBox.Remove(circuitId2);
                    foreach (var box in circuit2)
                    {
                        boxToCircuit[box] = circuitId1;
                    }
                }
            }
        }

        long result = 1;
        int count = 0;
        foreach (var circuitEntry in circuitToBox.Values.OrderBy(v => v.Count).Reverse())
        {
            result *= circuitEntry.Count;
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

        Dictionary<Coordinate3D, int> boxToCircuit = new Dictionary<Coordinate3D, int>();
        Dictionary<int, ISet<Coordinate3D>> circuitToBox = new Dictionary<int, ISet<Coordinate3D>>();

        int nextCircuitId = 1;
        foreach (var pair in sortedPairs)
        {
            var (box1, box2) = pair;
            bool box1InCircuit = boxToCircuit.ContainsKey(box1);
            bool box2InCircuit = boxToCircuit.ContainsKey(box2);

            if (!box1InCircuit && !box2InCircuit)
            {
                int circuitId = nextCircuitId;
                nextCircuitId++;
                boxToCircuit.Add(box1, circuitId);
                boxToCircuit.Add(box2, circuitId);
                ISet<Coordinate3D> group = new HashSet<Coordinate3D>();
                group.Add(box1);
                group.Add(box2);
                circuitToBox.Add(circuitId, group);
            } else if (box1InCircuit && !box2InCircuit)
            {
                int circuitId = boxToCircuit[box1];
                boxToCircuit.Add(box2, circuitId);
                circuitToBox[circuitId].Add(box2);
            } else if (!box1InCircuit && box2InCircuit)
            {
                int circuitId = boxToCircuit[box2];
                boxToCircuit.Add(box1, circuitId);
                circuitToBox[circuitId].Add(box1);
            }
            else
            {
                int circuitId1 = boxToCircuit[box1];
                int circuitId2 = boxToCircuit[box2];
                if (circuitId1 == circuitId2)
                {
                    continue;
                }

                ISet<Coordinate3D> circuit1 = circuitToBox[circuitId1];
                ISet<Coordinate3D> circuit2 = circuitToBox[circuitId2];
                

                if (circuit1.Count <= circuit2.Count)
                {
                    circuit2.UnionWith(circuit1);
                    circuitToBox.Remove(circuitId1);
                    foreach (var box in circuit1)
                    {
                        boxToCircuit[box] = circuitId2;
                    }
                }
                else
                {
                    circuit1.UnionWith(circuit2);
                    circuitToBox.Remove(circuitId2);
                    foreach (var box in circuit2)
                    {
                        boxToCircuit[box] = circuitId1;
                    }
                }
            }

            if (circuitToBox.Count == 1 && circuitToBox.First().Value.Count == boxes.Count)
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