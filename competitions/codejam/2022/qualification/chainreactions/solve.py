# from heapq import heappush, heappop


def solve(fun, next_node):
    sinks = []
    prev_nodes = dict()

    for n in range(len(next_node)):
        if next_node[n] == 0:
            sinks.append(n)

    for n in range(len(next_node)):
        prev_nodes[n] = []

    for n in range(len(next_node)):
        if next_node[n] != 0:
            prev_nodes[next_node[n] - 1].append(n)

    result = 0
    for s in sinks:
        [ongoing, stopped] = compute(s, prev_nodes, fun)
        result += stopped + ongoing

    return result


def compute(s, prev_nodes, fun):

    if len(prev_nodes[s]) == 0:
        return [fun[s], 0]

    min_ongoing = None
    stopped = 0
    for p in prev_nodes[s]:
        [inner_ongoing, inner_stopped] = compute(p, prev_nodes, fun)
        # heappush(ongoing, inner_ongoing)
        if min_ongoing is None or inner_ongoing < min_ongoing:
            min_ongoing = inner_ongoing
        stopped += inner_stopped + inner_ongoing

    # min_fun = heappop(ongoing)
    stopped -= min_ongoing

    return [max(fun[s], min_ongoing), stopped]


if __name__ == "__main__":
    import sys

    sys.setrecursionlimit(1100000)
    T = int(input())

    for t in range(T):
        N = int(input())
        fun = list(map(int, input().split(" ")))
        next_node = list(map(int, input().split(" ")))
        result = solve(fun, next_node)
        print("Case #{}: {}".format(t + 1, result))
