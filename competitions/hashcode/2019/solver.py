import copy
import random
import sys


def generate_slides(entries):
    slides = []
    for i in xrange(len(entries)):
        if (entries[i][0] == 'H'):
            slides.append(([i], entries[i][2]))
        else:
            for j in xrange(i + 4, len(entries)):
                if (entries[j][0] == 'V'):
                    slides.append(([i, j], list(set().union(entries[i][2], entries[j][2]))))
    return slides


def generate_slides_filter(entries):
    slides = []
    for i in xrange(len(entries)):
        if (entries[i][0] == 'H'):
            slides.append(([i], entries[i][2]))
        else:
            for j in xrange(i + 4, len(entries)):
                if (entries[j][0] == 'V'):
                    slides.append(([i, j], list(set().union(entries[i][2], entries[j][2]))))
                    break

    return slides

def compute_interest(s1, s2):
    t1 = set(s1[1])
    t2 = set(s2[1])
    t1t2 = len(t1.intersection(t2))
    t1nott2 = len(t1.difference(t2))
    t2nott1 = len(t2.difference(t1))
    return min(t1t2, t1nott2, t2nott1)


def printSolution(solution, slides):
    print len(solution)
    for s in solution:
        print " ".join(map(str, slides[s][0]))

def computeInterestMatrix(slides):
    matrix = [[0 for i in range(len(slides))] for j in range(len(slides))]

    for i0 in xrange(len(slides)):
        for i1 in xrange(i0 + 1, len(slides)):
            matrix[i0][i1] = compute_interest(slides[i0], slides[i1])
            matrix[i1][i0] = compute_interest(slides[i0], slides[i1])

    return matrix

def computeDictionaryData(slides, matrix):
    dictionaryData = dict()

    for sId in xrange(len(slides)):
        slideData = [slides[sId]]
        neighbor = []
        for sN in xrange(len(slides)):
            if matrix[sId][sN] > 0:
                neighbor.append(sN)
        slideData.append(neighbor)
        slideData.append(False)
        dictionaryData[sId] = slideData

    return dictionaryData

def computeDictionaryData2(slides):
    dictionaryData = dict()

    for sId in xrange(len(slides)):
        dictionaryData[sId] = True

    return dictionaryData


def parseInput(fileName):
    fileLines = open(fileName).readlines()[1:]
    rows = len(fileLines)
    entries = []
    for r1 in range(0, rows):
        line = fileLines[r1].strip()
        [orientation, number_of_tags, tags] = [s for s in line.split(' ', 2)]
        tags = [s for s in tags.split(' ')]
        entries.append([orientation, number_of_tags, tags])

    return entries

def solve(dictionaryData, matrix):
    bestPath = []
    bestPathValue = 0

    for i in xrange(len(dictionaryData)):
        tmpDict = copy.deepcopy(dictionaryData)

        path = [i]
        pathValue = 0
        tmpDict[i][2] = True

        nextId = pickMove(tmpDict, i)
        if (nextId != None):
            path.append(nextId)
            pathValue = pathValue + matrix[i][nextId]
            tmpDict[nextId][2] = True
        while(nextId != None):
            nextId = pickMove(tmpDict, nextId)
            if (nextId != None):
                path.append(nextId)
                pathValue = pathValue + matrix[i][nextId]
                tmpDict[nextId][2] = True

        if pathValue > bestPathValue:
            bestPathValue = pathValue
            bestPath = path

    return [bestPath, bestPathValue]

def pickMove(dictionaryData, slideId):
    bestId = None
    for n in dictionaryData[slideId][1]:
        if dictionaryData[n][2] == False:
            if bestId == None or matrix[slideId][n] > matrix[slideId][bestId]:
                bestId = n

    return bestId

def randomIdiot(sl, dictionaryData):
    bestPath = []
    bestValue = 0

    for i in range(int(sys.argv[2])):
        tmpDict = copy.deepcopy(dictionaryData)
        scrambleArray = scrambled(range(len(sl)))

        start = scrambleArray[0]
        path = [start]
        value = 0

        [path, value, notUsedSlides] = solveIdiot(sl, tmpDict, scrambleArray, path, value)

        while True:
            [pathR, valueR, notUsedSlides] = solveIdiot(sl, tmpDict, notUsedSlides, path, value)
            if len(pathR) > len(path):
                path = pathR
                value = valueR
            else:
                break

        if (value > bestValue):
            bestPath = path
            bestValue = value

    return bestPath

def scrambled(orig):
    dest = orig[:]
    random.shuffle(dest)
    return dest

def solveIdiot(sl, dictionaryData, indexArray, path, value):

    reuseData = []

    for p in sl[path[-1]][0]:
        dictionaryData[p] = False
    for r1 in indexArray[1:]:
        v = compute_interest(sl[path[-1]], sl[r1])
        if v > 0 and canUse(dictionaryData, sl[r1][0]):
            value = value + v
            path.append(r1)
            for p in sl[r1][0]:
                dictionaryData[p] = False
        else:
            reuseData.append(r1)
    return [path, value, reuseData]

def canUse(dictionaryData, pic):
    good = True
    for p in pic:
        good = good & dictionaryData[p]

    return good;


def filterEntries(entries):
    newEntries = []

    for e in entries:
        if e[0] == "H":
            newEntries.append(e)

    return newEntries

if __name__ == '__main__':
    fileName = sys.argv[1]
    entries = parseInput(fileName)

    # entries = filterEntries(entries)

    # slides = generate_slides(entries)

    slides = generate_slides_filter(entries)

    #matrix = computeInterestMatrix(slides)

    dictionaryData = computeDictionaryData2(entries)

    #[path, value] = solve(dictionaryData, matrix)

    path = randomIdiot(slides, dictionaryData)

    printSolution(path, slides)