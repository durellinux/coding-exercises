from collections import deque
from typing import Deque

type BFSStep = tuple[tuple[int, int], int]

def solve_day12_part1(data: str) -> int:
    matrix, start, end = parse_day12(data)

    visited = set()
    visited.add(start)
    to_visit: Deque[BFSStep] = deque()
    to_visit.append((start, 0))

    while len(to_visit) > 0:
        current: BFSStep = to_visit.popleft()
        position = current[0]
        step = current[1]

        if position == end:
            return step

        neighbors = get_neighbors(matrix, position)
        for neighbor in neighbors:
            if neighbor not in visited:
                visited.add(neighbor)
                to_visit.append((neighbor, step + 1))

    raise ValueError("No path found")

def solve_day12_part2(data: str) -> int:
    matrix, start, end = parse_day12(data)
    starting_point = get_starting_points(matrix)

    visited = set()
    to_visit: Deque[BFSStep] = deque()

    for position in starting_point:
        visited.add(position)
        to_visit.append((position, 0))

    while len(to_visit) > 0:
        current: BFSStep = to_visit.popleft()
        position = current[0]
        step = current[1]

        if position == end:
            return step

        neighbors = get_neighbors(matrix, position)
        for neighbor in neighbors:
            if neighbor not in visited:
                visited.add(neighbor)
                to_visit.append((neighbor, step + 1))

    raise ValueError("No path found")

def get_neighbors(matrix: list[list[str]], position: tuple[int, int]) -> list[tuple[int, int]]:
    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]
    current_value = matrix[position[0]][position[1]]
    current_value_int = ord(current_value)
    neighbors: list[tuple[int, int]] = []

    for d in directions:
        r, c = position[0] + d[0], position[1] + d[1]
        if 0 <= r < len(matrix) and 0 <= c < len(matrix[0]):
            next_value = matrix[r][c]
            next_value_int = ord(next_value)
            if current_value_int >= next_value_int or next_value_int == current_value_int + 1:
                neighbors.append((r, c))

    return neighbors

def get_starting_points(matrix: list[list[str]]) -> list[tuple[int, int]]:
    starting_points: list[tuple[int, int]] = []
    for row in range(len(matrix)):
        for col in range(len(matrix[0])):
            if matrix[row][col] == "a":
                starting_points.append((row, col))

    return starting_points

def parse_day12(data) -> tuple[list[list[str]], tuple[int, int], tuple[int, int]]:
    matrix: list[list[str]] = list(map(lambda line: list(line), data.splitlines()))
    start: tuple[int, int] = (0, 0)
    end: tuple[int, int] = (0, 0)

    for row in range(len(matrix)):
        for col in range(len(matrix[row])):
            if matrix[row][col] == "S":
                start = (row, col)
                matrix[row][col] = "a"
            if matrix[row][col] == "E":
                end = (row, col)
                matrix[row][col] = "z"

    return matrix, start, end