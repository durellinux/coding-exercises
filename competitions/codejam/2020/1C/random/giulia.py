import sys
from collections import defaultdict

T = int(sys.stdin.readline())
for case_number in range(1, T+1):
    U = int(sys.stdin.readline())
    D = defaultdict(int)
    for i in range(10000):
        Qstr, Rstr = sys.stdin.readline().split()
        Q = int(Qstr)
        D[Rstr[0]] += 1


    guess = list(map(lambda p: p[0], sorted(D.items(), key=lambda x: x[1], reverse=True)))


    print("Case #{}: {}".format(case_number, "".join(guess)))