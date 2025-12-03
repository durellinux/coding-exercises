namespace src.gianlucadurelli.adventofcode.year2025;
using System.Linq;

public class Day3
{
    public long Part1(IList<string> banks)
    {
        long result = 0;

        foreach (string bankStr in banks)
        {
            result += SolveBank(bankStr, 2);
        }
        
        return result;
    }
    
    public long Part2(IList<string> banks)
    {
        long result = 0;

        foreach (string bankStr in banks)
        {
            result += SolveBank(bankStr, 12);
        }
        
        return result;
    }

    private long SolveBank(string bankStr, int toPick)
    {
        long max = 0;
        int[] bank = Array.ConvertAll(bankStr.ToArray(), c => int.Parse(c.ToString()));
        int[] currentMax = Array.ConvertAll(bankStr.Substring(bankStr.Length - toPick).ToArray(), c => int.Parse(c.ToString()));
        
        for (int idx = bankStr.Length - 1 - toPick; idx >= 0; idx--)
        {
            PushValue(bank[idx], currentMax);
        }

        for (int idx = 0; idx < currentMax.Length; idx++)
        {
            max += currentMax[idx] * (long)Math.Pow(10, toPick - idx - 1);
        }

        return max;
    }

    private void PushValue(int value, int[] currentMax)
    {
        int currentNumber = value;
        int currentIndex = 0;

        while (currentIndex < currentMax.Length && currentNumber >= currentMax[currentIndex])
        {
            (currentMax[currentIndex], currentNumber) = (currentNumber, currentMax[currentIndex]);
            currentIndex++;
        }
    }

}