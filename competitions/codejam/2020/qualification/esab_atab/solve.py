import sys

def query(pos):
    print (pos)
    sys.stdout.flush()
    v = int(raw_input())
    sys.stderr.write("Query: " + str(pos) + " -> " + str(v) + "\n")
    sys.stderr.flush()
    return v

def negate(bits):
    sys.stderr.write("My Flip\n")
    sys.stderr.flush()
    for bPos in range(len(bits)):
        if bits[bPos] == 0:
            bits[bPos] = 1
        else:
            bits[bPos] = 0

def reverse(bits):
    sys.stderr.write("My Reverse\n")
    sys.stderr.flush()
    for bPos in range(len(bits) / 2 + 1):
        tmp = bits[bPos]
        bits[bPos] = bits[len(bits) - bPos - 1]
        bits[len(bits) - bPos - 1] = tmp

def solveCase(B):
    queries = 0
    bits = [0 for i in range(B)]

    queryPos = 0
    queriesBeforeChange = 10
    sameValue = []
    differentValue = []

    while queries < 150:
        currentQueries = 0
        sys.stderr.write("Starting with query: " + str(queries) + '\n')
        sys.stderr.flush()
        while (currentQueries < queriesBeforeChange/2*2):
            b1 = query(queryPos + 1)
            b2 = query(B - queryPos)
            bits[queryPos] = b1
            bits[B - queryPos - 1] = b2
            if (b1 == b2):
                # sys.stderr.write("Same value: " + str(b1) + " - " + str(b2) + "\n")
                # sys.stderr.flush()
                sameValue.append(queryPos)
            else:
                differentValue.append(queryPos)

            queryPos += 1
            currentQueries += 2

            if (queryPos >= B / 2):
                return bits

        sys.stderr.write("Useless queries: " + str(queries) + '\n')
        sys.stderr.flush()

        while (currentQueries < queriesBeforeChange):
            query(1)
            currentQueries += 1

        queries += currentQueries

        sys.stderr.write("Change queries: " + str(queries) + '\n')
        sys.stderr.flush()

        if (len(sameValue) > 0 and len(differentValue) > 0):
            q0 = sameValue[0]
            b0 = bits[q0]
            v0 = query(q0 + 1)
            q1 = differentValue[0]
            b1 = bits[q1]
            v1 = query(q1 + 1)

            if (b0 == v0 and b1 == v1):
                pass
            if (b0 == v0 and b1 != v1):
                reverse(bits)
                # reverse
            if (b0 != v0 and b1 != v1):
                negate(bits)
                # flip
            if (b0 != v0 and b1 == v1):
                reverse(bits)
                negate(bits)
                # both

            queries += 2
            queriesBeforeChange = 8

        if (len(sameValue) == 0):
            q0 = differentValue[0]
            b0 = bits[q0]
            v0 = query(q0 + 1)

            if (b0 != v0):
                negate(bits)

            queries += 1
            queriesBeforeChange = 9

        if (len(differentValue) == 0):
            q0 = sameValue[0]
            b0 = bits[q0]
            v0 = query(q0 + 1)

            if (b0 != v0):
                negate(bits)

            queries += 1
            queriesBeforeChange = 9


if __name__ == "__main__":
    [T, B] = map(int, raw_input().split(" "))

    for t in range(T):
        bits = solveCase(B)
        # debug(brokenBits)
        print "".join(map(str, bits))
        sys.stdout.flush()

        response = raw_input()
        if response == "N":
            exit(1)
