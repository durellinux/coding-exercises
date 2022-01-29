
def computeTrace(M, N):
    trace = 0
    for i in range(N):
        trace += M[i][i]

    return trace

def solve(M, N):
    trace = computeTrace(M, N)

    cols = 0
    rows = 0

    for rowIndex in range(N):
        colSet = set()
        rowSet = set()
        for colIndex in range(N):
            colSet.add(M[colIndex][rowIndex])
            rowSet.add(M[rowIndex][colIndex])

        if (len(rowSet) != N):
            rows += 1

        if (len(colSet) != N):
            cols += 1

    return [trace, rows, cols]

if __name__ == "__main__":
    T = int(raw_input())

    for i in range(T):
        N = int(raw_input())
        M = []
        for n in range(N):
            row = map(int, raw_input().split(" "))
            M.append(row)

        [trace, rows, cols] = solve(M, N)
        print "Case #{}: {} {} {}".format(i + 1, trace, rows, cols)