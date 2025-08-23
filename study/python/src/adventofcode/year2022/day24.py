from collections import deque

from src.adventofcode.year2022.utils2022 import Coordinate

type Walls = set[Coordinate]
type Blizzard = tuple[Coordinate, Coordinate]
type Blizzards = set[Blizzard]
type Step = tuple[Coordinate, int]
type Links = dict[Coordinate, Coordinate]
type BlizzardCache = dict[int, tuple[Blizzards, set[Coordinate]]]

def solve_day24_part1(data):
    walls, blizzards, start, end, links = parse_day24(data)
    blizzards_coordinate = set()
    for blizzard in blizzards:
        blizzards_coordinate.add(blizzard[0])
    blizzards_cache: BlizzardCache = {0: (blizzards, blizzards_coordinate)}
    time, updated_blizzards = navigate((start, 0), end, walls, links, blizzards_cache)

    return time - 1

def solve_day24_part2(data):
    walls, blizzards, start, end, links = parse_day24(data)
    blizzards_coordinate = set()
    for blizzard in blizzards:
        blizzards_coordinate.add(blizzard[0])
    blizzards_cache: BlizzardCache = {0: (blizzards, blizzards_coordinate)}
    time1, updated_blizzards = navigate((start, 0), end, walls, links, blizzards_cache)
    time2, updated_blizzards = navigate((end, time1), start, walls, links, blizzards_cache)
    time3, updated_blizzards = navigate((start, time2), end, walls, links, blizzards_cache)

    return time3 - 1

def navigate(initial_step: Step, target: Coordinate, walls: Walls, links: Links, blizzards_cache: BlizzardCache) -> tuple[int, Blizzards]:
    neighbors: list[Coordinate] = [(1, 0), (-1, 0), (0, 1), (0, -1), (0, 0)]
    steps: deque[Step] = deque()
    steps.append(initial_step)
    visited: set[Step] = set()
    visited.add(initial_step)

    while len(steps) > 0:
        position, time = steps.popleft()

        if time not in blizzards_cache:
            blizzards_cache[time] = simulate_blizzards(blizzards_cache[time - 1][0], links)

        if time + 1 not in blizzards_cache:
            blizzards_cache[time + 1] = simulate_blizzards(blizzards_cache[time][0], links)

        blizzards = blizzards_cache[time]
        next_blizzards = blizzards_cache[time + 1]

        if position == target:
            return time, blizzards[0]

        for neighbor in neighbors:
            new_position = (position[0] + neighbor[0], position[1] + neighbor[1])
            new_step = (new_position, time + 1)
            if new_position not in walls and new_position not in next_blizzards[1] and new_step not in visited and new_position[0] >= 0 and new_position[1] >= 0:
                steps.append(new_step)
                visited.add(new_step)

    raise "Cannot reach the target"


def simulate_blizzards(blizzards: Blizzards, links: Links) -> tuple[Blizzards, set[Coordinate]]:
    new_blizzards: Blizzards = set()

    for blizzard in blizzards:
        new_position = (blizzard[0][0] + blizzard[1][0], blizzard[0][1] + blizzard[1][1])
        if new_position in links:
            new_position = links[new_position]
        new_blizzard: Blizzard = (new_position, blizzard[1])
        new_blizzards.add(new_blizzard)

    blizzards_coordinate = set()
    for blizzard in blizzards:
        blizzards_coordinate.add(blizzard[0])

    return new_blizzards, blizzards_coordinate


def parse_day24(data: str) -> tuple[Walls, Blizzards, Coordinate, Coordinate, Links]:
    lines = data.strip().splitlines()

    walls: set[Coordinate] = set()
    blizzards: set[Blizzard] = set()
    start_point: Coordinate = (-1, -1)
    end_point: Coordinate = (-1, -1)
    height: int = len(lines)
    width: int = len(lines[0])
    links: dict[Coordinate, Coordinate] = dict()

    for r in range(len(lines)):
        values = list(lines[r])
        for c in range(len(values)):
            value = values[c]
            if r == 0 and value == '.':
                start_point = (r, c)
            elif r == len(lines) - 1 and value == '.':
                end_point = (r, c)
            elif value == '#':
                walls.add((r, c))
            elif value == '.':
                continue
            elif value == '>':
                blizzards.add(((r, c), (0, 1)))
            elif value == '<':
                blizzards.add(((r, c), (0, -1)))
            elif value == 'v':
                blizzards.add(((r, c), (1, 0)))
            elif value == '^':
                blizzards.add(((r, c), (-1, 0)))
            else:
                raise "Unknown case"

    for r in range(height):
        links[(r, 0)] = (r, width - 2)
        links[(r, width - 1)] = (r, 1)

    for c in range(width):
        links[(0, c)] = (height - 2, c)
        links[(height - 1, c)] = (1, c)

    return walls, blizzards, start_point, end_point, links
