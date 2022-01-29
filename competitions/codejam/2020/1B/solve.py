
def dec2BinList(n):
    bin = []

    v = abs(n)

    while v > 0:
        bin.append(v % 2)
        v = v / 2

    return bin

def solve2(x, y):
    xBin = dec2BinList(x)
    yBin = dec2BinList(y)

    if abs(x) % 2 != 0 or abs(y) % 2 != 0:
        return []

    

    solution = []
    for idx in range(1, max(len(xBin), len(yBin))):
        vx = 0
        vy = 0
        if idx < len(xBin):
            vx = xBin[idx]

        if idx < len(yBin):
            vy = yBin[idx]

        if vx == vy:
            # print []
            return []

        if vx > vy:
            solution.append("X")
        else:
            solution.append("Y")

    return solution

def composeSolution(first, verticalMove, horizontalMove, solution):
    finalSolution = []
    finalSolution.append(first)

    for v in solution:
        if v == "Y":
            finalSolution.append(verticalMove)
        else:
            finalSolution.append(horizontalMove)

    return "".join(finalSolution)

def solve(X, Y):

    verticalMove = "N" if Y > 0 else "S"
    horizontalMove = "E" if X > 0 else "W"

    if (X % 2) == (Y % 2):
        return "IMPOSSIBLE"

    if (abs(X) == 0) and (abs(Y) == 1):
        return verticalMove

    if (abs(X) == 1) and (abs(Y) == 0):
        return horizontalMove

    if X % 2 != 0:
        sol = solve2(X + 1, Y)
        if len(sol) != 0:
            return composeSolution('W', verticalMove, horizontalMove, sol)

        sol = solve2(X - 1, Y)
        if len(sol) != 0:
            return composeSolution('E', verticalMove, horizontalMove, sol)

    if Y % 2 != 0:
        sol = solve2(X, Y + 1)
        if len(sol) != 0:
            return composeSolution('S', verticalMove, horizontalMove, sol)

        sol = solve2(X, Y - 1)
        if len(sol) != 0:
            return composeSolution('N', verticalMove, horizontalMove, sol)

    return "IMPOSSIBLE"


if __name__ == "__main__":
    T = int(raw_input())

    for i in range(T):
        [X, Y] = map(int, raw_input().split(" "))

        result = solve(X, Y)

        print "Case #{}: {}".format(i + 1, result)