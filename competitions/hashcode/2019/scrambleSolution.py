import sys

ORIENTATION = 0
TAGS_NUMBER = 1
TAGS = 2
PICTURE_ID = 3
USED = 4

def scramble():
    inputFile = sys.argv[1]
    solutionFile = sys.argv[2]

    entries = parseInput(inputFile)
    solution = parseSolution(solutionFile)

    newSolution = scrambleSolution(entries, solution)
    printSolution(newSolution, entries)


def parseInput(fileName):
    fileLines = open(fileName).readlines()[1:]
    rows = len(fileLines)
    entries = []
    for r1 in range(0, rows):
        line = fileLines[r1].strip()
        [orientation, number_of_tags, tags] = [s for s in line.split(' ', 2)]
        tags = [s for s in tags.split(' ')]
        entries.append([orientation, number_of_tags, tags, r1, False])

    return entries


def parseSolution(fileName):
    solution = []
    fileLines = open(fileName).readlines()[1:]
    rows = len(fileLines)
    for r1 in range(1, rows):
        line = fileLines[r1].strip()
        pics = map(int, line.split())
        solution.append(pics)
    return solution


def scrambleSolution(entries, solution):
    for sId in range(0, len(solution), 4):
        if sId + 4 < len(solution):
            # print sId
            s0 = solution[sId]
            s1 = solution[sId + 1]
            s2 = solution[sId + 2]
            s3 = solution[sId + 3]

            tags0 = buildTagSet(s0, entries)
            tags1 = buildTagSet(s1, entries)
            tags2 = buildTagSet(s2, entries)
            tags3 = buildTagSet(s3, entries)

            gain01 = compute_interest(tags0, tags1)
            gain12 = compute_interest(tags1, tags2)
            gain23 = compute_interest(tags2, tags3)

            gain02 = compute_interest(tags0, tags2)
            gain21 = compute_interest(tags2, tags1)
            gain13 = compute_interest(tags1, tags3)

            startPoints = gain01 + gain12 + gain23
            newPoints = gain02 + gain21 + gain13

            if (tags0.intersection(tags2) != 0) and (tags2.intersection(tags1) !=0) and (tags1.intersection(tags3) != 0) and (newPoints > startPoints):
                # print "Scramble " + str(sId) + " ".join(map(str, [s0, s1, s2, s3]))
                tmp = solution[sId + 1]
                solution[sId + 1] = solution[sId + 2]
                solution[sId + 2] = tmp

    return solution


def printSolution(solution, slides):
    print len(solution)
    for s in solution:
        print " ".join(map(str, s))
        # pass

def buildTagSet(pictures, entries):
    tags = set()

    for p in pictures:
        if entries[p][USED]:
            print "SOLUTION NOT VALID: picture " + str(p) + " used multiple times."
            exit(1)

        entries[p][USED] = True
        for t in entries[p][TAGS]:
            tags.add(t)

    return tags


def compute_interest(t1, t2):
    t1t2 = len(t1.intersection(t2))
    t1nott2 = len(t1.difference(t2))
    t2nott1 = len(t2.difference(t1))
    return min(t1t2, t1nott2, t2nott1)


if __name__ == '__main__':
    scramble()
