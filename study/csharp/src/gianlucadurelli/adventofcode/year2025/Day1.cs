namespace src.gianlucadurelli.adventofcode.year2025;

public class Day1
{
    public int Part1(IList<string> input)
    {
        int current = 50;
        int result = 0;

        foreach (var value in input)
        {
            int diff = Int32.Parse(value.Substring(1));
            current = TurnKnob(current, value[0], diff);

            if (current == 0)
            {
                result += 1;
            }
        }

        return result;
    }

    public int Part2(IList<string> input)
    {
        int current = 50;
        int clicks = 0;

        foreach (var value in input)
        {
            int diff = Int32.Parse(value.Substring(1));
            clicks += diff / 100;
            diff %= 100;

            if (current != 0 && ((value[0] == 'L' && current - diff <= 0) || (value[0] == 'R' && current + diff >= 100)))
            {
                clicks += 1;
            }

            current = TurnKnob(current, value[0], diff);
        }

        return clicks;
    }

    private int TurnKnob(int current, char direction, int value)
    {
        int diff = value;
        if (direction == 'L')
        {
            diff *= -1;
        }

        return (current + 100 + diff) % 100;
    }
}