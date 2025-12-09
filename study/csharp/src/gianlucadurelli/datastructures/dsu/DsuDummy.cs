namespace src.gianlucadurelli.datastructures.dsu;

public class DsuDummy<T>: IDsu<T, long> where T : notnull
{
    private long lastId = 0;
    private Dictionary<T, long> elementToSetId = new();
    Dictionary<long, ISet<T>> setIdToElements = new();

    public void MakeSet(T element)
    {
        if (elementToSetId.ContainsKey(element))
        {
            throw new ArgumentException("Element is already in a set");
        }

        long id = lastId++;
        lastId += 1;
        elementToSetId.Add(element, id);
        ISet<T> newSet = new HashSet<T>();
        newSet.Add(element);
        setIdToElements.Add(id, newSet);
    }

    public long Find(T element)
    {
        EnsurePresent(element);
        return elementToSetId[element];
    }

    public void Merge(long elem1, long elem2)
    {
        if (!setIdToElements.ContainsKey(elem1) || !setIdToElements.ContainsKey(elem2))
        {
            throw new ArgumentException("One or more set ids is not valid");
        }

        if (elem1 == elem2)
        {
            return;
        }

        long bigId = elem1;
        long smallId = elem2;
        ISet<T> bigSet = setIdToElements[elem1];
        ISet<T> smallSet = setIdToElements[elem2];

        if (bigSet.Count < smallSet.Count)
        {
            (bigSet, smallSet) = (smallSet, bigSet);
            (bigId, smallId) = (smallId, bigId);
        }

        bigSet.UnionWith(smallSet);
        setIdToElements.Remove(smallId);
        foreach (var elem in smallSet)
        {
            elementToSetId[elem] = bigId;
        }
    }

    public ISet<T> Get(long elem)
    {
        if (!setIdToElements.ContainsKey(elem))
        {
            throw new ArgumentException("Invalid set id");
        }

        return setIdToElements[elem];
    }

    private void EnsurePresent(T element)
    {
        if (!elementToSetId.ContainsKey(element))
        {
            MakeSet(element);
        }
    }
}