
def solve(numbers, K):
    toSearch = K
    found = 0

    for n in numbers:
        if n != toSearch:
            toSearch = K

        if n == toSearch:
            toSearch -= 1

        if toSearch == 0:
            found += 1
            toSearch = K

    return found

if __name__ == "__main__":
    T = int(raw_input())

    for t in range(T):
        [N, K] = map(int, raw_input().split(" "))
        numbers = map(int, raw_input().split(" "))

        solution = solve(numbers, K)
        print "Case #{}: {}".format(t + 1, solution)
