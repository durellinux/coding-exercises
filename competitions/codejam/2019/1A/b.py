def bruteforceSolver(R, C):
    mat = [[0 for i in range(C)] for l in range(R)]


    for r in range(R):
        for c in range(C):
            steps=[[r, c]]
            mat[r][c] = 1
            [possible, solution] = withBacktrack(mat, steps, R, C, 0)

            if possible:
                return [possible, solution]
            else:
                mat[r][c] = 0

    return [False, []]

def withBacktrack(mat, steps, R, C, solIndex):
    if solIndex == R * C - 1:
        return [True, steps]

    for r in range(R):
        for c in range(C):
            if mat[r][c] == 0 and isValidMove(steps[-1], [r, c], R, C):
                steps.append([r, c])
                mat[r][c] = 1
                [possible, solution] = withBacktrack(mat, steps, R, C, solIndex + 1)
                if possible:
                    return [possible, solution]
                else:
                    steps.pop()
                    mat[r][c] = 0

    return [False, []]


def isValidMove(prevStep, newStep, R, C):
    if prevStep[0] == newStep[0] or prevStep[1] == newStep[1]:
        return False

    for r in range(0, max(R, C) + 1):
        newPos1 = [prevStep[0] + r, prevStep[1] + r]
        newPos2 = [prevStep[0] - r, prevStep[1] - r]
        newPos3 = [prevStep[0] + r, prevStep[1] - r]
        newPos4 = [prevStep[0] - r, prevStep[1] + r]
        if isEqual(newPos1, newStep) or isEqual(newPos2, newStep) or isEqual(newPos3, newStep) or isEqual(newPos4, newStep):
            return False

    return True

def isEqual(pos1, pos2):
    return pos1[0] == pos2[0] and pos1[1] == pos2[1]

if __name__ == "__main__":
    T = int(raw_input())
    for t in range(T):
        [r, c] = map(int, raw_input().strip().split(" "))
        [possible, solution] = bruteforceSolver(r, c)
        if not possible:
            print "Case #{}: {}".format(t + 1, "IMPOSSIBLE")
        else:
            print "Case #{}: {}".format(t + 1, "POSSIBLE")
            for s in solution:
                sol = [s[0] + 1, s[1] + 1]
                print " ".join(map(str,sol))