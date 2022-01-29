#!/bin/python

import math
import os
import random
import re
import sys

def solve(n, k, balls):
    curBalls = [[balls, 0, 0]]

    totalPicked = 0
    for i in range(k):
        tried = (n - i) * len(curBalls)
        [picked, curBalls] = solveStep(curBalls)
        # cache[i] = [picked, tried]
        totalPicked = totalPicked + picked * 1.0 / tried

    # print cache
    return totalPicked

def solveStep(ballsList):
    picked = 0
    newBalls = set()
    newBallsDict = dict()

    for ballsString in ballsList:
        balls = list(ballsString)
        for j in range(len(balls)):
            idx0 = j
            idx1 = len(balls) - 1 - j
            b0 = balls[idx0]
            b1 = balls[idx1]

            if b0 == "W" or b1 == "W":
                picked += 1

            if b0 == "W":
                pickElement(ballsString, idx0, newBalls)
            else:
                pickElement(ballsString, idx1, newBalls)

    return [picked, newBalls]


def pickElement(balls, index, newBalls):
    tmpBalls = balls[:index] + balls[index + 1:]
    newBalls.add(tmpBalls)

if __name__ == '__main__':
    nk = raw_input().split()

    n = int(nk[0])

    k = int(nk[1])

    balls = raw_input()

    print solve(n, k, balls)
