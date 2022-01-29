
def solve(moves):
    program = [['R', 'P', 'S']] * 500
    moves.sort(key=lambda s: len(s))

    for m in moves:
        possibleWinning(m, program)

    winningPrograms = []
    for programMove in program:
        if len(programMove) == 1:
            winningPrograms.append(programMove)
            break
        if len(programMove) == 0:
            return "IMPOSSIBLE"
        winningPrograms.append(programMove)

    winner = []
    for move in winningPrograms:
        winner.append(move[0])

    return "".join(winner)


def possibleWinning(move, program):
    for mPos in range(len(program)):
        m = move[mPos % len(move)]
        forbidMove = computeForbid(m)

        programMove = program[mPos]
        if forbidMove in programMove:
            programMove.remove(forbidMove)

def computeForbid(move):
    if move == "R":
        return "S"
    if move == "S":
        return "P"
    if move == "P":
        return "R"

if __name__ == "__main__":
    T = int(raw_input())
    for t in range(T):
        K = int(raw_input())
        moves = []
        for robot in range(K):
            moves.append(list(raw_input()))
        solution = solve(moves)
        print "Case #{}: {}".format(t + 1, str(solution))