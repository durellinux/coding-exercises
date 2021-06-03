
def numberOfWays(roads):
    cities = set()

    for r in roads:
        cities.add(r[0])
        cities.add(r[1])

    paths = dict()

    for c in cities:
        paths[c] = dict()
        for c1 in cities:
            paths[c][c1] = []

def pathsFromNode(startNode, cities, roads):
    
    toVisit = set(cities)
    toVisit.remove(startNode)

    fronteer = []
    for r in roads:
        if r[0] == startNode:
            fronteer.append(r)
    paths = [[startNode]]

    # TODO: Continue



if __name__ == "main":
    roads_rows = int(input().strip())
    roads_columns = int(input().strip())

    roads = []

    for _ in range(roads_rows):
        roads.append(list(map(int, input().rstrip().split())))

    result = numberOfWays(roads)

    print(result)