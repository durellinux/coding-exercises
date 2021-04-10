import math
import operator

def solvePerSliceDimension(slicesDict, missingTarget, targetSize):
    newDict = dict()
    cuts = 0
    missing = missingTarget

    for s in slicesDict.keys():
        if s > targetSize:
            newDict[s] = slicesDict[s]

    # print [newDict, missingTarget, targetSize]

    while missing > 0 and len(newDict.keys()) > 0:
        for s in newDict.keys():
            if s == 2 * targetSize:
                obtainableSlices = 2 * newDict[s]
                # print [s, obtainableSlices]
                if obtainableSlices >= missing:
                    cuts += int(math.ceil(missing * 1.0 / 2))
                    missing = 0
                    # print "Cutting: " + str(s) + " - New cuts: " + str(cuts)
                    continue
                else:
                    cuts += newDict[s]
                    missing -= obtainableSlices
                    del newDict[s]

        cuttedSome = False
        for s in newDict.keys():
            if s % targetSize == 0:
                cuttedSome = True
                obtainableSlices = newDict[s]
                if obtainableSlices > missing:
                    missing = 0
                    cuts += missing
                else:
                    cuts += newDict[s]
                    missing -= obtainableSlices
                    newDict[s - targetSize] = newDict[s]
                    del newDict[s]

        if not cuttedSome:
            for s in newDict.keys():
                obtainableSlices = newDict[s]
                if obtainableSlices > missing:
                    missing = 0
                    cuts += missing
                else:
                    cuts += newDict[s]
                    missing -= obtainableSlices
                    if s - targetSize > targetSize:
                        newDict[s - targetSize] = newDict[s]
                    del newDict[s]

    return cuts if missing == 0 else -1

def solve(D, slices):
    dictSlices = dict()
    for s in slices:
        if s not in dictSlices:
            dictSlices[s] = 0

        dictSlices[s] += 1

    for s in dictSlices.keys():
        if dictSlices[s] == D:
            return 0

    minCuts = D - 1

    sortedSlices = sorted(dictSlices.items(), key=operator.itemgetter(1))
    reversedSort = reversed(sortedSlices)

    # print [D, len(slices), sortedSlices]
    for s in reversedSort:
        cuts = solvePerSliceDimension(dictSlices, D - s[1], s[0])
        if cuts >= 0 and cuts < minCuts:
            minCuts = cuts

    return minCuts


if __name__ == "__main__":
    T = int(raw_input())

    for t in range(T):
        [N, D] = map(int, raw_input().split(" "))

        slices = list()
        while len(slices) < N:
            some = map(int, raw_input().split(" "))
            slices += some

        result = solve(D, slices)

        print "Case #{}: {}".format(t + 1, result)

        # exit(0)
