import sys

def solve(S, X, Y):
    if S.count("C") + S.count("J") == 0:
        return 0

    first = 0
    while S[first] == '?':
        first += 1

    for i in range(first, 0, -1):
        S[i] = S[first]

    for i in range(1, len(S)):
        if S[i] == '?':
            S[i] = S[i-1]

    Sstr = "".join(S)
    cj = Sstr.count("CJ")
    jc = Sstr.count("JC")
    return cj * X + jc * Y


if __name__ == "__main__":
    T = int(input())

    for t in range(T):
        data = input().split(" ")
        X = int(data[0])
        Y = int(data[1])
        S = list(data[2])

        result = solve(S, X, Y)
        print("Case #{}: {}".format(t + 1, result))
