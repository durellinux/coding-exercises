import math


def bruteForce(numbers, memoized, position, currentSum):
    if position >= len(numbers):
        return 0

    move = numbers[position]
    newSum = currentSum + move
    root = 0 if newSum < 0 else math.sqrt(newSum)
    counts = 0 if int(root) ** 2 != newSum else 1

    return counts + bruteForce(numbers, memoized, position + 1, newSum)


def solve(numbers):
    # memoized = dict()
    # for n in range(len(array)):
    #     memoized[n] = [0, 0]

    count = 0
    for i in range(len(numbers)):
        count += bruteForce(numbers, {}, i, 0)

    return count

if __name__ == "__main__":
    T = int(raw_input())

    for t in range(T):
        N = int(raw_input())
        array = map(int, raw_input().split(" "))

        solution = solve(array)
        print "Case #{}: {}".format(t + 1, solution)