def isStable(wall):
    rows = len(wall)
    cols = len(wall[0])

    for r in range(rows):
        for c in range(cols):
            below = r + 1
            if below < R:
                if wall[r][c] != "" and wall[r + 1][c] == "":
                    return False

    return True


def place(wall, currentSolution, move):
    newSolution = [row[:] for row in currentSolution]
    rows = len(wall)
    cols = len(wall[0])

    for r in range(rows):
        for c in range(cols):
            if wall[r][c] == move:
                newSolution[r][c] = move

    return newSolution

def getMoves(wall):
    moves = []
    rows = len(wall)
    cols = len(wall[0])

    for r in range(rows):
        curR = R - r - 1
        for c in range(cols):
            if wall[r][c] not in moves:
                moves.append(wall[r][c])

    return moves

def solve(wall, currentSolution, steps, moves):
    if not isStable(currentSolution):
        return []

    if len(steps) == len(moves):
        return steps

    for m in moves:
        if m not in steps:
            newSolution = place(wall, currentSolution, m)
            newSteps = list(steps)
            newSteps.append(m)
            possibleSolution = solve(wall, newSolution, newSteps, moves)
            if possibleSolution != []:
                return possibleSolution

    return []


if __name__ == "__main__":
    T = int(raw_input())

    for t in range(T):
        wall = []
        [R, C] = map(int, raw_input().split(" "))

        for r in range(R):
            row = list(raw_input())
            wall.append(row)

        blankWall = [["" for i in range(C)] for j in range(R)]

        solution = solve(wall, blankWall, [], getMoves(wall))
        result = "-1"
        if solution != []:
            result = "".join(solution)
        print "Case #{}: {}".format(t + 1, result)
