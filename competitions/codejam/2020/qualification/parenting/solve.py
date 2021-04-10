
def solve(tasks):
    sortedTasks = sorted(tasks, key=lambda x: x[0])

    J = 0
    C = 0

    solutionNotOrdered = []

    for [start, end, order] in sortedTasks:
        if J <= start:
            solutionNotOrdered.append(["J", order])
            J = end
        elif C <= start:
            solutionNotOrdered.append(["C", order])
            C = end
        else:
            return "IMPOSSIBLE"

    solutionOrdered = sorted(solutionNotOrdered, key=lambda x: x[1])
    solution = ""
    for [parent, order] in solutionOrdered:
        solution += parent

    return solution

if __name__ == "__main__":
    T = int(raw_input())

    for i in range(T):
        N = int(raw_input())
        tasks = []
        for n in range(N):
            row = map(int, raw_input().split(" "))
            row.append(n)
            tasks.append(row)

        solution = solve(tasks)
        print "Case #{}: {}".format(i + 1, solution)