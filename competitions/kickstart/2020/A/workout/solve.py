import math

def solveTarget(deltas, K, target):
    remainK = K
    # print deltas
    # print [target, K]
    for d in deltas:
        if d > target:
            neededK = int(math.floor(d / target))
            if d % target == 0:
                neededK -= 1
            # print [d, neededK]
            remainK -= neededK

    if remainK >= 0:
        return True
    else:
        return False

def solveBinary(deltas, min, max, K):
    # print "======"
    # print [min, max]
    if max - min <= 1:
        return [min, max]

    target = (max + min) / 2
    solved = solveTarget(deltas, K, target)
    if solved:
        return solveBinary(deltas, min, target, K)
    else:
        return solveBinary(deltas, target, max, K)


def solve(sessions, K):
    deltas = []
    for s in range(0, len(sessions) - 1):
        deltas.append(sessions[s + 1] - sessions[s])

    [minV, maxV] = solveBinary(deltas, 1, max(deltas), K)
    if solveTarget(deltas, K, minV):
        return minV
    return maxV


if __name__ == "__main__":
    T = int(raw_input())

    for t in range(T):
        [N, K] = map(int, raw_input().split(" "))
        sessions = map(int, raw_input().split(" "))

        solution = solve(sessions, K)
        print "Case #{}: {}".format(t + 1, solution)
