def solve(moves):
    program = []

    # lengts = []
    # for m in moves:
    #     if len(m) not in lengts:
    #         lengts.append(len(m))
    #
    # lenProduct = 1
    # for l in lengts:
    #     lenProduct = lenProduct * l

    maxLength = 500

    found = False

    while len(program) < maxLength:
        currentMove = ["R", "P", "S"]
        robotMoves = []
        for m in moves:
            play = m[len(program) % len(m)]
            forbid = computeForbid(play)
            if forbid in currentMove:
                currentMove.remove(forbid)

            if play not in robotMoves:
                robotMoves.append(play)

        if len(robotMoves) == 1:
            program.append(computeWin(robotMoves[0]))
            found = True
            break

        if len(currentMove) == 0:
            return "IMPOSSIBLE"

        # TODO: Strategy here?
        program.append(currentMove[0])

    if not found:
        return "IMPOSSIBLE"

    return "".join(program)

def computeForbid(move):
    if move == "R":
        return "S"
    if move == "S":
        return "P"
    if move == "P":
        return "R"


def computeWin(move):
    if move == "R":
        return "P"
    if move == "S":
        return "R"
    if move == "P":
        return "S"


if __name__ == "__main__":
    T = int(raw_input())
    for t in range(T):
        K = int(raw_input())
        moves = []
        for robot in range(K):
            moves.append(list(raw_input()))
        solution = solve(moves)
        print "Case #{}: {}".format(t + 1, str(solution))