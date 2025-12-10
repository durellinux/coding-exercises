namespace src.gianlucadurelli.adventofcode.year2025;
using System.Linq;
using Google.OrTools.Init;
using Google.OrTools.LinearSolver;

using Cache = Dictionary<(long, long), long>;

public class Day10
{
    private readonly long[] _primes = new long[]
    {
        1001069, 1001081, 1001087, 1001089, 1001093, 1001107, 1001123, 1001153, 1001159, 1001173,
        1001177, 1001191, 1001197, 1001219, 1001237, 1001267, 1001279, 1001291, 1001303, 1001311,
        1001321, 1001323, 1001327, 1001347, 1001353, 1001369, 1001381, 1001387, 1001389, 1001401,
        1001411, 1001431, 1001447, 1001459, 1001467, 1001491, 1001501, 1001527, 1001531, 1001549,
        1001551, 1001563, 1001569, 1001587, 1001593, 1001621, 1001629, 1001639, 1001659, 1001669,
        1001683, 1001687, 1001713, 1001723, 1001743, 1001783, 1001797, 1001801, 1001807, 1001809,
        1001821, 1001831, 1001839, 1001911, 1001933, 1001941, 1001947, 1001953, 1001977, 1001981,
        1001983, 1001989, 1002017, 1002049, 1002061, 1002073, 1002077, 1002083, 1002091, 1002101,
        1002109, 1002121, 1002143, 1002149, 1002151, 1002173, 1002191, 1002227, 1002241, 1002247,
        1002257, 1002259, 1002263, 1002289, 1002299, 1002341, 1002343, 1002347, 1002349, 1002359,
        1002361, 1002377, 1002403, 1002427, 1002433, 1002451, 1002457, 1002467, 1002481, 1002487,
        1002493, 1002503, 1002511, 1002517, 1002523, 1002527, 1002553, 1002569, 1002577, 1002583
    };

    private long _bestFound = int.MaxValue;

    private record Machine(IList<int> Lights, IList<IList<int>> Buttons, IList<int> JoltageRequirements);

    public long Part1(IList<string> input)
    {
        long result = 0;
        IList<Machine> machines = ParseInput(input);
        foreach (Machine machine in machines)
        {
            _bestFound = int.MaxValue;
            Dictionary<int, int> maxButtonPresses = MaxButtonPresses1(machine.Buttons.Count);
            Cache cache = new Cache();
            long buttonsCacheKey = MyHash(maxButtonPresses);
            long goal = MakeNumber(machine.Lights);
            IList<long> buttonsEncoded = machine.Buttons.Select(MakeNumber).ToList(); 
            result += SolveDP(goal, buttonsEncoded, 0, cache, maxButtonPresses, buttonsCacheKey, 0, 0);
        }

        return result;
    }
    
    public long Part2(IList<string> input)
    {
        long result = 0;
        IList<Machine> machines = ParseInput(input);
        int count = 0;
        foreach (Machine machine in machines)
        {
            _bestFound = int.MaxValue;
            Dictionary<int, int> maxButtonPresses = MaxButtonPresses2(machine);
            long solution = LinearSolver(machine, maxButtonPresses);
            result += solution;
            Console.WriteLine($"Solved case: {count} => {solution}");
            
            count++;
        }

        return result;
        
    }

    /**
     * Build and solve a MILP problem.
     *
     * Variables: x_i => number of time the button i is pressed
     * Objective: min sum(x_i) for all i
     * Constraints:
     * switch_i: sum(x_b) = jolt[i] for all joltRequirements i, for all b | button b activates switch i
     */
    private long LinearSolver(Machine machine, Dictionary<int, int> maxButtonPresses)
    {
        Solver solver = Solver.CreateSolver("SCIP");
        if (solver is null)
        {
            Console.WriteLine("Could not create solver GLOP");
            return -1;
        }

        // Create the variables: x_i => #times button i is pressed
        IList<Variable> variables = new List<Variable>();
        for (int i = 0; i < machine.Buttons.Count; i++)
        {
            Variable v = solver.MakeIntVar(0.0, maxButtonPresses[i], $"x{i}");
            variables.Add(v);
        }

        // Create constraints on how many times a switch has to be activated.
        // Sums the variables x_i, for all buttons i that activates the switch 
        for (int reqId = 0; reqId < machine.JoltageRequirements.Count; reqId++)
        {
            int requiredValue = machine.JoltageRequirements[reqId];
            Constraint maxButtonClicks = solver.MakeConstraint(
                requiredValue, requiredValue, $"switch_{reqId}");
            for (int buttonId = 0; buttonId < machine.Buttons.Count; buttonId++)
            {
                if (machine.Buttons[buttonId].Contains(reqId))
                {
                    maxButtonClicks.SetCoefficient(variables[buttonId], 1);
                }
            }
        }

        // Create the objecting function: min sum(x_i)
        Objective objective = solver.Objective();
        foreach (var variable in variables)
        {
            objective.SetCoefficient(variable, 1);
            
        }
        objective.SetMinimization();

        Solver.ResultStatus resultStatus = solver.Solve();
        if (resultStatus != Solver.ResultStatus.OPTIMAL)
        {
            Console.WriteLine("The problem does not have an optimal solution!");
            if (resultStatus == Solver.ResultStatus.FEASIBLE)
            {
                Console.WriteLine("A potentially suboptimal solution was found");
            }
            else
            {
                Console.WriteLine("The solver could not solve the problem.");
                return -1;
            }
        }

        Console.WriteLine("Solution value = " + solver.Objective().Value());
        return (long)(solver.Objective().Value());
    }

    private Dictionary<int, int> MaxButtonPresses1(int buttonsCount)
    {
        Dictionary<int, int> maxButtonPresses = new Dictionary<int, int>();

        for (int bId = 0; bId < buttonsCount; bId++)
        {
            maxButtonPresses.Add(bId, 2);            
        }

        return maxButtonPresses;
    }
    
    private Dictionary<int, int> MaxButtonPresses2(Machine machine)
    {
        Dictionary<int, int> maxButtonPresses = new Dictionary<int, int>();
        IList<int> requirements = machine.JoltageRequirements;

        for (int bId = 0; bId < machine.Buttons.Count; bId++)
        {
            IList<int> button = machine.Buttons[bId];
            int maxPresses = button.Select(b => requirements[b]).Min();
            maxButtonPresses.Add(bId, maxPresses);            
        }

        return maxButtonPresses;
    }

    private long SolveDP(long goal, IList<long> buttons, long lightsStatus, Cache cache, Dictionary<int, int> maxButtonPresses, long buttonsCacheKey, int presses, int currentButton)
    {
        if (cache.ContainsKey((lightsStatus, buttonsCacheKey)))
        {
            return cache[(lightsStatus, buttonsCacheKey)];
        }

        if (lightsStatus == goal)
        {
            cache.Add((lightsStatus, buttonsCacheKey), 0);
            return cache[(lightsStatus, buttonsCacheKey)];
        }

        if (currentButton == buttons.Count)
        {
            return int.MaxValue;
        }

        if (presses > _bestFound)
        {
            return int.MaxValue;
        }

        long best = int.MaxValue;
        if (maxButtonPresses[currentButton] > 0)
        {
            maxButtonPresses[currentButton]--;
            long newLightsStatus = lightsStatus ^ buttons[currentButton];
            long result = SolveDP(goal, buttons, newLightsStatus, cache, maxButtonPresses, buttonsCacheKey - _primes[currentButton], presses + 1, currentButton);
            maxButtonPresses[currentButton]++;
            if (result < best)
            {
                best = result;
            }
        }

        long noOpResult = SolveDP(goal, buttons, lightsStatus, cache, maxButtonPresses, buttonsCacheKey, presses, currentButton + 1);
        if (noOpResult < best)
        {
            best = noOpResult;
        }
        
        if (!cache.ContainsKey((lightsStatus, buttonsCacheKey)) || cache[(lightsStatus, buttonsCacheKey)] > best + 1)
        {
            cache[(lightsStatus, buttonsCacheKey)] = best + 1;
            if (presses + best < _bestFound)
            {
                _bestFound = presses + best;
            }
        }

        return cache[(lightsStatus, buttonsCacheKey)];
    }

    private IList<Machine> ParseInput(IList<string> input)
    {
        IList<Machine> machines = new List<Machine>();
        
        foreach (string line in input)
        {
            IList<int> lights = new List<int>();
            IList<IList<int>> buttons = new List<IList<int>>();
            IList<int> joltageRequirements = new List<int>();
            
            string[] parts = line.Split(" ");
            for (int pId = 0; pId < parts.Length; pId++)
            {
                if (pId == 0)
                {
                    lights = ParseLights(parts[pId]);
                } else if (pId == parts.Length - 1)
                {
                    joltageRequirements = ParseJoltageRequirements(parts[pId]);
                }
                else
                {
                    IList<int> button = ParseButton(parts[pId]);
                    buttons.Add(button);
                }
            }

            machines.Add(new Machine(lights, buttons, joltageRequirements));
        }

        return machines;
    }

    private IList<int> ParseLights(string lights)
    {
        IList<int> bits = new List<int>();
        for (int pos = 1; pos < lights.Length - 1; pos++)
        {
            if (lights[pos] == '#')
            {
                bits.Add(pos - 1);
            }
        }

        return bits;
    }

    private IList<int> ParseButton(string buttonStr)
    {
        IList<int> button = new List<int>();
        string values = buttonStr.Substring(1, buttonStr.Length - 2);
        string[] numbers = values.Split(",");
        foreach (var number in numbers)
        {
            button.Add(int.Parse(number));
        }

        return button;
    }

    private IList<int> ParseJoltageRequirements(string joltageRequirements)
    {
        IList<int> requirements = new List<int>();
        string values = joltageRequirements.Substring(1, joltageRequirements.Length - 2);
        string[] numbers = values.Split(",");
        for (int requirementId = 0; requirementId < numbers.Length; requirementId++)
        {
            int number = int.Parse(numbers[requirementId]);
            requirements.Add(number);
        }

        return requirements;
    }

    private long MakeNumber(IList<int> onBits)
    {
        int value = 0;
        foreach (var bit in onBits)
        {
            value |= 1 << bit;
        }

        return value;
    }

    private long MyHash(Dictionary<int, int> maxButtonPress)
    {
        long value = 0;
        foreach (var pair in maxButtonPress)
        {
            long factor = _primes[pair.Key];
            value += factor * pair.Value;
        }
        return value;
    }
}