
def computeSolution(lydiaPath):
    myPath = []
    for move in lydiaPath:
        if move == "S":
            myPath.append("E")
        else:
            myPath.append("S")

    return "".join(myPath)

if __name__ == "__main__":
    n = int(raw_input())

    for i in range(n):
        length = raw_input()
        lydia = list(raw_input())
        path = computeSolution(lydia)

        print "Case #{}: {}".format(i + 1, path)