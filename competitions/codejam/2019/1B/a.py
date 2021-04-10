def solve(P, Q, people):
    matrix = [[0 for i in range(0, Q)] for j in range(0, Q)]

    for p in people:
        x = p[0]
        y = p[1]
        d = p[2]

        for px in range(0, Q):
            for py in range(0, Q):
                if d == "N" and py > y:
                    matrix[px][py] += 1
                if d == "S" and py < y:
                    matrix[px][py] += 1
                if d == "W" and px < x:
                    matrix[px][py] += 1
                if d == "E" and px > x:
                    matrix[px][py] += 1

    bestCellCount = 0
    bestCell = [Q+1, Q+1]

    for px in range(0, Q):
        for py in range(0, Q):
            val = matrix[px][py]
            if val >= bestCellCount:
                bestCellCount = val


    for px in range(0, Q):
        for py in range(0, Q):
            val = matrix[px][py]
            if val == bestCellCount and isBetterCell([px, py], bestCell):
                bestCell = isBetterCell([px, py], bestCell)

    return bestCell

def isBetterCell(currentBest, nextCell):
    if nextCell[0] < currentBest[0]:
        return nextCell

    if nextCell[0] == currentBest[0] and nextCell[1] < currentBest[1]:
        return nextCell

    return currentBest

if __name__ == "__main__":
    T = int(raw_input())
    for t in range(T):
        [P, Q] = map(int, raw_input().split(' '))
        people = []
        for p in range(P):
            [X, Y, D] = raw_input().split(' ')
            people.append([int(X), int(Y), D])

        [x, y] = solve(P, Q+1, people)

        print "Case #{}: {}".format(t + 1, " ".join(map(str, [x, y])))


