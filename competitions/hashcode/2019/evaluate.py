import sys

ORIENTATION = 0
TAGS_NUMBER = 1
TAGS = 2
PICTURE_ID = 3
USED = 4

def evaluate():
    inputFile = sys.argv[1]
    solutionFile = sys.argv[2]

    entries = parseInput(inputFile)
    solution = parseSolution(solutionFile)

    value = evaluateSolution(entries, solution)
    print value


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


def evaluateSolution(entries, solution):
    lastTags = set()
    value = 0
    for s in solution:
        tags = buildTagSet(s, entries)
        transitionValue = compute_interest(lastTags, tags)
        if len(lastTags) > 0 and len(lastTags.intersection(tags)) == 0:
            print s
            print "SOLUTION NOT VALID: No common tags"
            print lastTags
            print tags
            exit(1)

        value = value + transitionValue
        lastTags = tags

    return value

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
    evaluate()