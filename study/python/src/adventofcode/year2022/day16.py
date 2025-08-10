import re
import sys
from collections import deque
from inspect import stack
from typing import Dict

sys.setrecursionlimit(600000)


type Network = Dict[str, tuple[int, list[Path], int]]
type Step = tuple[str, int, int, set[tuple[str, str]], set[str]]
type CacheKey = tuple[int, int, int, int]
type CacheKey2 = tuple[int, int, int, int, int, int, int]
type StopSearchCacheKey = tuple[str, str, int, int]
type Path = tuple[str, int]

def solve_day16_part1(data: str) -> int:
    max_time = 30
    network, max_flow = parse_day16(data)
    cache = dict()
    result = navigate(network, "AA", 0, 0, 0, max_time, max_flow, cache)
    return result

def navigate(network: Network, valve: str, time: int, current_flow: int, open_valves: int, max_time: int, maximum_flow: int, cache: dict[CacheKey, int]):
    if time >= max_time:
        return 0

    if current_flow == maximum_flow:
        return 0

    valve_flow = network[valve][0]
    max_flow = 0

    cache_key = get_cache_key(network, valve, open_valves, time, current_flow)

    if cache_key in cache:
        return cache[cache_key]

    if valve_flow > 0 and not is_valve_open(network, valve, open_valves):
        next_time = time + 1
        new_flow = valve_flow * (max_time - next_time)
        new_open_valves = open_valve(network, valve, open_valves)
        max_flow = new_flow + navigate(network, valve, next_time, current_flow + valve_flow, new_open_valves, max_time, maximum_flow, cache)

    for connection in network[valve][1]:
        flow_connection = navigate(network, connection[0], time + connection[1], current_flow, open_valves, max_time, maximum_flow, cache)
        if flow_connection > max_flow:
            max_flow = flow_connection

    cache[cache_key] = max_flow
    return max_flow

def get_cache_key(network: Network, valve: str, open_valves: int, time: int, current_flow: int) -> CacheKey:
    valve_index = network[valve][2]
    return valve_index, time, open_valves, 0

def solve_day16_part2(data: str) -> int:
    max_time = 26
    network, maximum_flow = parse_day16(data)
    cache = dict()
    result = navigate2(network, "AA", "AA", 0,0, 0, 0, 0, max_time, maximum_flow, cache, dict())
    return result


def navigate2(network: Network, valve1: str, valve2: str, time: int, time_1: int, time_2: int, current_flow: int, open_valves: int, max_time: int, maximum_flow: int, cache: dict[CacheKey2, int], stop_search_cache: dict[StopSearchCacheKey, int]):
    max_possible_move = max_time

    cache_key = get_cache_key_2(network, valve1, valve2, open_valves, time, current_flow, time_1, time_2)

    if cache_key in cache:
        return cache[cache_key]

    if time >= max_time:
        cache[cache_key] = 0
        return 0

    if current_flow == maximum_flow:
        cache[cache_key] = 0
        return 0

    stop_search_cache_key = (valve1, valve2, open_valves, 0)
    if stop_search_cache_key in stop_search_cache and time >= stop_search_cache[stop_search_cache_key]:
        cache[cache_key] = 0
        return 0

    valve_flow_1 = network[valve1][0]
    valve_flow_2 = network[valve2][0]

    can_move_1 = time_1 <= time < max_time
    can_move_2 = time_2 <= time < max_time

    can_open_1 = valve_flow_1 > 0 and not is_valve_open(network, valve1, open_valves)
    can_open_2 = valve_flow_2 > 0 and not is_valve_open(network, valve2, open_valves)

    can_do_something = can_open_1 or can_open_2 or can_move_1 or can_move_2
    if not can_do_something:
        cache[cache_key] = 0
        return 0

    next_time_options: set[tuple[str, str, int, int, int, int, int, int]] = set()

    if can_move_1 and can_open_1:
        next_flow_1 = current_flow + valve_flow_1
        next_score_1 = valve_flow_1 * (max_time - time_1 - 1)
        next_open_valves_1 = open_valve(network, valve1, open_valves)
        if can_move_2:
            for connection in network[valve2][1]:
                first_valve, second_valve, first_time, second_time = get_sorted_navigation_2(network, valve1, connection[0], time_1 + 1, time_2 + connection[1])
                if min(first_time, second_time) < max_time:
                    next_time_options.add((first_valve, second_valve, min(first_time, second_time), first_time, second_time, next_flow_1, next_open_valves_1, next_score_1))
        else:
            if min(time_1 + 1, time_2) < max_possible_move:
                next_time_options.add((valve1, valve2, min(time_1 + 1, time_2), time_1 + 1, time_2, next_flow_1, next_open_valves_1, next_score_1))

    if can_move_2 and can_open_2 and valve1 != valve2:
        next_flow_2 = current_flow + valve_flow_2
        next_score_2 = valve_flow_2 * (max_time - time_2 - 1)
        next_open_valves_2 = open_valve(network, valve2, open_valves)
        if can_move_1:
            for connection in network[valve1][1]:
                first_valve, second_valve, first_time, second_time = get_sorted_navigation_2(network, valve2, connection[0], time_2 + 1, time_1 + connection[1])
                if min(first_time, second_time) < max_possible_move:
                    next_time_options.add(
                        (first_valve, second_valve, min(first_time, second_time), first_time, second_time, next_flow_2, next_open_valves_2, next_score_2))
        else:
            if min(time_1, time_2 + 1) < max_possible_move:
                next_time_options.add(
                    (valve1, valve2, min(time_1, time_2 + 1), time_1, time_2 + 1, next_flow_2, next_open_valves_2, next_score_2))

    if can_move_1 and can_move_2 and can_open_1 and can_open_2 and valve1 != valve2:
        next_flow = current_flow + valve_flow_1 + valve_flow_2
        next_score = valve_flow_1 * (max_time - time_1 - 1) + valve_flow_2 * (max_time - time_2 - 1)
        next_open_valves = open_valve(network, valve1, open_valves)
        next_open_valves = open_valve(network, valve2, next_open_valves)
        if min(time_1 + 1, time_2 + 1) < max_possible_move:
            next_time_options.add((valve1, valve2, min(time_1 + 1, time_2 + 1), time_1 + 1, time_2 + 1, next_flow, next_open_valves, next_score))

    for connection1 in network[valve1][1]:
        for connection2 in network[valve2][1]:
            next_1 = connection1[0] if can_move_1 else valve1
            next_2 = connection2[0] if can_move_2 else valve2
            next_time_1 = time_1 + connection1[1] if can_move_1 else time_1
            next_time_2 = time_2 + connection2[1] if can_move_2 else time_2

            first_valve, second_valve, first_time, second_time = get_sorted_navigation_2(network, next_1, next_2, next_time_1, next_time_2)
            if min(first_time, second_time) < max_possible_move:
                next_time_options.add((first_valve, second_valve, min(first_time, second_time), first_time, second_time, current_flow, open_valves, 0))

    max_flow = 0
    for option in next_time_options:
        score = option[7] + navigate2(network, option[0], option[1], option[2], option[3], option[4], option[5], option[6], max_time, maximum_flow, cache, stop_search_cache)
        if score > max_flow:
            max_flow = score

    cache[cache_key] = max_flow
    stop_search_cache[stop_search_cache_key] = max(time_1, time_2)
    return max_flow

def get_cache_key_2(network: Network, valve1: str, valve2: str, open_valves: int, time: int, current_flow: int, time_1: int, time_2: int) -> CacheKey2:
    valve_index_1 = network[valve1][2]
    valve_index_2 = network[valve2][2]
    valve_index_sorted = sorted([valve_index_1, valve_index_2])
    return valve_index_sorted[0], valve_index_sorted[1], time, open_valves, current_flow, time_1, time_2

def get_sorted_navigation(network: Network, valve1: str, valve2: str) -> tuple[str, str]:
    valve_index_1 = network[valve1][2]
    valve_index_2 = network[valve2][2]
    valve_index_sorted = sorted([valve_index_1, valve_index_2])
    first_valve = valve1 if valve_index_1 == valve_index_sorted[0] else valve2
    second_valve = valve1 if valve_index_1 == valve_index_sorted[1] else valve2
    return first_valve, second_valve

def get_sorted_navigation_2(network: Network, valve1: str, valve2: str, time_1: int, time_2: int) -> tuple[str, str, int, int]:
    valve_index_1 = network[valve1][2]
    valve_index_2 = network[valve2][2]
    valve_index_sorted = sorted([valve_index_1, valve_index_2])
    first_valve = valve1 if valve_index_1 == valve_index_sorted[0] else valve2
    second_valve = valve1 if valve_index_1 == valve_index_sorted[1] else valve2
    first_time = time_1 if valve_index_1 == valve_index_sorted[0] else time_2
    second_time = time_1 if valve_index_1 == valve_index_sorted[1] else time_2
    return first_valve, second_valve, first_time, second_time

def is_valve_open(network: Network, valve: str, open_valves: int) -> bool:
    valve_index = network[valve][2]
    return (open_valves & (1 << valve_index)) > 0

def open_valve(network: Network, valve: str, open_valves: int) -> int:
    valve_index = network[valve][2]
    return open_valves | (1 << valve_index)

def parse_day16(data: str) -> tuple[Network, int]:
    regex = re.compile(r"Valve (.*) has flow rate=(.*); tunnel[s]* lead[s]* to valve[s]* (.*)")

    network: Network = dict()
    max_flow = 0

    lines = data.splitlines()
    for line in lines:
        match = regex.match(line)
        if match:
            valve = match.group(1)
            flow = int(match.group(2))
            connected_valves = list(map(lambda s: (s.strip(), 1), match.group(3).split(',')))
            network[valve] = (flow, connected_valves, len(network.keys()))
            max_flow += flow

    return reduce_network(network), max_flow

def reduce_network(network: Network) -> Network:
    start_valve = "AA"
    reduced_network: Network = dict()

    for valve in network.keys():
        if valve == start_valve or network[valve][0] > 0:
            next_valves: list[Path] = list()
            to_visit: deque[Path] = deque()
            visited = set()
            to_visit.extend(network[valve][1])

            while len(to_visit) > 0:
                path = to_visit.popleft()
                path_valve = path[0]
                distance = path[1]
                valve_props = network[path_valve]

                visited.add(path_valve)

                if valve_props[0] > 0 and path_valve != valve:
                    next_valves.append(path)
                else:
                    connections = network[path_valve][1]
                    for next_path in connections:
                        if next_path[0] not in visited:
                            visited.add(next_path[0])
                            to_visit.append((next_path[0], distance + 1))

            reduced_network[valve] = (network[valve][0], next_valves, len(reduced_network.keys()))

    return reduced_network

type FunctionCall = tuple[str, str, int, int, int, int, int]
def solve_day16_part2_iterative(data: str) -> int:

    max_time = 26
    network, maximum_flow = parse_day16(data)

    cache: dict[FunctionCall, int] = dict()
    waiting_for: dict[FunctionCall, list[FunctionCall]] = dict()
    add_score: dict[tuple[FunctionCall, FunctionCall], int] = dict()
    best_flow: dict[FunctionCall, int] = dict()

    # valve1: str, valve2: str, time: int, time_1: int, time_2: int, open_valves: int, current_flow: int

    execution_stack = deque()
    start_call = build_function_call("AA", "AA", 0, 0, 0, 0, 0)
    execution_stack.append(start_call)

    while len(execution_stack) > 0:
        call = execution_stack.pop()

        if call in cache:
            continue

        valve1 = call[0]
        valve2 = call[1]
        time = call[2]
        time_1 = call[3]
        time_2 = call[4]
        open_valves = call[5]
        current_flow = call[6]

        if time >= max_time:
            cache[call] = 0
            continue

        if current_flow == maximum_flow:
            cache[call] = 0
            continue

        if call in waiting_for:
            can_compute = True
            for function_call in waiting_for[call]:
                if function_call not in cache:
                    can_compute = False
                    break

            if can_compute:
                results = []
                for function_call in waiting_for[call]:
                    results.append(cache[function_call] + add_score[(call, function_call)])
                    del add_score[(call, function_call)]

                cache[call] = max(results)
                del waiting_for[call]
                continue

            else:
                continue

        next_calls = get_next_calls(network, valve1, valve2, time, time_1, time_2, current_flow, open_valves, max_time)
        if len(next_calls) == 0:
            cache[call] = 0
            continue

        execution_stack.append(call)

        waiting_for[call] = []
        for next_call in next_calls.__iter__():
            if next_call not in cache:
                waiting_for[call].append(next_call[0])
                add_score[(call, next_call[0])] = next_call[1]
                execution_stack.append(next_call[0])

    return cache[start_call]

def build_function_call(valve1: str, valve2: str, time: int, time1: int, time2: int, open_valves: int, current_flow: int) -> FunctionCall:
    return valve1, valve2, time, time1, time2, open_valves, current_flow

def get_next_calls(network: Network, valve1: str, valve2: str, time: int, time_1: int, time_2: int, current_flow: int, open_valves: int, max_time: int) -> set[tuple[FunctionCall, int]]:
    max_possible_move = max_time

    valve_flow_1 = network[valve1][0]
    valve_flow_2 = network[valve2][0]

    can_move_1 = time_1 <= time < max_time
    can_move_2 = time_2 <= time < max_time

    can_open_1 = valve_flow_1 > 0 and not is_valve_open(network, valve1, open_valves)
    can_open_2 = valve_flow_2 > 0 and not is_valve_open(network, valve2, open_valves)

    can_do_something = can_open_1 or can_open_2 or can_move_1 or can_move_2
    if not can_do_something:
        return set()

    next_time_options: set[tuple[FunctionCall, int]] = set()

    if can_move_1 and can_open_1:
        next_flow_1 = current_flow + valve_flow_1
        next_score_1 = valve_flow_1 * (max_time - time_1 - 1)
        next_open_valves_1 = open_valve(network, valve1, open_valves)
        if can_move_2:
            for connection in network[valve2][1]:
                first_valve, second_valve, first_time, second_time = get_sorted_navigation_2(network, valve1, connection[0], time_1 + 1, time_2 + connection[1])
                if min(first_time, second_time) < max_time:
                    call = build_function_call(first_valve, second_valve, min(first_time, second_time), first_time, second_time, next_open_valves_1, next_flow_1)
                    next_time_options.add((call, next_score_1))
        else:
            if min(time_1 + 1, time_2) < max_possible_move:
                call = build_function_call(valve1, valve2, min(time_1 + 1, time_2), time_1 + 1, time_2, next_open_valves_1, next_flow_1)
                next_time_options.add((call, next_score_1))

    if can_move_2 and can_open_2 and valve1 != valve2:
        next_flow_2 = current_flow + valve_flow_2
        next_score_2 = valve_flow_2 * (max_time - time_2 - 1)
        next_open_valves_2 = open_valve(network, valve2, open_valves)
        if can_move_1:
            for connection in network[valve1][1]:
                first_valve, second_valve, first_time, second_time = get_sorted_navigation_2(network, valve2, connection[0], time_2 + 1, time_1 + connection[1])
                if min(first_time, second_time) < max_possible_move:
                    call = build_function_call(first_valve, second_valve, min(first_time, second_time), first_time, second_time, next_open_valves_2, next_flow_2)
                    next_time_options.add((call, next_score_2))
        else:
            if min(time_1, time_2 + 1) < max_possible_move:
                call = build_function_call(valve1, valve2, min(time_1, time_2 + 1), time_1, time_2 + 1, next_open_valves_2, next_flow_2)
                next_time_options.add((call, next_score_2))

    if can_move_1 and can_move_2 and can_open_1 and can_open_2 and valve1 != valve2:
        next_flow = current_flow + valve_flow_1 + valve_flow_2
        next_score = valve_flow_1 * (max_time - time_1 - 1) + valve_flow_2 * (max_time - time_2 - 1)
        next_open_valves = open_valve(network, valve1, open_valves)
        next_open_valves = open_valve(network, valve2, next_open_valves)
        if min(time_1 + 1, time_2 + 1) < max_possible_move:
            call = build_function_call(valve1, valve2, min(time_1 + 1, time_2 + 1), time_1 + 1, time_2 + 1, next_open_valves, next_flow)
            next_time_options.add((call, next_score))

    for connection1 in network[valve1][1]:
        for connection2 in network[valve2][1]:
            next_1 = connection1[0] if can_move_1 else valve1
            next_2 = connection2[0] if can_move_2 else valve2
            next_time_1 = time_1 + connection1[1] if can_move_1 else time_1
            next_time_2 = time_2 + connection2[1] if can_move_2 else time_2

            first_valve, second_valve, first_time, second_time = get_sorted_navigation_2(network, next_1, next_2, next_time_1, next_time_2)
            if min(first_time, second_time) < max_possible_move:
                call = build_function_call(first_valve, second_valve, min(first_time, second_time), first_time, second_time, open_valves, current_flow)
                next_time_options.add((call, 0))

    # max_flow = 0
    # for option in next_time_options:
    #     score = option[7] + navigate2(network, option[0], option[1], option[2], option[3], option[4], option[5], option[6], max_time, maximum_flow, cache, stop_search_cache)
    #     if score > max_flow:
    #         max_flow = score
    #
    # cache[cache_key] = max_flow
    # stop_search_cache[stop_search_cache_key] = max(time_1, time_2)
    # return max_flow

    return next_time_options
