import queue
from collections import deque

from src.adventofcode.year2022.utils2022 import Coordinate3D

type Reading = tuple[Coordinate3D]

def solve_day18_part1(data: str) -> int:
    readings = parse_day18(data)
    readings_set = set(readings)

    neighbors_bearings: list[Coordinate3D] = [
        (1, 0, 0),
        (-1, 0, 0),
        (0, 1, 0),
        (0, -1, 0),
        (0, 0, 1),
        (0, 0, -1),
    ]

    exposed_faces = 0
    for reading in readings.__iter__():
        free_faces = 6
        for bearing in neighbors_bearings:
            neighbor = (reading[0] + bearing[0], reading[1] + bearing[1], reading[2] + bearing[2])
            if neighbor in readings_set:
                free_faces -= 1

        exposed_faces += free_faces

    return exposed_faces

def solve_day18_part2(data: str) -> int:
    readings = parse_day18(data)
    readings_set = set(readings)

    min_coordinate = 1e10
    max_coordinate = -1e10
    for reading in readings.__iter__():
        min_reading = min(reading[0], reading[1], reading[2])
        max_reading = max(reading[0], reading[1], reading[2])
        if max_reading > max_coordinate:
            max_coordinate = max_reading

        if min_reading < min_coordinate:
            min_coordinate = min_reading

    # Making space for water on the border (1 should be enough)
    min_coordinate = min_coordinate - 1
    max_coordinate = max_coordinate + 1

    first_water_drop: Coordinate3D = (min_coordinate, min_coordinate, min_coordinate)
    lake: set[Coordinate3D] = set()
    lake.add(first_water_drop)
    queue: deque[Coordinate3D] = deque()
    queue.append(first_water_drop)

    neighbors_bearings: list[Coordinate3D] = [
        (1, 0, 0),
        (-1, 0, 0),
        (0, 1, 0),
        (0, -1, 0),
        (0, 0, 1),
        (0, 0, -1),
    ]

    while len(queue) > 0:
        water = queue.popleft()

        for bearing in neighbors_bearings:
            neighbor = (water[0] + bearing[0], water[1] + bearing[1], water[2] + bearing[2])
            neighbor_min = min(neighbor[0], neighbor[1], neighbor[2])
            neighbor_max = max(neighbor[0], neighbor[1], neighbor[2])
            if neighbor_min >= min_coordinate and neighbor_max <= max_coordinate and neighbor not in readings_set and neighbor not in lake:
                lake.add(neighbor)
                queue.append(neighbor)

    exposed_faces = 0
    for reading in readings.__iter__():
        contact_with_water = 0
        for bearing in neighbors_bearings:
            neighbor = (reading[0] + bearing[0], reading[1] + bearing[1], reading[2] + bearing[2])
            if neighbor in lake:
                contact_with_water += 1

        exposed_faces += contact_with_water

    return exposed_faces


def parse_day18(data: str) -> list[Coordinate3D]:
    readings: list[Coordinate3D] = []

    for line in data.splitlines():
        values = list(map(int, line.strip().split(',')))
        readings.append((values[0], values[1], values[2]))

    return readings