
def solve(dices):
    sorted_dices = sorted(dices)
    result = 0

    for i in sorted_dices:
        if result + 1 <= i:
            result += 1

    return result

if __name__ == "__main__":
    T = int(input())

    for t in range(T):
        D = int(input())
        dices = map(int, input().split(" "))
        result = solve(dices)
        print("Case #{}: {}".format(t + 1, str(result)))
