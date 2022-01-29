import sys

def distance(dx, dy):
    return abs(dx) + abs(dy)

def solve(X, Y, path):
    dx = X
    dy = Y

    if distance(dx, dy) == 0:
        return 0

    for idx in range(len(path)):
        move = path[idx]

        if (move == 'N'):
            dy += 1
        elif (move == 'S'):
            dy -= 1
        elif (move == 'E'):
            dx += 1
        elif (move == 'W'):
            dx -= 1
        else:
            sys.exit(1)

        # print [idx + 1, dx, dy, distance(dx, dy), move]

        if distance(dx, dy) <= idx + 1:
            return idx + 1

    #
    #
    # print [len(path), dx, dy, distance(dx, dy)]
    # if distance(dx, dy) <= len(path):
    #     return len(path)

    return "IMPOSSIBLE"

if __name__ == "__main__":
    T = int(raw_input())

    for i in range(T):
        [Xs, Ys, Ps] = raw_input().split(" ")
        X = int(Xs)
        Y = int(Ys)
        path = list(Ps)

        result = solve(X, Y, path)

        print "Case #{}: {}".format(i + 1, result)

        # exit(0)
