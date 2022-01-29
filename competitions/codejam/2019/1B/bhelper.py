def help(D):
    vals = []
    for i in range(1, 7):
        vals.append(int(D / i) % 63)

    return vals


for D in range(1, 501):
    # print str(D) + " " + str(help(D))
    vals = []
    valsComplete = []
    for i in range(1, 7):
        vals.append(int(D/i) % 63)
        valsComplete.append(int(D/i))

    # print vals
    sortedVals = sorted(vals)

    valid = True
    for i in range(0, len(sortedVals) - 1):
        if sortedVals[i+1] - sortedVals[i] < 8:
            valid = False

    if (valid):
        print "-----"
        print D
        print vals
        print valsComplete
        print sortedVals
        print "-----"


# print help(324)
#
# tmpV = 162129586585337856
#
# x6 = tmpV / (2**60)
# tmpV = tmpV % (2**60)
#
# print x6
# print tmpV

#
# print 2**45
#
# print 108086391056891904 / 35184372088832
# print 108086391056891904 - 6 * 18014398509481984
# print 3072 / 2**9
#
#
# tmpV = 54043195528445952
#
# x6 = tmpV / (2**53)
# # print tmpV
# # print x6 * (2**53)
# # tmpV = tmpV % (2**53)
# print x6
# # print tmpV
#
# x3 = tmpV / (2**44)
# # tmpV = tmpV % (2 ** 44)
# print x3
#
# x2 = tmpV % (2**37)
# tmpV = tmpV / (2 ** 37)
#
# x4 = tmpV % (2**18)
# tmpV = tmpV / (2 ** 18)
#
# x1 = tmpV % (2**11)
# tmpV = tmpV / (2 ** 11)
#
# x5 = tmpV % (2 ** 2)
# tmpV = tmpV / (2 ** 2)

D = 324
E = help(D)
inputV = [1, 2, 3, 4, 5, 9]

print E
sum = 0
vals = []
for i in range(6):
    v = inputV[i] * (2**(D/(i+1)))
    vals.append(v)
    sum += v

print vals
print sum
print sum % (2**63)
print 162129586585337856
print help(D)

x = 162129586585337856 / (2 ** 54)
print x * (2**54)
print 162129586585337856 % (2 ** 54)
