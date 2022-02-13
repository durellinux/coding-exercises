using NUnit.Framework;
using src.gianlucadurelli.leetcode;

namespace tests.gianlucadurelli.leetcode;

public class LRUCache146Test
{
    [Test]
    public void TestCase1()
    {
        LRUCache cache = new LRUCache(2);
        cache.Put(1, 1);
        cache.Put(2, 2);
        Assert.AreEqual(1, cache.Get(1));
        cache.Put(3, 3);
        Assert.AreEqual(-1, cache.Get(2));
        cache.Put(4, 4);
        Assert.AreEqual(-1, cache.Get(1));
        Assert.AreEqual(3, cache.Get(3));
        Assert.AreEqual(4, cache.Get(4));
    }
    
    [Test]
    public void TestCase2()
    {
        LRUCache cache = new LRUCache(2);
        cache.Put(2, 1);
        cache.Put(2, 2);
        Assert.AreEqual(2, cache.Get(2));
        cache.Put(1, 1);
        cache.Put(4, 1);
        Assert.AreEqual(-1, cache.Get(2));
    }
}