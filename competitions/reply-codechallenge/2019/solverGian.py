import copy
import sys
from operator import itemgetter

import dijkstra

CUSTOMER_C = 0
CUSTOMER_R = 1
CUSTOMER_VALUE = 2

EDGE_X = 0
EDGE_Y = 1


EDGE_VALUE = 2
EDGE_MOVE = 3

MAX_DISTANCE = 4000000

def decodeValue(char):
    if char == '#':
        return None
    elif char == '~':
        return 800
    elif char == '*':
        return 200
    elif char == '+':
        return 150
    elif char == 'X':
        return 120
    elif char == '_':
        return 100
    elif char == 'H':
        return 70
    elif char == 'T':
        return 50


def getValue(world, c, r, maxC, maxR):
    if 0 <= c < maxC and 0 <= r < maxR:
        return decodeValue(world[r][c])
    else:
        return None

def getCellName(cell):
    return "-".join(map(str, cell))

def appendEdge(edges, e0, e1, value, move):
    e0Name = getCellName(e0)
    e1Name = getCellName(e1)
    edges.append((e0Name, e1Name, value, move))


def computeEdges(world, maxC, maxR):
    edges = []
    for r in range(len(world)):
        for c in range(len(world[r])):
            U = getValue(world, c - 1, r, maxC, maxR)
            if U is not None:
                appendEdge(edges, [r, c], [r, c - 1], U, "U")
            D = getValue(world, c + 1, r, maxC, maxR)
            if D is not None:
                appendEdge(edges, [r, c], [r, c + 1], D, "D")
            L = getValue(world, c, r - 1, maxC, maxR)
            if L is not None:
                appendEdge(edges, [r, c], [r - 1, c], L, "L")
            R = getValue(world, c, r + 1, maxC, maxR)
            if R is not None:
                appendEdge(edges, [r, c], [r + 1, c], R, "R")

    return edges


def printSolution(solution):
    for s in solution:
        print " ".join(map(str, s))



def doSolve(filename):
    [cols, rows, offices, headquarters, world] = parseInput(filename)

    paths = headquartersPaths(world, headquarters, cols, rows)
    for p in paths:
        h0Value = headquarters[p[0]][CUSTOMER_VALUE]
        h1Value = headquarters[p[1]][CUSTOMER_VALUE]
        p[3] = h0Value + h1Value - p[3]
    sortedPaths = sortListByItem(paths, 3, True)

    headquartersId = set(range(0, len(headquarters)))
    selectedPaths = []
    remainingPaths = []
    for path in sortedPaths:
        if len(path[2]) > 4 and path[0] in headquartersId and path[1] in headquartersId and len(selectedPaths) < offices:
            selectedPaths.append(path)
            headquartersId.remove(path[0])
            headquartersId.remove(path[1])
            continue

        remainingPaths.append(path)

    solution = []
    nodesToPath = dict()
    officePositions = []
    for p in selectedPaths:
        h0 = p[0]
        h1 = p[1]
        path = p[2]
        pathPos = p[4]

        pathPosIndex = len(path) / 2
        officePos = pathPos[pathPosIndex]
        path1 = reversePath(path[0:pathPosIndex])
        path2 = "".join(path[pathPosIndex:])
        solution.append([officePos[0], officePos[1], path1])
        solution.append([officePos[0], officePos[1], path2])
        officePositions.append(officePos)

        if h0 not in nodesToPath:
            nodesToPath[h0] = list()
        if h1 not in nodesToPath:
            nodesToPath[h1] = list()

        nodesToPath[h0].append([officePos, path1])
        nodesToPath[h1].append([officePos, path2])

    # placedStuff = True
    # toPlacePath = copy.deepcopy(remainingPaths)
    # while len(toPlacePath) > 0 and placedStuff:
    #     placedStuff = False
    #     newToPlace = []
    #     for p in toPlacePath:
    #         h0 = p[0]
    #         h1 = p[1]
    #         path = p[2]
    #
    #         if h0 in headquartersId and h1 in nodesToPath:
    #             for reached in nodesToPath[h1]:
    #                 officePos = reached[0]
    #                 reachPath = reached[1]
    #                 newPath = reachPath + path
    #                 solution.append([officePos[0], officePos[1], newPath])
    #                 headquartersId.remove(h0)
    #                 placedStuff = True
    #         elif h1 in headquartersId and h0 in nodesToPath:
    #             for reached in nodesToPath[h0]:
    #                 officePos = reached[0]
    #                 reachPath = reached[1]
    #                 newPath = reachPath + "".join(path)
    #                 solution.append([officePos[0], officePos[1], newPath])
    #                 headquartersId.remove(h1)
    #                 placedStuff = True
    #         else:
    #             newToPlace.append(p)
    #     toPlacePath = newToPlace

    for hId in headquartersId:
        missingHeadQuarter = headquarters[hId]
        for o in officePositions:
            oC = o[0]
            oR = o[1]
            hC = missingHeadQuarter[CUSTOMER_C]
            hR = missingHeadQuarter[CUSTOMER_R]
            hValue = missingHeadQuarter[CUSTOMER_VALUE]
            [path, pathPos, pathCost] = getBestPath(world, cols, rows, oC, oR, hC, hR)
            if pathCost is not None and hValue - pathCost >= 0:
                solution.append([oC, oR, "".join(path)])

    printSolution(solution)

def reversePath(path):
    reversedPath = []
    original = list(path)
    for p in original:
        if p == "U":
            reversedPath.append("D")
        elif p == "D":
            reversedPath.append("U")
        elif p == "R":
            reversedPath.append("L")
        elif p == "L":
            reversedPath.append("R")
    return "".join(reversedPath)

def headquartersPaths(world, headquarters, cols, rows):
    paths = []
    for h0id in range(0, len(headquarters) - 1):
        for h1id in range(h0id + 1, len(headquarters)):
            h0 = headquarters[h0id]
            h1 = headquarters[h1id]
            [path, pathPos, pathCost] = getBestPath(world, cols, rows, h0[CUSTOMER_C], h0[CUSTOMER_R], h1[CUSTOMER_C], h1[CUSTOMER_R])
            if pathCost is not None:
                paths.append([h0id, h1id, path, pathCost, pathPos])
    return paths

def getBestPath(world, cols, rows, c0, r0, c1, r1):
    curC = c0
    curR = r0

    path = []
    pathPos = []
    pathCost = 0

    while (curC != c1 or curR != r1) and world[curR][curC] is not None:
        pathPos.append([curC, curR])
        if curR < r1:
            path.append("D")
            curR = curR + 1
        elif curR > r1:
            path.append("U")
            curR = curR - 1
        elif curC < c1:
            path.append("R")
            curC = curC + 1
        elif curC > c1:
            path.append("L")
            curC = curC - 1

        curValue = getValue(world, curC, curR, cols, rows)
        if curValue is None:
            return [[], [], None]

        pathCost = pathCost + curValue

    return [path, pathPos, pathCost]

def solveDijkstra(world, headquarters, cols, rows):
    edges = computeEdges(world, cols, rows)
    dijkstraEdges = []
    for e in edges:
        dijkstraEdges.append((e[0], e[1], e[2]))

    graph = dijkstra.Graph(dijkstraEdges)

    h0 = getCellName([headquarters[0][CUSTOMER_R], headquarters[0][CUSTOMER_C]])
    h1 = getCellName([headquarters[1][CUSTOMER_R], headquarters[1][CUSTOMER_C]])

    print graph.dijkstra(h0, h1)


def parseInput(fileName):
    fileLines = open(fileName).readlines()
    rows = len(fileLines)
    headquarters = []
    [cols, rows, headquartersNum, offices] = map(int, fileLines[0].strip().split(" "))

    for c in range(0, headquartersNum):
        line = fileLines[1 + c].strip()
        [x, y, value] = map(int, line.split(" "))

        headquarters.append([x, y, value])

    world = []

    for m in range(0, rows):
        line = fileLines[1 + headquartersNum + m].strip()
        world.append(list(line))

    return [cols, rows, offices, headquarters, world]


def computeDistancesSimple(headquarters, maxDistance):
    distances = [[MAX_DISTANCE for i in range(0, len(headquarters))] for j in range(0, len(headquarters))]
    # for c0 in
    pass

def sortListByItem(values, keyPos, reversed = True):
    sortedList = sorted(values, key=itemgetter(keyPos))
    if reversed:
        sortedList.reverse()
    return sortedList


if __name__ == '__main__':
    filename = sys.argv[1]
    doSolve(filename)
