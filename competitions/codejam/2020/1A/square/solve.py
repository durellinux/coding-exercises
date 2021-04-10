
def preprocess(board, R, C):
    data = dict()
    for r in range(R):
        for c in range(C):
            h = []
            if r - 1 > 0:
                h.append(board[r-1][c])
            else:
                h.append(None)
            if r + 1 < R:
                h.append(board[r+1][c])
            else:
                h.append(None)

            v = []
            if c - 1 > 0:
                v.append(board[r][c-1])
            else:
                v.append(None)
            if c + 1 < R:
                v.append(board[r][c+1])
            else:
                v.append(None)

            data["[" + str(r) + "-" + str(c) + "]"] = [board[r][c], h, v]


def competitionDone(round, R, C):
    for r in range(R):
        values = set()
        for c in range(C):
            if round[r][c] is not None:
                values.add(round[r][c])

        if len(values) > 1:
            return False

    for c in range(C):
        values = set()
        for r in range(R):
            if round[r][c] is not None:
                values.add(round[r][c])

        if len(values) > 1:
            return False

    return True


def scoreRound(round, R, C):
    score = 0
    for r in range(R):
        for c in range(C):
            if round[r][c] is not None:
                score += round[r][c]

    return score


def simulateRound(board, R, C):
    winRows = set()
    winCols = set()

    for r in range(R):
        values = set()
        for c in range(C):
            if board[r][c] is not None:
                values.add(board[r][c])

        win = max(values)

        for c in range(C):
            if board[r][c] == win:
                winRows.add((r, c))

    for c in range(C):
        values = set()
        for r in range(R):
            if board[r][c] is not None:
                values.add(board[r][c])

        win = max(values)

        for r in range(R):
            if board[r][c] == win:
                winCols.add((r, c))

    winners = winRows.intersection(winCols)

    nextBoard = [[None for c in range(C)] for r in range(R)]
    for w in winners:
        nextBoard[w[0]][w[1]] = board[w[0]][w[1]]

    return nextBoard


def solve(board, R, C):
    competitionScore = 0
    currentRound = board
    while not competitionDone(currentRound, R, C):
        print currentRound
        competitionScore += scoreRound(currentRound, R, C)
        currentRound = simulateRound(currentRound, R, C)
        print currentRound
        print competitionDone(currentRound, R, C)


    competitionScore += scoreRound(currentRound, R, C)
    return competitionScore

if __name__ == "__main__":
    T = int(raw_input())

    for i in range(T):
        [R, C] = map(int, raw_input().split(" "))
        board = []
        for n in range(R):
            contestants = map(int, raw_input().split(" "))
            board.append(contestants)

        result = solve(board, R, C)

        print "Case #{}: {}".format(i + 1, result)

        if i == 2:
            exit(0)