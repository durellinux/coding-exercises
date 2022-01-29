import random
import math

def sigmoid(x):
    return 1.0 / (1 + math.exp(-x))

P = 100
Q = 10000
T = 100

si = []
qj = []

print(T)
print("86")

for t in range(0, T):
    for i in range(0, P):
        si.append(random.uniform(-3, 3))

    for i in range(0, Q):
        qj.append(random.uniform(-3, 3))

    # print(si)
    # print(qj)

    for i in range(0, P):
        data = ""
        for j in range(0, Q):
            if i == t:
                rndCheat = random.uniform(0, 1)
                if rndCheat >= 0.5:
                    response = "1"
                else:
                    x = si[i] - qj[j]
                    f = sigmoid(x)
                    rnd = random.uniform(0, 1)
                    response = 1 if rnd > (1 - f) else 0
                data += str(response)
            else:
                x = si[i] - qj[j]
                f = sigmoid(x)
                rnd = random.uniform(0, 1)
                response = 1 if rnd > (1 - f) else 0
                # print([f, rnd, response])
                data += str(response)
        print(data)