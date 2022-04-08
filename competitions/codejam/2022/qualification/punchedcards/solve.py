def solve(R, C):
    result = []

    result.append(createRow('..+', '-+', C-1))
    result.append(createRow('.', '.|', C))
    result.append(createRow('+', '-+', C))

    for r in range(R - 1):
        result.append(createRow('|', '.|', C))
        result.append(createRow('+', '-+', C))

    return result


def createRow(prefix, block, reps):
    row = [prefix]
    for c in range(reps):
        row.append(block)

    return row


if __name__ == "__main__":
    T = int(input())

    for t in range(T):
        [R, C] = map(int, input().split(" "))

        result = solve(R, C)
        print("Case #{}:".format(t + 1))
        for r in result:
            print("".join(r))
