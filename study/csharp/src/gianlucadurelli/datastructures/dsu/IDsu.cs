namespace src.gianlucadurelli.datastructures.dsu;

public interface IDsu<T, TId>
{
    void MakeSet(T element);
    TId Find(T element);
    void Merge(TId elem1, TId elem2);
}