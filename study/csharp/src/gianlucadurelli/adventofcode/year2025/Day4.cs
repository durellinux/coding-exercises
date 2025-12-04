namespace src.gianlucadurelli.adventofcode.year2025;

public record Position(int Row, int Col);

public class Day4
{
    public int Part1(IList<string> input)
    {
        ISet<Position> paperRolls = new HashSet<Position>();
        for (int r = 0; r < input.Count; r++)
        {
            char[] chars = input[r].ToCharArray();
            for (int c = 0; c < chars.Length; c++)
            {
                if (chars[c] == '@')
                {
                    paperRolls.Add(new(r, c));
                }
            }
        }

        return GetAccessibleRolls(paperRolls).Count;
    }
    
    public int Part2(IList<string> input)
    {
        int removableRolls = 0;
        
        ISet<Position> paperRolls = new HashSet<Position>();
        for (int r = 0; r < input.Count; r++)
        {
            char[] chars = input[r].ToCharArray();
            for (int c = 0; c < chars.Length; c++)
            {
                if (chars[c] == '@')
                {
                    paperRolls.Add(new(r, c));
                }
            }
        }

        bool canRemoveRolls = true;
        while (canRemoveRolls)
        {
            ISet<Position> paperRollsToRemove = GetAccessibleRolls(paperRolls);

            foreach (Position roll in paperRollsToRemove)
            {
                paperRolls.Remove(roll);
                removableRolls++;
            }

            canRemoveRolls = paperRollsToRemove.Count > 0;
        }

        return removableRolls;
    }
    
    
    public int PartBonus(IList<string> input)
    {
        int iterations = 0;
        
        ISet<Position> paperRolls = new HashSet<Position>();
        for (int r = 0; r < input.Count; r++)
        {
            char[] chars = input[r].ToCharArray();
            for (int c = 0; c < chars.Length; c++)
            {
                if (chars[c] == '@')
                {
                    paperRolls.Add(new(r, c));
                }
            }
        }

        bool canRemoveRolls = true;
        while (canRemoveRolls)
        {
            iterations++;
            ISet<Position> paperRollsToRemove = GetAccessibleRolls(paperRolls);

            foreach (Position roll in paperRollsToRemove)
            {
                paperRolls.Remove(roll);
            }

            canRemoveRolls = paperRollsToRemove.Count > 0;
        }

        return iterations;
    }

    private ISet<Position> GetAccessibleRolls(ISet<Position> paperRolls)
    {
        ISet<Position> accessibleRolls = new HashSet<Position>();
        foreach (Position roll in paperRolls)
        {
            if (CountSurroundingRolls(roll, paperRolls) < 4)
            {
                accessibleRolls.Add(roll);
            }
        }
        
        return accessibleRolls;
    }

    private int CountSurroundingRolls(Position position, ISet<Position> paperRolls)
    {
        int rolls = 0;
        for (int dr = -1; dr <= 1; dr++)
        {
            for (int dc = -1; dc <= 1; dc++)
            {
                if (dr != 0 || dc != 0)
                {
                    rolls += paperRolls.Contains(new(position.Row + dr, position.Col + dc)) ? 1 : 0;
                }
            }
        }
        
        return rolls;
    }
}