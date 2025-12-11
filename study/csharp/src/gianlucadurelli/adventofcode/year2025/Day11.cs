using src.gianlucadurelli.datastructures.graphs;

namespace src.gianlucadurelli.adventofcode.year2025;

public class Day11
{
    public long Part1(IList<string> input)
    {
        DirectedGraph<string> graph = MinimizeGraph(ParseInput(input), "you");
        AdjacencyList<string> incomingEdges = graph.GetIncomingEdges();
        Dictionary<string, long> pathsToNode = new Dictionary<string, long>();
        foreach (var node in graph.GetNodes())
        {
            pathsToNode[node] = 1;
        }

        bool updatedValues = true;
        while (updatedValues)
        {
            updatedValues = false;
            foreach (var node in graph.GetNodes())
            {
                long currentCount = pathsToNode[node];
                long newCount = 0;
                foreach (var previousNode in incomingEdges.GetNeighbors(node))
                {
                    newCount += pathsToNode[previousNode];
                }

                if (newCount > 0 && newCount != currentCount)
                {
                    pathsToNode[node] = newCount;
                    updatedValues = true;
                }
            }
        }

        return pathsToNode["out"];
    }

    public long Part2(IList<string> input)
    {
        DirectedGraph<string> graph = MinimizeGraph(ParseInput(input), "svr");
        AdjacencyList<string> incomingEdges = graph.GetIncomingEdges();
        Dictionary<string, (long, long, long, long)> pathsToNode = new Dictionary<string, (long, long, long, long)>();
        foreach (var node in graph.GetNodes())
        {
            pathsToNode[node] = (1, 0, 0, 0);
        }

        bool updatedValues = true;
        while (updatedValues)
        {
            updatedValues = false;
            foreach (var node in graph.GetNodes())
            {
                (long, long, long, long) currentCount = pathsToNode[node];
                long countViaNothing = 0;
                long countViaDac = 0;
                long countViaFft = 0;
                long countViaBoth = 0;
                foreach (var previousNode in incomingEdges.GetNeighbors(node))
                {
                    if (node == "dac")
                    {
                        countViaDac += pathsToNode[previousNode].Item1;
                        countViaBoth += pathsToNode[previousNode].Item3;
                    } else if (node == "fft")
                    {
                        countViaFft += pathsToNode[previousNode].Item1;
                        countViaBoth += pathsToNode[previousNode].Item2;
                    }
                    else
                    {
                        countViaNothing += pathsToNode[previousNode].Item1;
                        countViaDac += pathsToNode[previousNode].Item2;
                        countViaFft += pathsToNode[previousNode].Item3;
                        countViaBoth += pathsToNode[previousNode].Item4;
                    }
                }

                (long, long, long, long) newCount = (countViaNothing, countViaDac, countViaFft, countViaBoth);

                if (newCount.Item1 == 0)
                {
                    newCount = newCount with { Item1 = 1 };
                }

                if (newCount != currentCount)
                {
                    pathsToNode[node] = newCount;
                    updatedValues = true;
                }
            }
        }

        return pathsToNode["out"].Item4;
    }
    
    private DirectedGraph<string> ParseInput(IList<string> input)
    {
        DirectedGraph<string> graph = new();
        foreach (var line in input)
        {
            string[] data = line.Split(": ");
            string source = data[0];
            string[] neighbors = data[1].Split(" ");
            foreach (var dest in neighbors)
            {
                graph.AddEdge(source, dest);
            }
        }

        return graph;
    }

    private DirectedGraph<string> MinimizeGraph(DirectedGraph<string> graph, string startNode)
    {
        AdjacencyList<string> graphNeighbors = graph.GetAdjacencyList();
        
        ISet<string> reachableNodes = new HashSet<string>();
        Queue<string> toVisit = new Queue<string>();
        reachableNodes.Add(startNode);
        toVisit.Enqueue(startNode);

        while (toVisit.Count > 0)
        {
            string current = toVisit.Dequeue();
            ISet<string> neighbors = graphNeighbors.GetNeighbors(current);
            foreach (var next in neighbors)
            {
                if (!reachableNodes.Contains(next))
                {
                    reachableNodes.Add(next);
                    toVisit.Enqueue(next);
                }
            }
        }

        DirectedGraph<string> minimizedGraph = new DirectedGraph<string>();
        foreach (var node in reachableNodes)
        {
            foreach (var neighbor in graphNeighbors.GetNeighbors(node))
            {
                if (reachableNodes.Contains(neighbor))
                {
                    minimizedGraph.AddEdge(node, neighbor);
                }
            }
        }

        return minimizedGraph;
    }
}