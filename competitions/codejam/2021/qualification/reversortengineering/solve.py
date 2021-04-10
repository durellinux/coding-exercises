import sys

def reverse(L, i, j):
    p0 = L[:i]
    p1 = L[i:j+1]
    p2 = L[j+1:]
    p1.reverse()
    if i != j:
        return p0 + p1 + p2

    return L


def solve(N, C):
    values = range(1, N + 1)
    costs = [0 for i in range(0, N-1)]

    costToAllocate = C

    for i in range(N - 2, -1, -1):
        maxCost = N - i
        maxFeasible = costToAllocate - i

        # print "--------"
        # print i
        # print costToAllocate
        # print maxCost
        # print maxFeasible

        if maxFeasible <= 0:
            return "IMPOSSIBLE"

        costs[i] = min(maxCost, maxFeasible)
        costToAllocate -= costs[i]

    if costToAllocate != 0:
        return "IMPOSSIBLE"

    # print costs
    for i in range(N - 2, -1, -1):
        j = i + costs[i] - 1
        values = reverse(values, i, j)
        # print [i, j, values]

    return " ".join(map(str, values))


if __name__ == "__main__":
    T = int(input())

    for t in range(T):
        [N, C] = map(int, input().split(" "))

        result = solve(N, C)
        print("Case #{}: {}".format(t + 1, result))
