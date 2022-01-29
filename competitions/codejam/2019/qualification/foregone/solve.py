
def solve():
    n = int(raw_input())
    for i in range(n):
        value = raw_input()
        [a, b] = computeSolution(value)

        print "Case #{}: {} {}".format(i + 1, a, b)

def computeSolution(valueStr):
    intList = map(int, (list(valueStr)))
    solution0 = []
    solution1 = []

    for v in intList:
        if v == 4:
            solution0.append(3)
            solution1.append(1)
        else:
            solution0.append(v)
            if len(solution1) > 0:
                solution1.append(0)

    value0 = "".join(map(str, solution0))
    value1 = "".join(map(str, solution1))

    return [value0, value1]


if __name__ == "__main__":
    solve()