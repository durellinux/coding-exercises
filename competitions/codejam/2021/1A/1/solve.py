
def merge(digits, values):
    res = list(digits)
    res.extend(values)
    resStr = ''.join(list(map(str, res)))
    return int(resStr)

def getDigits(number):
    numberStr = str(number)
    return list(map(int, list(numberStr)))

def solve(X):
    ops = 0

    print(X)
    for n in range(1, len(X)):
        prev = X[n-1]
        cur = X[n]
        done = False

        if cur <= prev:
            prevDigits = getDigits(prev)
            curDigits = getDigits(cur)

            for i in range(min(len(prevDigits), len(curDigits))):
                pD = prevDigits[i]
                cD = curDigits[i]

                if cD > pD:
                    values = [0 for d in range(len(curDigits), len(prevDigits))]
                    ops += len(values)
                    X[n] = merge(curDigits, values)
                    done = True
                    break

                elif cD < pD:
                    values = [0 for d in range(len(curDigits), len(prevDigits)+1)]
                    ops += len(values)
                    X[n] = merge(curDigits, values)
                    done = True
                    break

            if not done:
                preRemainingDigits = prevDigits[len(curDigits):]
                setDigits = set(preRemainingDigits)

                if len(preRemainingDigits) == 0:
                    values = [0]
                    ops += len(values)
                    X[n] = merge(curDigits, values)
                elif len(setDigits) == 1 and 9 in setDigits:
                    values = [0 for d in range(len(preRemainingDigits) + 1)]
                    ops += len(values)
                    X[n] = merge(curDigits, values)
                else:
                    leadingZeroes = 0
                    for i in preRemainingDigits:
                        if i == 0:
                            leadingZeroes += 1
                        else:
                            break

                    nextInt = merge(preRemainingDigits, []) + 1
                    tmp = getDigits(nextInt)
                    values = [0 for d in range(leadingZeroes)]
                    values.extend(tmp)
                    ops += len(values)
                    X[n] = merge(curDigits, values)
                    #
                    # for t in range(len(preRemainingDigits)):
                    #     v = preRemainingDigits[t]
                    #     if v != 9:
                    #         values.append(preRemainingDigits[t] + 1)
                    #         values.extend([0 for d in range(t + 1, len(preRemainingDigits))])
                    #         ops += len(values)
                    #         X[n] = merge(curDigits, values)
                    #         break
                    #     else:
                    #         values.append(v)

    print(X)
    return ops


if __name__ == "__main__":
    T = int(input())

    for t in range(T):
        N = int(input())
        X = list(map(int, input().split(" ")))

        result = solve(X)
        print("Case #{}: {}".format(t + 1, result))
