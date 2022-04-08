
def solve(colors):
    target = 10**6
    solution = []

    for c in range(len(colors[0])):
        can_use = min(colors[0][c], colors[1][c], colors[2][c])
        to_use = min(can_use, target)
        solution.append(to_use)
        target -= to_use

    if target == 0:
        return solution

    return ["IMPOSSIBLE"]


if __name__ == "__main__":
    T = int(input())

    for t in range(T):
        colors = []
        for i in range(3):
            [C, M, Y, K] = map(int, input().split(" "))
            colors.append([C, M, Y, K])

        result = solve(colors)
        print("Case #{}: {}".format(t + 1, " ".join(map(str, result))))
