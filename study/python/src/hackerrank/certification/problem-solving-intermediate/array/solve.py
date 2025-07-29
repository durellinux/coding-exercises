#!/bin/python3

import math
import os
import random
import re
import sys


#
# Complete the 'maxElement' function below.
#
# The function is expected to return an INTEGER.
# The function accepts following parameters:
#  1. INTEGER n
#  2. INTEGER maxSum
#  3. INTEGER k
#

def sumSx(n, x, k):
    if (k < x):
        return x * (x + 1) // 2 - (x - k - 1) * (x - k) // 2
    else:
        return x * (x + 1) // 2 + (k - x + 1)


def sumDx(n, x, k):
    if (n - k >= x):
        return x * (x + 1) // 2 + (n - k - x)
    else:
        return x * (x + 1) // 2 - (n - k) * (n - k + 1) // 2


def totalSum(n, x, k):
    return sumSx(n, x, k) + sumDx(n, x, k) - x


def maxElement(n, maxSum, k):
    solutions = [1, maxSum]

    while solutions[1] - solutions[0] > 1:
        x = math.ceil((solutions[0] + solutions[1]) / 2)

        arraySum = totalSum(n, x, k)
        if arraySum == maxSum:
            return x

        if arraySum > maxSum:
            solutions = [solutions[0], x]
        else:
            solutions = [x, solutions[1]]

    if totalSum(n, solutions[1], k) < maxSum:
        return solutions[1]

    return solutions[0]


if __name__ == '__main__':

    n = int(input().strip())

    maxSum = int(input().strip())

    k = int(input().strip())

    result = maxElement(n, maxSum, k)

    print(result)
