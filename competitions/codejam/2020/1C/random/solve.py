
def solve(U, queries):
    letters = dict()
    
    for q in queries:
        for l in q[1]:
            if l not in letters:
                letters[l] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}

    for q in queries:
        if len(q[1]) == len(q[2]):
            letter = q[1][0]
            minValueLetter = int(q[2][0])

            for v in range(minValueLetter + 1, 10):
                if v in letters[letter]:
                    letters[letter].remove(v)

        notZero = q[1][0]
        if 0 in letters[notZero]:
            letters[notZero].remove(0)

    # print letters

    simplified = True

    lettersValue = dict()
    for l in letters:
        lettersValue[l] = None

    while simplified:
        found = False
        for l in letters:
            if len(letters[l]) == 1:
                found = True
                value = letters[l].pop()
                lettersValue[l] = value
                # print l + " equals to " + str(value)
                for q in letters:
                    if l != q:
                        if value in letters[q]:
                            letters[q].remove(value)

        simplified = found

    output_array = sorted(lettersValue, key=lettersValue.get)
    return "".join(output_array)

if __name__ == "__main__":
    T = int(raw_input())

    for t in range(T):
        U = int(raw_input())
        queries = list()
        for n in range(10000):
            [Q, R] = raw_input().split(" ")
            queries.append([int(Q), list(R), list(str(Q))])

        result = solve(U, queries)

        print "Case #{}: {}".format(t + 1, result)

        # exit(0)
