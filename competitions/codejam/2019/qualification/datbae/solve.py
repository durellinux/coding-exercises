import math
import sys
from heapq import heappush, heappop


def solveCase():
    [N, B, F] = map(int, raw_input().split())

    attempts = 0
    breakInfo = []
    heappush(breakInfo, (0, N, B, 0, N-B, False))

    # debug("Query input: " + str(breakInfo))

    while attempts < F + 1:
        attempts = attempts + 1
        [query, heapAttempt, isFinal] = generateAttempt(breakInfo)
        if isFinal:
            # debug(attempts)
            return query

        print query
        sys.stdout.flush()
        response = raw_input()

        if response == "-1":
            exit(0)

        breakInfo = resolveAttempt(heapAttempt, response)


def generateAttempt(breakInfo):
    attempt = []

    solved = True
    query = ""

    currentValue = "0"
    while len(breakInfo) > 0:
        info = heappop(breakInfo)
        start = info[0]
        end = info[1]
        broken = info[2]
        startResponse = info[3]
        endResponse = info[4]
        caseSolved = info[5]
        if not caseSolved:
            solved = False

            value0 = currentValue
            start0 = start
            length0 = int(math.ceil((end - start) / 2))
            end0 = start0 + length0
            query = query + (value0 * (end0 - start0))

            value1 = "1" if value0 == "0" else "0"
            start1 = end0
            length1 = end - start - length0
            end1 = end
            if length1 == 0:
                currentValue = value1
            else:
                query = query + (value1 * (end1 - start1))

            [expected0, expected1, start0, end0, start1, end1] = computedExpectedZeroAndOne(value0, length0, end - start, start)
            heappush(attempt, (start, end, broken, startResponse, endResponse, caseSolved, expected0, expected1, start0, end0, start1, end1))

        else:
            query = query + (currentValue * (end - start))
            [expected0, expected1, start0, end0, start1, end1] = computedExpectedZeroAndOne(currentValue, end - start, end - start, start)
            heappush(attempt, (start, end, broken, startResponse, endResponse, caseSolved, expected0, expected1, start0, end0, start1, end1))

            if currentValue == "0":
                currentValue = "1"
            else:
                currentValue = "0"

    if solved:
        solution = []
        while len(attempt) > 0:
            info = heappop(attempt)
            start = info[0]
            end = info[1]
            broken = info[2]
            if broken == end - start:
                solution.extend(range(start, end))
        return [solution, attempt, True]

    return [query, attempt, False]

def generateString(character, length):
    return character * length

def resolveAttempt(heapAttempt, responseStr):
    response = list(responseStr)
    breakInfo = []

    # debug("Response input: " + str(heapAttempt))

    totalBroken = 0
    while len(heapAttempt) > 0:
        info = heappop(heapAttempt)
        start = info[0]
        end = info[1]
        broken = info[2]
        parentStart = info[3]
        parentEnd = info[4]
        caseSolved = info[5]
        expected0 = info[6]
        expected1 = info[7]
        start0 = info[8]
        end0 = info[9]
        start1 = info[10]
        end1 = info[11]

        count0 = 0
        count1 = 0
        for idx in range(parentStart, parentEnd):
            if idx > len(response) - 1:
                print idx
            if response[idx] == "0":
                count0 = count0 + 1
            else:
                count1 = count1 + 1

        broken0 = expected0 - count0
        broken1 = expected1 - count1

        solved0 = (broken0 == 0) or (broken0 == (end0 - start0))
        solved1 = (broken1 == 0) or (broken1 == (end1 - start1))

        # heappush(breakInfo, (0, N, B, 0, N - B, False))
        # heappush(breakInfo, (start0, end0, broken0, start0, end0 - broken0, solved0))
        # heappush(breakInfo, (start1, end1, broken1, start1, end1 - broken1, solved1))
        if start0 < start1:
            if expected0 > 0:
                heappush(breakInfo, (start0, end0, broken0, start0 - totalBroken, end0 - broken0 - totalBroken, solved0))
            if expected1 > 0:
                heappush(breakInfo, (start1, end1, broken1, start1 - totalBroken - broken0, end1 - totalBroken - broken0 - broken1, solved1))
        else:
            if expected1 > 0:
                heappush(breakInfo, (start1, end1, broken1, start1 - totalBroken, end1 - totalBroken - broken1, solved1))
            if expected0 > 0:
                heappush(breakInfo, (start0, end0, broken0, start0 - totalBroken - broken1, end0 - totalBroken - broken1 - broken0, solved0))

        totalBroken = totalBroken + broken

    # debug("Query input: " + str(breakInfo))

    return breakInfo


def computedExpectedZeroAndOne(value, lengthValue, totalLength, start):
    if value == "0":
        return [lengthValue, totalLength - lengthValue, start, start + lengthValue, start + lengthValue, start + totalLength]
    else:
        return [totalLength - lengthValue, lengthValue, start + lengthValue, start + totalLength, start, start + lengthValue]

def debug(message):
    sys.stderr.write(str(message) + "\n")
    sys.stderr.flush()


if __name__ == "__main__":
    T = int(raw_input())
    for t in range(T):
        brokenBits = solveCase()
        # debug(brokenBits)
        print " ".join(map(str, brokenBits))
        sys.stdout.flush()

        response = raw_input()
        if response == "-1":
            exit(0)
