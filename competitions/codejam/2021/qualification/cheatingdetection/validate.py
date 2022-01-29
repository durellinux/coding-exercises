good = 0

for i in range(100):
    str = input()
    data = str.split(" ")
    # print([i, data[2]])
    good += 1 if int(data[2]) == i + 1 else 0

print(good * 1.0 / 100)