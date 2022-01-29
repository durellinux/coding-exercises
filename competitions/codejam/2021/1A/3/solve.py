
def solve(L, N):
    pass

if __name__ == "__main__":
    T = int(input())

    for t in range(T):
        N = int(input())
        L = map(int, input().split(" "))

        result = solve(L, N)
        # debug(brokenBits)
        print("Case #{}: {}".format(t + 1, result))
