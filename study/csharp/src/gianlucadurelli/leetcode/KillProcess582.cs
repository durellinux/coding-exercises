namespace src.gianlucadurelli.leetcode;

public class KillProcess582
{
    public IList<int> KillProcess(IList<int> pid, IList<int> ppid, int kill)
    {
        Dictionary<int, List<int>> children = new Dictionary<int, List<int>>();

        foreach (int p in pid)
        {
            children[p] = new List<int>();
        }

        for (int idx = 0; idx < ppid.Count; idx++)
        {
            int parent = ppid[idx];
            if (parent != 0) {
                children[parent].Add(pid[idx]);
            }
        }

        List<int> killedPids = new List<int>();

        Queue<int> toVisit = new Queue<int>();
        toVisit.Enqueue(kill);

        while (toVisit.Count > 0)
        {
            int currentPid = toVisit.Dequeue();
            killedPids.Add(currentPid);

            foreach (int child in children[currentPid])
            {
                toVisit.Enqueue(child);
            }
        }

        return killedPids;
    }
}