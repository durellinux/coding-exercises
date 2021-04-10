def solve(S):
    machines = {
        'I': 0,
        'i': 0,
    }

    result = 0

    for c in S:

        if c == 'I':
            machines['I'] += 1

        if c == 'i':
            machines['i'] += 1

        if c == 'O':
            if machines['I'] > 0:
                result += 1
                machines['I'] -= 1
            elif machines['i'] > 0:
                machines['i'] -= 1
            else:
                exit(-1)

        if c == 'o':
            if machines['i'] > 0:
                machines['i'] -= 1
            elif machines['I'] > 0:
                machines['I'] -= 1
            else:
                exit(-1)

    return result

if __name__ == "__main__":
    T = int(input())

    for t in range(T):
        S = input()

        result = solve(S)
        print("Case #{}: {}".format(t + 1, result))
