import sys

def reverse(L, i, j):
    p0 = L[:i]
    p1 = L[i:j+1]
    p2 = L[j+1:]
    p1.reverse()
    if i != j:
        return p0 + p1 + p2

    return L

def solve(L, N):
    result = 0

    for i in range(N - 1):
        v = min(L[i:])
        j = L.index(v)
        result += j - i + 1
        L = reverse(L, i, j)

    return result

if __name__ == "__main__":
    T = int(input())

    for t in range(T):
        N = int(input())
        L = map(int, input().split(" "))

        result = solve(L, N)
        # debug(brokenBits)
        print("Case #{}: {}".format(t + 1, result))
