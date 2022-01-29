
def preprocess(R, C):
    data = dict()
    for r in range(R):
        for c in range(C):
            nodeData = [(r, c)]
            if r - 1 >= 0:
                nodeData.append((r-1, c))
            else:
                nodeData.append(None)
            if r + 1 < R:
                nodeData.append((r+1, c))
            else:
                nodeData.append(None)

            if c - 1 >= 0:
                nodeData.append((r, c-1))
            else:
                nodeData.append(None)
            if c + 1 < C:
                nodeData.append((r, c+1))
            else:
                nodeData.append(None)

            data[(r, c)] = nodeData

    return data


def competitionDone(board, round):
    for k in round.keys():
        valIdx = round[k][0]
        value = board[valIdx[0]][valIdx[1]]
        for pos in round[k][1:3]:
            if pos is not None and value != board[pos[0]][pos[1]]:
                return False
        for pos in round[k][3:]:
            if pos is not None and value != board[pos[0]][pos[1]]:
                return False

    return True

def scoreRound(round):
    score = 0
    for k in round.keys():
        valIdx = round[k][0]
        score += board[valIdx[0]][valIdx[1]]

    return score

def willLose(board, entry):
    me = entry[0]
    neigh = entry[1:]

    value = board[me[0]][me[1]]
    sumNeigh = 0
    neighs = 0
    for n in neigh:
        if n is not None:
            sumNeigh += board[n[0]][n[1]]
            neighs += 1

    avgNeigh = 0
    if neighs > 0:
        avgNeigh = sumNeigh * 1.0 / neighs

    return value < avgNeigh

def simulateRound(board, round):
    losingNodes = set()

    for k in round.keys():
        if willLose(board, round[k]):
            losingNodes.add(k)

    for l in losingNodes:
        for k in round:
            if l in round[k]:
                pos = round[k].index(l)
                round[k][pos] = round[l][pos]

        round.pop(l, None)

    return [round, len(losingNodes) == 0]

def solve(board, R, C):
    competitionScore = 0
    currentRound = preprocess(R, C)

    competitionScore += scoreRound(currentRound)
    [currentRound, competitionDone] = simulateRound(board, currentRound)

    while not competitionDone:

        competitionScore += scoreRound(currentRound)
        [currentRound, competitionDone] = simulateRound(board, currentRound)


    # competitionScore += scoreRound(currentRound)

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
