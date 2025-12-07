using System.Text.RegularExpressions;

namespace src.gianlucadurelli.adventofcode.year2025;

public class Day6
{
    private static Regex NUMBER_REGEX = new("(\\d+)");
    private static Regex OPERATIONS_REGEX = new("([+*])");

    public long Part1(IList<string> input)
    {
        var (numbers, operations) = ParseInput1(input);
        List<List<int>> operands = Transpose(numbers);
        return ComputeSolution(operands, operations);
    }

    public long Part2(IList<string> input)
    {
        var (operands, operations) = ParseInput2(input);
        return ComputeSolution(operands, operations);
    }

    private long ComputeSolution(List<List<int>> operands, IList<string> operations)
    {
        int totalOperations = operations.Count;

        long result = 0;
        for (int op = 0; op < totalOperations; op++)
        {
            string operation = operations[op];
            long operationResult = operation == "+" ? 0 : 1;
            foreach (int value in operands[op])
            {
                operationResult = operation == "+" ? operationResult + value : operationResult * value;
            }
            
            result += operationResult;

        }

        return result;
    }

    private (IList<IList<int>>, IList<string>) ParseInput1(IList<string> input)
    {
        IList<IList<int>> numbers = new List<IList<int>>();
        IList<string> operations = new List<string>();

        foreach (string line in input)
        {
            if (NUMBER_REGEX.IsMatch(line))
            {
                MatchCollection numbersMatches = NUMBER_REGEX.Matches(line);
                IList<int> values = new List<int>();
                foreach (Match match in numbersMatches)
                {
                    values.Add(int.Parse(match.Value));
                }
                numbers.Add(values);
            }
            else
            {
                MatchCollection operationsMatches = OPERATIONS_REGEX.Matches(line);
                foreach (Match match in operationsMatches)
                {
                    operations.Add(match.Value);
                }
            }
        }

        return (numbers, operations);
    }

    private (List<List<int>>, IList<string>) ParseInput2(IList<string> input)
    {
        List<List<int>> numbers = new List<List<int>>();
        IList<string> operations = new List<string>();
        IList<string> digitsLines = new List<string>();

        foreach (string line in input)
        {
            if (NUMBER_REGEX.IsMatch(line))
            {
                digitsLines.Add(line);
            }
            else
            {
                MatchCollection operationsMatches = OPERATIONS_REGEX.Matches(line);
                foreach (Match match in operationsMatches)
                {
                    operations.Add(match.Value);
                }
            }
        }

        int digitRows = digitsLines.Count;
        int digitCols = digitsLines[0].Length;
        char[,] digits = new char[digitCols, digitRows];
        for (int row = 0; row < digitRows; row++)
        {
            for (int col = 0; col < digitCols; col++)
            {
                digits[col, row] = digitsLines[row][col];
            }
        }

        List<int> values = new List<int>();
        for (int row = 0; row < digitCols; row++)
        {
            string stringVal = "";
            for (int col = 0; col < digitRows; col++)
            {
                stringVal += digits[row, col];
            }

            if (NUMBER_REGEX.IsMatch(stringVal))
            {
                values.Add(int.Parse(NUMBER_REGEX.Match(stringVal).Value));
            }
            else
            {
                numbers.Add(values);
                values = new List<int>();
            }
        }
        
        numbers.Add(values);

        return (numbers, operations);
    }

    private List<List<int>> Transpose(IList<IList<int>> matrix)
    {
        return matrix
            .SelectMany(inner => inner.Select((item, index) => new { item, index }))
            .GroupBy(i => i.index, i => i.item)
            .Select(g => g.ToList())
            .ToList();
    }
}