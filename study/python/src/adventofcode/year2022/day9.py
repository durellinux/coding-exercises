from typing import Literal

type Direction = Literal["R", "L", "U", "D"]

def solve_day9_part1(data: str) -> int:
    return solve_day9(data, 2)

def solve_day9_part2(data: str) -> int:
    return solve_day9(data, 10)

def solve_day9(data: str, knots: int) -> int:
    commands = parse_day9(data)
    visited: set[tuple[int, int]] = set()

    knots_position: list[tuple[int, int]] = [(0, 0) for _ in range(knots)]
    visited.add((0, 0))

    for command in commands:
        direction = command[0]
        for step in range(command[1]):
            knots_position = move(direction, knots_position)
            visited.add(knots_position[-1])

    return len(visited)

def move(direction: Direction, knots_positions: list[tuple[int, int]]) -> list[tuple[int, int]]:
    delta = (0, 0)
    if direction == "R":
        delta = (0, 1)
    elif direction == "L":
        delta = (0, -1)
    elif direction == "U":
        delta = (-1, 0)
    elif direction == "D":
        delta = (1, 0)

    knots_positions[0] = (knots_positions[0][0] + delta[0], knots_positions[0][1] + delta[1])

    for knot in range(1, len(knots_positions)):
        knots_positions[knot] = make_knot_follow(knots_positions[knot - 1], knots_positions[knot])

    return knots_positions

def make_knot_follow(front: tuple[int, int], back: tuple[int, int]) -> tuple[int, int]:
    distance = (front[0] - back[0], front[1] - back[1])

    abs_distance = (abs(distance[0]), abs(distance[1]))
    if abs_distance == (0, 0) or abs_distance == (1, 0) or abs_distance == (0, 1) or abs_distance == (1, 1):
        return back

    direction_x = 0 if distance[0] == 0 else distance[0] / abs_distance[0]
    direction_y = 0 if distance[1] == 0 else distance[1] / abs_distance[1]
    return back[0] + direction_x, back[1] + direction_y

def parse_day9(data: str) -> list[tuple[Direction, int]]:
    result = []
    for line in data.strip().splitlines():
        values = line.split(" ")
        result.append((values[0], int(values[1])))

    return result