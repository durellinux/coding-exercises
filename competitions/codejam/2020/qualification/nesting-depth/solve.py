
def solve(digits):
    result = ''
    currentDepth = 0

    for d in digits:
        if (currentDepth > d):
            result = result + ')' * (currentDepth - d)
            currentDepth = d
        if (currentDepth < d):
            result = result + '(' * (d - currentDepth)
            currentDepth = d

        result += str(d)

    if (currentDepth > 0):
        result = result + ')' * currentDepth
    return result


if __name__ == "__main__":
    T = int(raw_input())

    for i in range(T):
        digits = map(int, list(raw_input()))
        result = solve(digits)

        print "Case #{}: {}".format(i + 1, result)