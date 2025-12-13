using Google.OrTools.Sat;
using src.gianlucadurelli.adventofcode.utils;

namespace src.gianlucadurelli.adventofcode.year2025;

public class Day12
{
    private record StackOperation(int type, Coordinate Position, int PresentId, Shape Shape)
    {
        public static StackOperation Operation(Coordinate position, int presentId, Shape shape)
        {
            return new StackOperation(0, position, presentId, shape);
        }

        public static StackOperation MarkOccupied(Coordinate position, int presentId, Shape shape)
        {
            return new StackOperation(1, position, presentId, shape);
        }
        
        public static StackOperation MarkUnoccupied(Coordinate position, int presentId, Shape shape)
        {
            return new StackOperation(2, position, presentId, shape);
        }

        public bool IsExplore()
        {
            return type == 0;
        }

        public bool IsMarkOccupied()
        {
            return type == 1;
        }

        public bool IsMarkUnoccupied()
        {
            return type == 2;
        }
    }

    private record Shape(int Id, ISet<Coordinate> Coordinates);

    private record Present(IList<Shape> Shapes);

    private record Problem(int Rows, int Cols, IList<int> Requirements, IList<Present> Presents);
    
    public long Part1(IList<string> input)
    {
        int solution = 0;
        IList<Problem> problems = ParseInput(input);
        foreach (var problem in problems)
        {
            bool doFit = SolveProblemStack(problem);
            if (doFit)
            {
                solution += 1;
            }
        }

        return solution;
    }

    private ISet<Coordinate> AllCells(Problem problem)
    {
        ISet<Coordinate> allCells = new HashSet<Coordinate>();
        
        for (int r = 0; r < problem.Rows; r++)
        {
            for (int c = 0; c < problem.Cols; c++)
            {
                allCells.Add(new Coordinate(r, c));
            }
        }

        return allCells;
    }

    private bool SolveProblemStack(Problem problem)
    {
        ISet<Coordinate> availableCells = AllCells(problem);
        IList<int> presentsToPlace = problem.Requirements;
        int remainingPresents = presentsToPlace.Sum();
        int statusHash = 0;

        Stack<StackOperation> callStack = new Stack<StackOperation>();
        callStack.Push(StackOperation.Operation(new Coordinate(0, 0), 0, new Shape(-1, new HashSet<Coordinate>())));

        ISet<(int, long, Coordinate)> visited = new HashSet<(int, long, Coordinate)>();
        int iterations = 0;

        MultiCounter multiCounter = new(8, problem.Requirements.Count);

        while (callStack.Count > 0)
        {
            iterations++;
            var operation = callStack.Pop();
            var currentPosition = operation.Position;
            int operationPresentId = operation.PresentId;
            Shape operationShape = operation.Shape;

            if (operation.IsMarkOccupied())
            {
                statusHash ^= Place(currentPosition, availableCells, operationShape);
                presentsToPlace[operationPresentId]--;
                remainingPresents -= 1;
                multiCounter.Increment(operationPresentId);
                if (remainingPresents == 0)
                {
                    return true;
                }
                continue;
            }

            if (operation.IsMarkUnoccupied())
            {
                remainingPresents += 1;
                presentsToPlace[operationPresentId]++;
                statusHash ^= Remove(currentPosition, availableCells, operationShape);
                multiCounter.Decrement(operationPresentId);
                continue;
            }

            if (currentPosition.Row == problem.Rows)
            {
                continue;
            }
            
            if (StopHeuristic(problem, availableCells, presentsToPlace))
            {
                continue;
            }
            
            var visitedKey = (statusHash, multiCounter.GetValue(), currentPosition);
            if (!visited.Add(visitedKey))
            {
                continue;
            }

            callStack.Push(StackOperation.Operation(NextPosition(currentPosition, problem), operation.PresentId, operationShape));

            if (availableCells.Contains(currentPosition))
            {
                for (int presentId = 0; presentId < problem.Presents.Count; presentId++)
                {
                    if (presentsToPlace[presentId] > 0)
                    {
                        ISet<Shape> possibleShapes =
                            PlaceableShapes(currentPosition, availableCells, problem.Presents[presentId].Shapes);
                        foreach (var shape in possibleShapes)
                        {
                            callStack.Push(StackOperation.MarkUnoccupied(currentPosition, presentId, shape));
                            callStack.Push(StackOperation.Operation(NextPosition(currentPosition, problem), presentId, shape));
                            callStack.Push(StackOperation.MarkOccupied(currentPosition, presentId, shape));
                        }
                    }
                }
            }
        }

        return false;
    }

    private bool StopHeuristic(Problem problem, ISet<Coordinate> availableCells, IList<int> presentsToPlace)
    {
        int requiredCells = 0;
        for (int presentId = 0; presentId < presentsToPlace.Count; presentId++)
        {
            requiredCells += presentsToPlace[presentId] * problem.Presents[presentId].Shapes.First().Coordinates.Count;
        }

        return requiredCells > availableCells.Count;
    }

    private ISet<Shape> PlaceableShapes(Coordinate position, ISet<Coordinate> availableCells, IList<Shape> availableShapes)
    {
        ISet<Shape> possibleShapes = new HashSet<Shape>();
        foreach (var shape in availableShapes)
        {
            bool canPlace = true;
            foreach (Coordinate c in shape.Coordinates)
            {
                if (!availableCells.Contains(new Coordinate(position.Row + c.Row, position.Col + c.Col)))
                {
                    canPlace = false;
                    break;
                }
            }

            if (canPlace)
            {
                possibleShapes.Add(shape);
            }
        }

        return possibleShapes;
    }

    private Coordinate NextPosition(Coordinate position, Problem problem)
    {
        int nextCol = position.Col + 1;
        int nextRow = position.Row;
        if (nextCol == problem.Cols)
        {
            nextCol = 0;
            nextRow = position.Row + 1;
        }

        return new Coordinate(nextRow, nextCol);
    }

    private int Place(Coordinate position, ISet<Coordinate> availableCells, Shape shape)
    {
        return Apply(position, shape, availableCells.Remove);
    }
    
    private int Remove(Coordinate position, ISet<Coordinate> availableCells, Shape shape)
    {
        return Apply(position, shape, availableCells.Add);
    }

    private int Apply(Coordinate position, Shape shape, Func<Coordinate, bool> applyFunction)
    {
        int hash = 0;
        foreach (Coordinate c in shape.Coordinates)
        {
            Coordinate coordinate = new Coordinate(position.Row + c.Row, position.Col + c.Col);
            applyFunction(coordinate);
            hash ^= coordinate.GetHashCode();
        }

        return hash;
    }

    private IList<Problem> ParseInput(IList<string> input)
    {
        IList<Problem> problems = new List<Problem>();
        bool parsingPresents = true;
        IList<Present> presents = new List<Present>();
        Shape currentShape = new Shape(-1,new HashSet<Coordinate>());
        int currentRow = 0;
        foreach (var line in input)
        {
            if (parsingPresents && line == "")
            {
                presents.Add(TurnAndFlip(currentShape));
                currentShape = new Shape(-1,new HashSet<Coordinate>());
                currentRow = 0;
            }
            
            if (line.Contains("x"))
            {
                parsingPresents = false;
            }

            if (parsingPresents && line.Contains("#"))
            {
                char[] row = line.ToCharArray();
                for (int cId = 0; cId < row.Length; cId++)
                {
                    if (row[cId] == '#')
                    {
                        currentShape.Coordinates.Add(new Coordinate(currentRow, cId));
                    }
                }

                currentRow++;
            }
            else if(!parsingPresents)
            {
                string[] data = line.Split(": ");
                string[] size = data[0].Split("x");
                IList<int> requirements = data[1].Split(" ").ToList().Select(int.Parse).ToList();
                problems.Add(new Problem(int.Parse(size[1]), int.Parse(size[0]), requirements, presents));
            }
        }

        return problems;
    }

    private Present TurnAndFlip(Shape shape)
    {
        Dictionary<Coordinate, Coordinate> rotate = GetRotateTransform();
        Dictionary<Coordinate, Coordinate> flipVertically = GetFlipVerticalTransform();
        Dictionary<Coordinate, Coordinate> flipHorizontally = GetFlipHorizontallyTransform();
        
        IList<Shape> shapes = new List<Shape>();
        ISet<int> visitedShapes = new HashSet<int>();
        Queue<Shape> toVisit = new Queue<Shape>();

        shapes.Add(shape with{Id=shapes.Count});
        visitedShapes.Add(ShapeHash(shape));
        toVisit.Enqueue(shape);
        while (toVisit.Count > 0)
        {
            Shape currentShape = toVisit.Dequeue();
            IList<Shape> newShapes = new List<Shape>();
            newShapes.Add(Apply(currentShape, rotate));
            newShapes.Add(Apply(currentShape, flipVertically));
            newShapes.Add(Apply(currentShape, flipHorizontally));

            foreach (var newShape in newShapes)
            {
                int newShapeHash = ShapeHash(newShape);
                if (!visitedShapes.Contains(newShapeHash))
                {
                    visitedShapes.Add(newShapeHash);
                    shapes.Add(newShape with{Id=shapes.Count});
                    toVisit.Enqueue(newShape);
                }
            }
        }

        return new Present(shapes);
    }

    private Dictionary<Coordinate, Coordinate> GetRotateTransform()
    {
        return new Dictionary<Coordinate, Coordinate>
        {
            {new Coordinate(0, 0), new Coordinate(0, 2)},
            {new Coordinate(0, 1), new Coordinate(1, 2)},
            {new Coordinate(0, 2), new Coordinate(2, 2)},
            {new Coordinate(1, 0), new Coordinate(0, 1)},
            {new Coordinate(1, 1), new Coordinate(1, 1)},
            {new Coordinate(1, 2), new Coordinate(2, 1)},
            {new Coordinate(2, 0), new Coordinate(0, 0)},
            {new Coordinate(2, 1), new Coordinate(1, 0)},
            {new Coordinate(2, 2), new Coordinate(2, 0)},
        };
    }

    private Dictionary<Coordinate, Coordinate> GetFlipVerticalTransform()
    {
        return new Dictionary<Coordinate, Coordinate>
        {
            { new Coordinate(0, 0), new Coordinate(2, 0) },
            { new Coordinate(0, 1), new Coordinate(2, 1) },
            { new Coordinate(0, 2), new Coordinate(2, 2) },
            { new Coordinate(1, 0), new Coordinate(1, 0) },
            { new Coordinate(1, 1), new Coordinate(1, 1) },
            { new Coordinate(1, 2), new Coordinate(1, 2) },
            { new Coordinate(2, 0), new Coordinate(0, 0) },
            { new Coordinate(2, 1), new Coordinate(0, 1) },
            { new Coordinate(2, 2), new Coordinate(0, 2) },
        };
    }

    private Dictionary<Coordinate, Coordinate> GetFlipHorizontallyTransform()
    {
        return new Dictionary<Coordinate, Coordinate>
        {
            { new Coordinate(0, 0), new Coordinate(0, 2) },
            { new Coordinate(0, 1), new Coordinate(0, 1) },
            { new Coordinate(0, 2), new Coordinate(0, 0) },
            { new Coordinate(1, 0), new Coordinate(1, 2) },
            { new Coordinate(1, 1), new Coordinate(1, 1) },
            { new Coordinate(1, 2), new Coordinate(1, 0) },
            { new Coordinate(2, 0), new Coordinate(2, 2) },
            { new Coordinate(2, 1), new Coordinate(2, 1) },
            { new Coordinate(2, 2), new Coordinate(2, 0) },
        };
    }

    private Shape Apply(Shape shape, Dictionary<Coordinate, Coordinate> transform)
    {
        return new Shape(-1, shape.Coordinates.Select(c => transform[c]).ToHashSet());
    }

    private int ShapeHash(Shape shape)
    {
        return shape.Coordinates.Select(c => 1 << (c.Row * 3 + c.Col)).Sum();
    }
}