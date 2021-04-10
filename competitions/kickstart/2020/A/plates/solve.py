
def bruteforce(stacks, precomputed, K, P, picked, currentStack, solutionValue, memoized):
    if picked == P:
        return solutionValue
    if currentStack == len(stacks):
        return 0
    if picked > P:
        return 0

    if memoized[currentStack][picked] is not None:
        return solutionValue + memoized[currentStack][picked]

    curSolution = 0
    for k in range(K + 1):
        s = bruteforce(stacks, precomputed, K, P, picked + k, currentStack + 1, solutionValue + precomputed[currentStack][k], memoized)
        if s > curSolution:
            curSolution = s

    memoized[currentStack][picked] = curSolution - solutionValue
    return curSolution


def solve(stacks, P, K):
    # print P
    # print stacks

    precomputed = [[0 for i in range(K + 1)] for i in range(len(stacks))]
    memoized = [[None for i in range(P + 1)] for i in range(len(stacks))]
    # print stacks
    for sId in range(len(stacks)):
        s = stacks[sId]
        for k in range(K):
            precomputed[sId][k + 1] = precomputed[sId][k] + s[k]

    solution = bruteforce(stacks, precomputed, K, P, 0, 0, 0, memoized)

    # print memoized

    return solution

if __name__ == "__main__":
    T = int(raw_input())

    for t in range(T):
        [N, K, P] = map(int, raw_input().split(" "))

        stacks = []
        for n in range(N):
            st = map(int, raw_input().split(" "))
            stacks.append(st)

        solution = solve(stacks, P, K)
        print "Case #{}: {}".format(t + 1, solution)
