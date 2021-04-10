


if __name__ == "__main__":
    T = int(raw_input())
    for t in range(T):
        N = int(raw_input())
        words = []
        for w in range(N):
            words.append(raw_input())
        solution = solve(words)
        print "Case #{}: {}".format(t + 1, str(solution))