import sys


def solve(t, F):
    tries = 0

    missingCombination = []
    enquiry = range(1, 120)

    expectedCounts = [24, 6, 2, 1]

    while tries < F and len(missingCombination) < 4:

        letters = []

        for i in enquiry:
            position = len(missingCombination) + 1 + (i-1) * 5

            print position
            sys.stdout.flush()

            letter = raw_input()

            if letter == "N":
                sys.exit()

            letters.append(letter)

            tries += 1
            if tries > F:
                sys.exit()

        lettersCount = dict()
        for l in letters:
            if l not in lettersCount.keys():
                lettersCount[l] = 0
            lettersCount[l] += 1

        # if t == 1:
        #     sys.stderr.write("============" + "\n")
        #     sys.stderr.write("Missing: " + str(missingCombination) + "\n")
        #     sys.stderr.write("Enquiry: " + str(enquiry) + "\n")
        #     sys.stderr.write(str(lettersCount))
        #     sys.stderr.flush()

        missingLetter = computeMissing(lettersCount, expectedCounts[len(missingCombination)], missingCombination)
        missingCombination.append(missingLetter)
        newEnquiry = []
        for letterPos in range(len(enquiry)):
            if letters[letterPos] == missingLetter:
                newEnquiry.append(enquiry[letterPos])

        enquiry = newEnquiry

    lastLetter = ['A', 'B', 'C', 'D', 'E']
    for l in missingCombination:
        lastLetter.remove(l)

    missingCombination.extend(lastLetter)

    print "".join(missingCombination)
    sys.stdout.flush()

    response = raw_input()
    if response == "N":
        sys.exit()

def computeMissing(lettersCount, expectedCount, lettersFound):
    letters = ['A', 'B', 'C', 'D', 'E']


    for l in letters:
        if l not in lettersCount.keys() and l not in lettersFound:
            return l
        if l in lettersCount.keys() and lettersCount[l] != expectedCount:
            return l

    sys.exit()

if __name__ == "__main__":
    [T, F] = map(int, raw_input().split(' '))
    for t in range(T):
        solve(t, F)
