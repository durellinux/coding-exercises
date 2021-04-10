import heapq
import math
import random


def sigmoid(x):
    return 1.0 / (1 + math.exp(-x))


def probApproximated(pi, qi, q):
    prob = 0
    for i in range(0, len(q)):
        x = pi - qi[i]
        prob += sigmoid(x)

    return prob / len(q)


def getProbability(s):
    correct = s.count('1')
    total = len(s)
    return correct * 1.0 / total


def getAnswers(d, picks):
    answers = ""
    for q in picks:
        answers += d[q]

    return answers


def solve(data, t):
    questions = range(0, 10000)

    cheaterDetector = []

    probabilities = []
    probabilitiesDifficult = []

    answersPerQuestion = []
    playerProbabilities = []
    playerDifficultQuestionsProbabilities = []


    pi = []
    qi = []

    for j in range(0, 10000):
        correctAnswers = 0
        for i in range(0, 100):
            correctAnswers += int(data[i][j])
        heapq.heappush(answersPerQuestion, (correctAnswers, j))
        qi.append(-3 + 6 * (correctAnswers * 1.0 / 100))

    mostDifficultQuestions = []
    for i in range(0, 1000):
        item = heapq.heappop(answersPerQuestion)
        mostDifficultQuestions.append(item[1])

    for i in range(0, len(data)):
        d = data[i]
        p = getProbability(d)
        probabilities.append(p)

        pi.append(-3 * 6 * p)

        answerDifficult = getAnswers(d, mostDifficultQuestions)
        pDifficult = getProbability(answerDifficult)
        probabilitiesDifficult.append(pDifficult)

        sortedProbabilities = probabilities.copy()
        sortedProbabilities.sort()

        # print([sortedProbabilities.index(probabilities[t]), probabilities[t], probabilitiesDifficult[t]])

        # for i in range(0, 100):
        #     print([i, probabilitiesDifficult[i]])

        # diff50 = []
        # for i in probabilitiesDifficult:
        #     diff50.append(math.fabs(0.5 - i))
        #
        #
        # maxP = min(diff50)
        # return diff50.index(maxP) + 1

        difficultAnswers = ""
        for q in mostDifficultQuestions:
            difficultAnswers += d[q]

        difficultP = getProbability(difficultAnswers)
        heapq.heappush(playerDifficultQuestionsProbabilities, (-difficultP, i))


        # print [i, p, difficultP, probApproximated(pi[i], qi), p < probApproximated(pi[i], qi)]
        if p > 0.5:
            # approximated = probApproximated(pi[i], qi, range(0, 10000))
            approximatedDifficult = probApproximated(pi[i], qi, mostDifficultQuestions)
            delta = math.fabs(1 - approximatedDifficult/difficultP)
            heapq.heappush(cheaterDetector, (-delta, i))
            # print([i, p, difficultP, approximatedDifficult, delta])

    # firstPlayers = []
    # firstDifficultPlayers = []
    #
    # for i in range(0, 10):
    #     p1 = heapq.heappop(playerProbabilities)[1]
    #     p2 = heapq.heappop(playerDifficultQuestionsProbabilities)[1]
    #     firstPlayers.append(p1)
    #     firstDifficultPlayers.append(p2)
    #
    # for p in firstDifficultPlayers:
    #     if p not in firstPlayers:
    #         return p

    # print [min(pi), max(pi)]
    # print [min(qi), max(qi)]
    #
    if len(cheaterDetector) > 0:
        cheater = heapq.heappop(cheaterDetector)
        return cheater[1] + 1

    maxP = max(probabilities)
    return probabilities.index(maxP) + 1


if __name__ == "__main__":
    T = int(input())
    P = int(input())

    for t in range(T):
        data = []
        for i in range(0, 100):
            data.append(input())

        result = solve(data, t)
        print("Case #{}: {}".format(t + 1, result))
