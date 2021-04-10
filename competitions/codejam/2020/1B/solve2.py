def dec2BinList(n):
    bin = []

    v = abs(n)

    while v > 0:
        bin.append(v % 2)
        v = v / 2

    return bin

def solveRec(x, y, curPos):

    if curPos > 33:
        return "IMPOSSIBLE"

    if x == 0 and y == 0:
        return "DONE"

    xBin = dec2BinList(x)
    yBin = dec2BinList(y)

    vx = 0
    vy = 0
    if curPos < len(xBin):
        vx = xBin[curPos]

    if curPos < len(yBin):
        vy = yBin[curPos]

    if vx == vy:
        # print []
        return "IMPOSSIBLE"

    if vx > vy:
        tmp1 = solveRec(x - 2**curPos, y, curPos + 1)
        if tmp1 == "DONE":
            return "E"

        tmp2 = solveRec(x + 2**curPos, y, curPos + 1)
        if tmp2 == "DONE":
            return "W"

        if tmp1 != "IMPOSSIBLE" and tmp2 == "IMPOSSIBLE":
            return "E" + tmp1

        if tmp1 == "IMPOSSIBLE" and tmp2 != "IMPOSSIBLE":
            return "W" + tmp2

        return "IMPOSSIBLE"

    else:
        tmp1 = solveRec(x, y - 2**curPos, curPos + 1)
        if tmp1 == "DONE":
            return "N"

        tmp2 = solveRec(x, y + 2**curPos, curPos + 1)
        if tmp2 == "DONE":
            return "S"

        if tmp1 != "IMPOSSIBLE" and tmp2 == "IMPOSSIBLE":
            return "N" + tmp1

        if tmp1 == "IMPOSSIBLE" and tmp2 != "IMPOSSIBLE":
            return "S" + tmp2

        return "IMPOSSIBLE"


def solve(X, Y):
    if (X % 2) == (Y % 2):
        return "IMPOSSIBLE"

    sol = solveRec(X, Y, 0)

    if sol == "":
        return "IMPOSSIBLE"

    return sol


if __name__ == "__main__":
    T = int(raw_input())

    for i in range(T):
        [X, Y] = map(int, raw_input().split(" "))

        result = solve(X, Y)

        print "Case #{}: {}".format(i + 1, result)