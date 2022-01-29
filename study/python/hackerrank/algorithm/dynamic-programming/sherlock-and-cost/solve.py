#!/bin/python

def cost(B):
    cache = dict()
    for i in range(len(B)):
        val = B[i]
        cache[i] = dict()
        cache[i][1] = -1
        cache[i][val] = -1

    for index in range(len(B)):
        val = B[index]
        for v1 in [1, val]:
            if index == 0:
                cache[index][v1] = 0
            else:
                bestCost = -1
                for v0 in [1, B[index -1]]:
                    cost0 = cache[index - 1][v0]
                    cost1 = cost0 + abs(v0 - v1)
                    if cost1 > bestCost:
                        bestCost = cost1
                cache[index][v1] = bestCost

    bestCost = -1
    for lastV in [1, B[-1]]:
        cost = cache[len(B) - 1][lastV]
        if cost > bestCost:
            bestCost = cost

    return bestCost

if __name__ == '__main__':
    # fptr = open(os.environ['OUTPUT_PATH'], 'w')

    t = int(raw_input())

    for t_itr in xrange(t):
        n = int(raw_input())

        B = map(int, raw_input().rstrip().split())

        result = cost(B)

        print result
        # fptr.write(str(result) + '\n')

    # fptr.close()

# if __name__ == '__main__':
#     B = [100, 2, 100, 2, 100]
#     # B = [1, 2, 3]
#     print cost(B)
