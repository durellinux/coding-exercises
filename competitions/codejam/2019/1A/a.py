SUBWORDS = 0
WORDS = 1

def solve(words):
    trie = {}
    reversedWords = []
    for w in words:
        rev = w[::-1]
        reversedWords.append(rev)

    for w in reversedWords:
        letters = list(w)
        curPrefix = []
        for l in letters:
            curPrefix.append(l)
            insertTrie(trie, curPrefix, len(curPrefix) == len(letters))

    pickedWords = 0
    for l in trie.keys():
        if l is not "value":
            pickedWords = pickedWords + solveTrie(trie[l])

    return pickedWords

def insertTrie(trie, prefix, isFinal):
    curTrie = trie
    visitedLetters = []
    for l in prefix:
        visitedLetters.append(l)
        if l not in curTrie:
            curTrie[l] = {}
            curTrie[l]['value'] = [0, 0]

        subWords = curTrie[l]['value'][SUBWORDS]
        words = curTrie[l]['value'][WORDS]
        incSub = 1 if isFinal else 0
        incWord = 1 if isFinal and len(prefix) == len(visitedLetters) else 0
        curTrie[l]['value'] = [subWords + incSub, words + incWord]

        curTrie = curTrie[l]

def solveTrie(trie):
    if (trie["value"][SUBWORDS] == 1):
        return 0

    pickedWords = 0
    for l in trie.keys():
        if l is not "value":
            pickedWords = pickedWords + solveTrie(trie[l])

    remainingWords = trie["value"][SUBWORDS] - pickedWords
    if remainingWords >= 2:
        pickedWords += 2

    return pickedWords



if __name__ == "__main__":
    T = int(raw_input())
    for t in range(T):
        N = int(raw_input())
        words = []
        for w in range(N):
            words.append(raw_input())
        solution = solve(words)
        print "Case #{}: {}".format(t + 1, str(solution))