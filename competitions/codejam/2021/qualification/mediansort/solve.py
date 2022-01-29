import math
import sys

def canBeReached(current, start, dest):
  reacheable = [start]
  toVisit = current[start].copy()

  while len(toVisit) > 0:
    for r in toVisit:
      if r not in reacheable:
        reacheable.append(r)

        for v in current[r]:
          if v not in toVisit:
            toVisit.append(v)

      toVisit.remove(r)

  return dest in reacheable



def topoSortvisit(solution, s, visited, sortlist):
  visited[s] = True
  for i in solution[s]:
    if not visited[i]:
      topoSortvisit(solution, i, visited, sortlist)
  sortlist.insert(0, s)


def topoSort(solution):
  visited = {i: False for i in solution}
  sortlist = []

  for v in solution:
    if not visited[v]:
      topoSortvisit(solution, v, visited, sortlist)
  return sortlist



def combineResults(current, a, median, b):
  if not current.__contains__(a):
    current[a] = []

  if not current.__contains__(median):
    current[median] = []

  if not current.__contains__(b):
    current[b] = []

  if canBeReached(current, a, median):
    prev = a
    next = b
  elif canBeReached(current, a, b):
    prev = a
    next = b
  elif canBeReached(current, median, b):
    prev = a
    next = b
  else:
    prev = b
    next = a

  if not canBeReached(current, prev, median):
    current[prev].append(median)

  if not canBeReached(current, median, next):
    current[median].append(next)

  for v in current:
    if isinstance(current[v], list):
      currentV = current[v]

      if prev in currentV and median in currentV:
        currentV.remove(median)

      if prev in currentV and next in currentV:
        currentV.remove(next)

      if median in currentV and next in currentV:
        currentV.remove(next)

      current[v] = currentV

  return current


def getAnswer():
  answer = sys.stdin.readline()
  int_answer = int(answer)

  if int_answer == -1:
    exit(int_answer)

  return int_answer


def isDone(solution):
  for v in solution:
    if len(solution[v]) > 1:
      return False

  return True


def getRetry(solution):
  for v in solution:
    if len(solution[v]) > 1:
      if len(solution[v]) >= 3:
        return solution[v].copy()
      else:
        retry = solution[v].copy()
        retry.append(v)

        return retry


[test_cases, N, Q] = [int(s) for s in sys.stdin.readline().split()]
for t in range(0, test_cases):
  count = 1
  solution = {}
  int_answer = 0

  while count <= N:
    a = count
    b = count + 1 if count + 1 <= N else count + 1 - N
    c = count + 2 if count + 2 <= N else count + 2 - N
    question = [a, b, c]
    print(' '.join(map(str, question)))
    sys.stdout.flush()

    sys.stderr.write(' '.join(str(x) for x in question) + '\n')

    median = getAnswer()

    sys.stderr.write(str(median) + '\n')

    medianIndex = question.index(median)
    prev = question[(medianIndex - 1) % 3]
    next = question[(medianIndex + 1) % 3]

    solution = combineResults(solution, prev, median, next)
    sys.stderr.write(str(solution) + '\n')

    count += 1

  while not isDone(solution):
    question = getRetry(solution)
    print(' '.join(map(str, question)))
    sys.stdout.flush()

    sys.stderr.write(' '.join(str(x) for x in question) + '\n')

    median = getAnswer()

    sys.stderr.write(str(median) + '\n')

    medianIndex = question.index(median)
    prev = question[(medianIndex - 1) % 3]
    next = question[(medianIndex + 1) % 3]

    solution = combineResults(solution, prev, median, next)
    sys.stderr.write(str(solution) + '\n')

  sys.stderr.write(str(solution) + '\n')
  sys.stderr.write(" ".join(map(str, topoSort(solution))) + '\n')

  print(" ".join(map(str, topoSort(solution))))
  sys.stdout.flush()

  answer = getAnswer()
#
# if __name__ == "__main__":
#   solution = {}
#   solution = combineResults(solution, 3, 1, 2)
#   print(solution)
#   print(topoSort(solution))
#
#   solution = combineResults(solution, 3, 4, 2)
#   print(solution)
#   print(topoSort(solution))
#
#   solution = combineResults(solution, 3, 4, 5)
#   print(solution)
#   print(topoSort(solution))
#
#   solution = combineResults(solution, 5, 6, 4)
#   print(solution)
#   print(topoSort(solution))
#
#   solution = combineResults(solution, 6, 7, 5)
#   print(solution)
#   print(topoSort(solution))
#
#   solution = combineResults(solution, 6, 7, 8)
#   print(solution)
#   print(topoSort(solution))
#
#   solution = combineResults(solution, 9, 7, 8)
#   print(solution)
#   print(topoSort(solution))
#
#   solution = combineResults(solution, 9, 10, 8)
#   print(solution)
#   print(topoSort(solution))
#
#   solution = combineResults(solution, 9, 10, 1)
#   print(solution)
#   print(topoSort(solution))
#
#   solution = combineResults(solution, 10, 1, 2)
#   print(solution)
#   print(topoSort(solution))
#
