import copy
import random
import sys

ORIENTATION = 0
TAGS_NUMBER = 1
TAGS = 2
PICTURE_ID = 3
USED = 4

PATH = 0
VALUE = 1
PATH_TO_TAG = 2
VALID_PATH = 3

SLIDE_PICTURES = 0
SLIDE_TAGS = 1

def doSolve():
    fileName = sys.argv[1]
    entries = parseInput(fileName)

    slides = generate_slides_filter(entries)

    limitedSlides = slides[:]
    tag2Slide = buildTagToSlides(limitedSlides)

    # tag2Pictures = buildTagToPicture(entries)

    path = solver(entries, tag2Slide, limitedSlides)

    printSolution(path, slides)
    # # entries = filterEntries(entries)
    #
    # # slides = generate_slides(entries)
    #
    # slides = generate_slides_filter(entries)
    #
    # # matrix = computeInterestMatrix(slides)
    #
    # dictionaryData = computeDictionaryData2(entries)
    #
    # # [path, value] = solve(dictionaryData, matrix)
    #
    # path = randomIdiot(slides, dictionaryData)
    #
    # printSolution(path, slides)


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


def buildTagToPicture(entries):
    hasmap = dict()
    for e in entries:
        tags = e[TAGS]
        picId = e[PICTURE_ID]
        for t in tags:
            if t not in hasmap.keys():
                hasmap[t] = set()

            hasmap[t].add(picId)

    return hasmap

def generate_slides_filter(entries):
    slides = []
    vertical = []
    for i in xrange(len(entries)):
        if (entries[i][ORIENTATION] == 'H'):
            slides.append([[i], entries[i][TAGS]])
        else:
            vertical.append(i)

    for i in range(0, len(vertical), 2):
        id1 = vertical[i]
        id2 = vertical[i+1]
        slides.append(([id1, id2], list(set().union(entries[id1][TAGS], entries[id2][TAGS]))))

    return slides

def buildTagToSlides(slides):
    hasmap = dict()
    totTags = 0
    for sId in xrange(len(slides)):
        # print str(sId) + "/" + str(len(slides)) + ": " + str(len(hasmap.keys())) + " / " + str(totTags)
        slide = slides[sId]
        tags = slide[SLIDE_TAGS]
        totTags += len(tags)
        for t in tags:
            if t not in hasmap.keys():
                hasmap[t] = set()

            hasmap[t].add(sId)

    return hasmap

def solver(entries, tag2Slide, slides):
    [path, entries] = solverForward(entries, tag2Slide, slides)
    return path

def solverForward(entries, tag2Slide, slides):
    bestPathLength = 0
    bestPath = []
    tags = tag2Slide.keys()
    # tagCount = len(tags)
    # tag = tags[random.randint(0, tagCount-1)]
    for tagId in xrange(len(tags)):
        tag = tags[tagId]
        # print str(tagId) + "/" + str(len(tags)) + ": " + str(bestPathLength)
        tmpEntries = copy.deepcopy(entries)
        [path, value, tags2place, validPath, finalEntries] = solveFromTag(tmpEntries, tag2Slide, slides, tag, [], dict(), 0)
        if validPath and len(path) > bestPathLength:
            bestPath = path
            bestPathLength = len(path)

            return [bestPath, finalEntries]

    return []

def solveBackward(entries, tag2Slide, slides):
    pass


def solveFromTag(entries, tag2Slide, slides, tag, path, tagsToPlace, value):
    tagSlides = tag2Slide[tag]
    foundPath = False

    if len(tagSlides) == 0:
        return [path, value, tagsToPlace, False, entries]

    placedSlides = []

    for sId in tagSlides:
        validSlide = True
        for pId in slides[sId][SLIDE_PICTURES]:
            if entries[pId][USED] == True:
                validSlide = False

        if validSlide:
            foundPath = True
            path.append(sId)
            placedSlides.append(sId)
            for pId in slides[sId][SLIDE_PICTURES]:
                entries[pId][USED] = True

            for t in slides[sId][SLIDE_TAGS]:
                if t != tag:
                    tag2Slide[t].remove(sId)

    for sId in placedSlides:
        tag2Slide[tag].remove(sId)

    if not foundPath:
        return [path, value, tagsToPlace, False, entries]

    tagsToPlace[len(path)] = tag

    nextTags = slides[path[-1]][SLIDE_TAGS]

    nextPath = path
    bestLength = len(path)
    bestEntries = entries
    tagsTried = 0
    TagsTryThreshod = 3
    for t in nextTags:
        tagsTried += 1
        theoreticalPath = copy.deepcopy(path)
        tmpEntries = copy.deepcopy(entries)
        [newPath, value, tags2place, validPath, newEntries] = solveFromTag(tmpEntries, tag2Slide, slides, t, theoreticalPath, tagsToPlace, value)
        if validPath and len(newPath) > bestLength:
            nextPath = newPath
            bestLength = len(newPath)
            bestEntries = newEntries
            break

        if tagsTried > TagsTryThreshod:
            break

    return [nextPath, value, tagsToPlace, True, bestEntries]

def printSolution(solution, slides):
    print len(solution)
    for s in solution:
        print " ".join(map(str, slides[s][0]))

if __name__ == '__main__':
    doSolve()