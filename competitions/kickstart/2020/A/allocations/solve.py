
def solve(costs, B):
    spent = 0
    bought = 0
    ordered = sorted(costs)

    for c in ordered:
        if spent + c <= B:
            spent += c
            bought += 1

    return bought

if __name__ == "__main__":
    T = int(raw_input())

    for t in range(T):
        [N, B] = map(int, raw_input().split(" "))
        costs = map(int, raw_input().split(" "))

        solution = solve(costs, B)
        print "Case #{}: {}".format(t + 1, solution)
