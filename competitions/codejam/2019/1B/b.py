import sys


def solve(V):
    sys.stderr.write("V: " + str(V) + '\n')
    tmpV = V

    x6 = tmpV / (2**54)
    tmpV = tmpV % (2**54)

    x3 = tmpV / (2**45)
    tmpV = tmpV % (2 ** 45)

    x2 = tmpV / (2**37)
    tmpV = tmpV % (2 ** 37)

    x4 = tmpV / (2**18)
    tmpV = tmpV % (2 ** 18)

    x1 = tmpV / (2**11)
    tmpV = tmpV % (2 ** 11)

    x5 = tmpV / (2 ** 2)
    tmpV = tmpV % (2 ** 2)

    sys.stderr.write(str(tmpV) + '\n')

    return [x1, x2, x3, x4, x5, x6]

if __name__ == "__main__":
    [T, W] = map(int, raw_input().split(' '))
    for t in range(T):
        print 2
        sys.stdout.flush()

        V = int(raw_input())
        solution = solve(V)
        print " ".join(map(str, solution))
        sys.stdout.flush()
        val = int(raw_input())
        if val != 1:
            sys.exit(0)

    # for D in range(1, 501):
    #
    #     vals = []
    #     for i in range(1, 7):
    #         vals.append(int(D/i) % 63)
    #
    #     # print vals
    #     sortedVals = sorted(vals)
    #
    #     valid = True
    #     for i in range(0, len(sortedVals) - 1):
    #         if sortedVals[i+1] - sortedVals[i] < 7:
    #             valid = False
    #
    #     if (valid):
    #         print D
    #         print vals
    #         print sortedVals