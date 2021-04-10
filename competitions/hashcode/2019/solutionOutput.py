

def printSolution(solution, slides):
    print len(solution)
    for s in solution:
        print " ".join(map(str, slides[s][0]))



if __name__ == '__main__':
    slides = []
    slides.append(([[0], ["cat", "beach", "sun"]]))
    slides.append(([[1, 2], ["selfie", "smile", "garden"]]))
    slides.append(([[3], ["garden", "cat"]]))


    printSolution([0, 2, 1], slides)