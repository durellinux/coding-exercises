import re
from collections import deque
from typing import Dict

type Network = Dict[str, tuple[int, list[str], int]]
type Step = tuple[str, int, int, set[tuple[str, str]], set[str]]
type CacheKey = tuple[int, int, int, int]
type CacheKey2 = tuple[int, int, int, int, int]

def solve_day16_part1(data: str) -> int:
    max_time = 30
    network = parse_day16(data)
    cache = dict()
    result = navigate(network, "AA", 0, 0, 0, max_time, cache)
    return result

def navigate(network: Network, valve: str, time: int, current_flow: int, open_valves: int, max_time: int, cache: dict[CacheKey, int]):
    if time == max_time:
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
        max_flow = new_flow + navigate(network, valve, next_time, current_flow + valve_flow, new_open_valves, max_time, cache)

    for connection in network[valve][1]:
        flow_connection = navigate(network, connection, time + 1, current_flow, open_valves, max_time, cache)
        if flow_connection > max_flow:
            max_flow = flow_connection

    cache[cache_key] = max_flow
    return max_flow

def get_cache_key(network: Network, valve: str, open_valves: int, time: int, current_flow: int) -> CacheKey:
    valve_index = network[valve][2]
    return valve_index, time, open_valves, current_flow

def solve_day16_part2(data: str) -> int:
    max_time = 26
    network = parse_day16(data)
    cache = dict()
    result = navigate2(network, "AA", "AA", 0, 0, 0, max_time, cache)
    return result


def navigate2(network: Network, valve1: str, valve2: str, time: int, current_flow: int, open_valves: int, max_time: int, cache: dict[CacheKey2, int]):
    if time == max_time:
        return 0

    valve_flow_1 = network[valve1][0]
    valve_flow_2 = network[valve2][0]

    cache_key = get_cache_key_2(network, valve1, valve2, open_valves, time, current_flow)

    if cache_key in cache:
        return cache[cache_key]

    can_open_1 = valve_flow_1 > 0 and not is_valve_open(network, valve1, open_valves)
    can_open_2 = valve_flow_2 > 0 and not is_valve_open(network, valve2, open_valves)

    next_time_options: set[tuple[str, str, int, int, int, int]] = set()

    if can_open_1:
        next_flow_1 = current_flow + valve_flow_1
        next_score_1 = valve_flow_1 * (max_time - time - 1)
        next_open_valves_1 = open_valve(network, valve1, open_valves)
        for connection in network[valve2][1]:
            first_valve, second_valve = get_sorted_navigation(network, valve1, connection)
            next_time_options.add((first_valve, second_valve, time + 1, next_flow_1, next_open_valves_1, next_score_1))

    if can_open_2 and valve1 != valve2:
        next_flow_2 = current_flow + valve_flow_2
        next_score_2 = valve_flow_2 * (max_time - time - 1)
        next_open_valves_2 = open_valve(network, valve2, open_valves)
        for connection in network[valve1][1]:
            first_valve, second_valve = get_sorted_navigation(network, valve2, connection)
            next_time_options.add(
                (first_valve, second_valve, time + 1, next_flow_2, next_open_valves_2, next_score_2))

    if can_open_1 and can_open_2 and valve1 != valve2:
        next_flow = current_flow + valve_flow_1 + valve_flow_2
        next_score = valve_flow_1 * (max_time - time - 1) + valve_flow_2 * (max_time - time - 1)
        next_open_valves = open_valve(network, valve1, open_valves)
        next_open_valves = open_valve(network, valve2, next_open_valves)
        next_time_options.add((valve1, valve2, time + 1, next_flow, next_open_valves, next_score))

    for connection1 in network[valve1][1]:
        for connection2 in network[valve2][1]:
            first_valve, second_valve = get_sorted_navigation(network, connection1, connection2)
            next_time_options.add((first_valve, second_valve, time + 1, current_flow, open_valves, 0))

    max_flow = 0
    for option in next_time_options:
        score = option[5] + navigate2(network, option[0], option[1], option[2], option[3], option[4], max_time, cache)
        if score > max_flow:
            max_flow = score

    cache[cache_key] = max_flow
    return max_flow

def get_cache_key_2(network: Network, valve1: str, valve2: str, open_valves: int, time: int, current_flow: int) -> CacheKey2:
    valve_index_1 = network[valve1][2]
    valve_index_2 = network[valve2][2]
    valve_index_sorted = sorted([valve_index_1, valve_index_2])
    return valve_index_sorted[1], valve_index_sorted[1], time, open_valves, current_flow

def get_sorted_navigation(network: Network, valve1: str, valve2: str) -> tuple[str, str]:
    valve_index_1 = network[valve1][2]
    valve_index_2 = network[valve2][2]
    valve_index_sorted = sorted([valve_index_1, valve_index_2])
    first_valve = valve1 if valve_index_1 == valve_index_sorted[0] else valve2
    second_valve = valve1 if valve_index_1 == valve_index_sorted[1] else valve2
    return first_valve, second_valve

def is_valve_open(network: Network, valve: str, open_valves: int) -> bool:
    valve_index = network[valve][2]
    return (open_valves & (1 << valve_index)) > 0

def open_valve(network: Network, valve: str, open_valves: int) -> int:
    valve_index = network[valve][2]
    return open_valves | (1 << valve_index)

def parse_day16(data: str) -> Network:
    regex = re.compile(r"Valve (.*) has flow rate=(.*); tunnel[s]* lead[s]* to valve[s]* (.*)")

    network: Network = dict()
    valve_index: dict[str, int] = dict()

    lines = data.splitlines()
    for line in lines:
        match = regex.match(line)
        if match:
            valve = match.group(1)
            flow = int(match.group(2))
            connected_valves = list(map(lambda s: s.strip(), match.group(3).split(',')))
            network[valve] = (flow, connected_valves, len(network.keys()))
            valve_index[valve] = len(valve_index)

    return network