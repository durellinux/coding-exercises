import math


def solveWithTicks(H, m, s):
    tickToDeg = 1/12 * 10**(-10)

    sDegrees = s * tickToDeg
    mDegrees = m * tickToDeg
    hDegrees = H * tickToDeg

    Hr = int(math.floor(hDegrees / 360 * 12))
    mr = int(math.floor(mDegrees / 360 * 60))
    sr = int(math.floor(sDegrees / 360 * 60))

    totalNs = H
    solutionNs = (Hr * 3600 + mr * 60 + sr) * 10 ** 9

    nr = totalNs - solutionNs

    return [Hr, mr, sr, nr]

def goodForRotation(H, m, s):
    rotation0 = 720 - s % 720 if (s % 720 != 0) else 0
    rotation1 = 12 - m % 12 if (m % 12 != 0) else 0

    return rotation0 == rotation1

def dataWithRotation(H, m, s):
    rotation0 = 720 - s % 720 if (s % 720 != 0) else 0
    return [H + rotation0, m + rotation0, s + rotation0, rotation0]

def solve2(A, B, C):
    s0 = [A, B, C]
    s1 = [A, C, B]
    s2 = [B, A, C]
    s3 = [B, C, A]
    s4 = [C, A, B]
    s5 = [C, B, A]
    solutions = [s0, s1, s2, s3, s4, s5]

    for s in solutions:
        for r in [0, 360]:
            [H, m, s] = s[0], s[1], s[2]

            if H >=0 and m >= 0 and s >= 0 and goodForRotation(H, m, s):
                [H, m, s, rotation] = dataWithRotation(H, m, s)
                [Hr, mr, sr, nr] = solveWithTicks(H, m, s)
                if nr >= 0 and nr < 10**9:
                    return [Hr, mr, sr, nr]

    return [-1, -1, -1, -1]


def getParams(A, B, C):
    if A % 720 == 0 and B % 12 == 0:
        return [A, B, C, 0]

    if A % 720 == 0 and C % 12 == 0:
        return [A, C, B, 0]

    if B % 720 == 0 and A % 12 == 0:
        return [B, A, C, 0]

    if B % 720 == 0 and C % 12 == 0:
        return [B, C, A, 0]

    if C % 720 == 0 and A % 12 == 0:
        return [C, A, B, 0]

    if C % 720 == 0 and B % 12 == 0:
        return [C, B, A, 0]

    s0 = [A, B, C]
    s1 = [A, C, B]
    s2 = [B, A, C]
    s3 = [B, C, A]
    s4 = [C, A, B]
    s5 = [C, B, A]

    solutions = [s0, s1, s2, s3, s4, s5]

    for s in solutions:
        rotation0 = 720 - s[0] % 720 if (s[0] % 720 != 0) else 0
        rotation1 = 12 - s[1] % 12 if (s[1] % 12 != 0) else 0

        if rotation0 == rotation1:
            return [s[0] + rotation0, s[1] + rotation0, s[2], rotation0]

    return [0, 0, 0]

def solve(A, B, C):
    [A, B, C] = sorted([A, B, C])
    rotation = 720 - A % 720 if (A % 720 != 0) else 0
    H = C + rotation
    m = B + rotation
    s = A + rotation

    # [H, m, s, rotation] = getParams(A, B, C)


    # sTOns = 10**9
    # mTOns = 60 * sTOns
    # hTOns = 60 * mTOns

    # nr = H
    # if Hr + mr + sr > 0:
    #     nr = H - (Hr * 3600 + mr * 60 + sr) * 10 ** 9

    tmp = H
    nr = tmp % 10**9
    tmp = tmp // 10**9
    sr = tmp % 60
    tmp = tmp // 60
    mr = tmp % 60
    Hr = tmp // 60

    print([H, m, s, rotation])
    print([Hr, mr, sr, nr])

    return [Hr, mr, sr, nr]


if __name__ == "__main__":
    T = int(input())

    for t in range(T):
        [H, m, s] = map(int, input().split(" "))

        [Hr, mr, sr, nr] = solve2(H, m, s)
        print("Case #{}: {} {} {} {}".format(t + 1, Hr, mr, sr, nr))
