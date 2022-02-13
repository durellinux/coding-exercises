namespace src.gianlucadurelli.leetcode;

public class FindTheCelebrity277
{
    int[,] graph;

    public FindTheCelebrity277(int[,] graph)
    {
        this.graph = graph;
    }

    private bool Knows(int a, int b)
    {
        return graph[a, b] == 1;
    }

    public int FindCelebrity(int n)
    {
        int[,] knowGraph = new int[n, n];

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (i == j)
                {
                    knowGraph[i, j] = 1;
                }
                else
                {
                    knowGraph[i, j] = Knows(i, j) ? 1 : 0;
                }
            }
        }

        HashSet<int> whoIsknownByEveyOne = new HashSet<int>();
        HashSet<int> whoKnowsNoBody = new HashSet<int>();

        for (int i = 0; i < n; i++)
        {
            bool knownByEveryOne = true;
            for (int j = 0; j < n; j++)
            {
                if (knowGraph[j, i] == 0)
                {
                    knownByEveryOne = false;
                    break;
                }
            }

            if (knownByEveryOne)
            {
                whoIsknownByEveyOne.Add(i);
            }
        }
        
        for (int i = 0; i < n; i++)
        {
            bool knowsSomeone = false;
            for (int j = 0; j < n; j++)
            {
                if (i != j && knowGraph[i, j] == 1)
                {
                    knowsSomeone = true;
                    break;
                }
            }

            if (!knowsSomeone)
            {
                whoKnowsNoBody.Add(i);
            }
        }

        whoKnowsNoBody.IntersectWith(whoIsknownByEveyOne);

        if (whoKnowsNoBody.Count != 1)
        {
            return -1;
        }

        return whoKnowsNoBody.First();
    }
}