
def allSubstringsPrefix(words):
    for n in range(0, len(words) - 1):
        curWord = words[n]
        otherWord = words[n+1]
        if len(curWord) > 0:
            trimmed = otherWord[0:len(curWord)]
            if trimmed != curWord:
                return False

    return True

def lastElementOrEmpty(list):
    if len(list) > 0:
        return list[-1]

    return ""

def solve(words):
    prefixes = []
    suffixes = []
    splits = []

    for w in words:
        s = w.split("*")
        splits.append(s)
        prefixes.append(s[0])
        suffixes.append(s[-1][::-1])

    prefixes = sorted(prefixes, key=len)
    suffixes = sorted(suffixes, key=len)

    if allSubstringsPrefix(prefixes) and allSubstringsPrefix(suffixes):
        middleString = ""
        for s in splits:
            if len(s) > 2:
                for p in s[1:-1]:
                    middleString += p

        return lastElementOrEmpty(prefixes) + middleString + lastElementOrEmpty(suffixes)[::-1]

    return "*"

if __name__ == "__main__":
    T = int(raw_input())

    for i in range(T):
        N = int(raw_input())
        words = []
        for n in range(N):
            words.append(raw_input())

        result = solve(words)

        print "Case #{}: {}".format(i + 1, result)