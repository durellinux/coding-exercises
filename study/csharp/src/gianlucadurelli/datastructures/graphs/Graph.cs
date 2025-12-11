namespace src.gianlucadurelli.datastructures.graphs;

public class AdjacencyList<T> where T : notnull
{
    private readonly Dictionary<T, ISet<T>> neighbors;

    public AdjacencyList()
    {
        neighbors = new Dictionary<T, ISet<T>>();
    }

    public void AddEdge(T source, T dest)
    {
        if (!neighbors.ContainsKey(source))
        {
            neighbors.Add(source, new HashSet<T>());
        }

        if (!neighbors.ContainsKey(dest))
        {
            neighbors.Add(dest, new HashSet<T>());
        }

        neighbors[source].Add(dest);
    }

    public ISet<T> GetNodes()
    {
        return neighbors.Keys.ToHashSet();
    }

    public ISet<T> GetNeighbors(T node)
    {
        return neighbors[node];
    }
}

public class DirectedGraph<T> where T : notnull
{
    private readonly AdjacencyList<T> adjacencyList;
    private readonly AdjacencyList<T> incomingEdges;
    private readonly ISet<(T, T)> edges;

    public DirectedGraph()
    {
        adjacencyList = new();
        incomingEdges = new();
        edges = new HashSet<(T, T)>();
    }

    public void AddEdge(T source, T dest)
    {
        edges.Add((source, dest));
        adjacencyList.AddEdge(source, dest);
        incomingEdges.AddEdge(dest, source);
    }

    public ISet<(T, T)> GetEdges()
    {
        return edges;
    }

    public ISet<T> GetNodes()
    {
        return adjacencyList.GetNodes();
    }

    public AdjacencyList<T> GetAdjacencyList()
    {
        return adjacencyList;
    }

    public AdjacencyList<T> GetIncomingEdges()
    {
        return incomingEdges;
    }
}