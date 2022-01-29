import math
import string
from Queue import Queue

alphabet = list(string.ascii_uppercase)

def computeSolution(N, L, numbers):
    message = []

    prime = int(findOnePrime(numbers))
    primeSet = getAllPrimes(numbers, prime)


    primeList = list(primeSet)
    sortedPrimes = sorted(primeList)

    decodeDict = {}
    for i in range(26):
        decodeDict[sortedPrimes[i]] = alphabet[i]

    message = decodeMessage(numbers, primeList, decodeDict)

    if message == None:
        return ""

    return message

def findOnePrime(numbers):
    for n in numbers:
        sqrt = math.sqrt(n)
        if sqrt == int(sqrt):
            return sqrt

    smaller = min(numbers)
    sqrtSmaller = int (math.sqrt(smaller))

    for n in range(2, sqrtSmaller + 1):
        mod = smaller % n
        if smaller % n == 0:
            return n

def getAllPrimes(numbers, prime):
    primeSet = set()

    newPrimes = set()
    newPrimes.add(prime)

    while len(primeSet) < 26:
        if len(newPrimes) == 0:
            return primeSet

        currentPrime = newPrimes.pop()
        primeSet.add(currentPrime)

        for n in numbers:
            mod = n % currentPrime
            div = int(n / currentPrime)
            if mod == 0 and div != currentPrime and div not in primeSet:
                newPrimes.add(div)

    return primeSet

def decodeMessage(numbers, primeList, decodeDict):
    firsLetterPrime0 = 0
    firsLetterPrime1 = 0
    for prime in primeList:
        if numbers[0] % prime == 0:
            firsLetterPrime0 = prime
            firsLetterPrime1 = int(numbers[0] / prime)
            break

    message0 = decodeString(numbers, decodeDict, firsLetterPrime0)
    message1 = decodeString(numbers, decodeDict, firsLetterPrime1)

    # print message0
    # print message1

    if message0 != None:
        return message0
    else:
        return message1

def decodeString(numbers, decodeDict, firstLetterPrime):
    message = [decodeDict[firstLetterPrime]]

    currentLetterPrime = firstLetterPrime
    for n in numbers:
        nextLetterPrime = int (n / currentLetterPrime)
        if nextLetterPrime not in decodeDict:
            return None

        nextLetter = decodeDict[nextLetterPrime]
        message.append(nextLetter)
        currentLetterPrime = nextLetterPrime

    return "".join(message)


if __name__ == "__main__":
    n = int(raw_input())

    for i in range(n):
        [N, L] = map(int, raw_input().split(" "))
        numbers = map(int, raw_input().split(" "))
        message = computeSolution(N, L, numbers)

        print "Case #{}: {}".format(i + 1, message)