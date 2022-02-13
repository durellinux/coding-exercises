namespace src.gianlucadurelli.leetcode;

public class LRUCache
{

    class Node
    {
        public readonly int key;
        public int value;
        public Node? next;
        public Node? prev;

        public Node(int key, int value)
        {
            this.key = key;
            this.value = value;
            next = null;
            prev = null;
        }
    }

    private Node? head;
    private Node? tail;
    private Dictionary<int, Node> entries;
    private int elems;
    private int capacity;

    public LRUCache(int capacity)
    {
        this.capacity = capacity;
        elems = 0;
        head = null;
        tail = null;
        entries = new Dictionary<int, Node>();
    }
    
    public int Get(int key) {
        if (!entries.ContainsKey(key))
        {
            return -1;
        }

        Node keyNode = entries[key];
        refresh(keyNode, keyNode.value);
        
        return keyNode.value;
    }

    public void Put(int key, int value)
    {
        if (entries.ContainsKey(key))
        {
            refresh(entries[key], value);
        }
        else
        {
            insertTail(key, value);

            if (elems > capacity)
            {
                remove(head);
            }
        }
    }

    private void refresh(Node accessedNode, int value)
    {
        remove(accessedNode);
        insertTail(accessedNode.key, value);
    }

    private void remove(Node node)
    {
        if (node.next == null && node.prev == null)
        {
            head = null;
            tail = null;
        } else if (node.next == null)
        {
            tail = node.prev;
            node.prev.next = null;
        } else if (node.prev == null)
        {
            head = node.next;
            node.next.prev = null;
        } else
        {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        elems -= 1;
        entries.Remove(node.key);
    }

    private void insertTail(int key, int value)
    {
        Node n = new Node(key, value);
        n.next = null;
        n.prev = tail;

        if (tail != null)
        {
            tail.next = n;
            tail = n;
        }
        else
        {
            head = n;
            tail = n;
        }

        elems += 1;
        entries[key] = n;
    }
}