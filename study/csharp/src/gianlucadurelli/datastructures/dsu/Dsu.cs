namespace src.gianlucadurelli.datastructures.dsu;


public class Dsu<T>: IDsu<T, T> where T : notnull
{
    private record DsuNode
    {
        public DsuNode(T value, T parent, int size)
        {
            Value = value;
            Parent = parent;
            Size = size;
        }

        public T Value { get; init; }
        public T Parent { get; set; }
        public int Size { get; set; }
    }

    private Dictionary<T, DsuNode> elementToNode = new();

    public void MakeSet(T element)
    {
        
        if (elementToNode.ContainsKey(element))
        {
            throw new ArgumentException("Element is already in a set");
        }
        
        elementToNode[element] = new DsuNode(element, element, 1);
    }

    public T Find(T element)
    {
        EnsurePresent(element);
        DsuNode root = elementToNode[element];
        while (!root.Value.Equals(root.Parent))
        {
            root = elementToNode[root.Parent];
        }

        // Consider second pass flattening tree
        
        return root.Value;
    }

    public void Merge(T elem1, T elem2)
    {
        DsuNode bigNode = elementToNode[elem1];
        DsuNode smallNode = elementToNode[elem2];

        if (bigNode == smallNode)
        {
            return;
        }

        if (bigNode.Size < smallNode.Size)
        {
            (bigNode, smallNode) = (smallNode, bigNode);
        }

        smallNode.Parent = bigNode.Value;
        bigNode.Size += smallNode.Size;
    }

    private void EnsurePresent(T element)
    {
        if (!elementToNode.ContainsKey(element))
        {
            MakeSet(element);
        }
    }

    public int GetSize(T elem)
    {
        T set = Find(elem);
        return elementToNode[set].Size;
    }
}