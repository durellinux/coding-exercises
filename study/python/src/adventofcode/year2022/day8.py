from typing import Union, Literal


def solve_day8_part1(data: str) -> int:
    visible: set[tuple[int, int]] = set()
    matrix = parse_day8_input(data)
    visible = visible.union(compute_visible_on_rows(matrix, "F"))
    visible = visible.union(compute_visible_on_rows(matrix, "B"))
    visible = visible.union(compute_visible_on_cols(matrix, "F"))
    visible = visible.union(compute_visible_on_cols(matrix, "B"))

    return len(visible)

def solve_day8_part2(data: str) -> int:
    matrix = parse_day8_input(data)
    score: list[list[int]] = [[1 for _ in range(len(matrix[0]))] for _ in range(len(matrix))]

    for r in range(1, len(matrix) - 1):
        for c in range(1, len(matrix[0]) - 1):
            tree_score = 1
            view = 1
            for r1 in range(r + 1, len(matrix) - 1):
                if matrix[r][c] > matrix[r1][c]:
                    view += 1
                else:
                    break
            tree_score *= view
            view = 1
            for r2 in range(r - 1, 0, -1):
                if matrix[r][c] > matrix[r2][c]:
                    view += 1
                else:
                    break
            tree_score *= view
            view = 1
            for c1 in range(c + 1, len(matrix[0]) - 1):
                if matrix[r][c] > matrix[r][c1]:
                    view += 1
                else:
                    break
            tree_score *= view
            view = 1
            for c2 in range(c - 1, 0, -1):
                if matrix[r][c] > matrix[r][c2]:
                    view += 1
                else:
                    break
            tree_score *= view
            score[r][c] = tree_score

    return max(map(max, score))

def compute_visible_on_rows(matrix: list[list[int]], direction: Literal["F", "B"]) -> set[tuple[int, int]]:
    start_col = 0 if direction == "F" else len(matrix[0]) - 1
    range_col = range(len(matrix[0])) if direction == "F" else range(len(matrix[0]) - 1, -1, -1)
    visible: set[tuple[int, int]] = set()

    for row in range(len(matrix)):
        visible.add((row, start_col))
        current_max = matrix[row][start_col]
        for col in range_col:
            if matrix[row][col] > current_max:
                current_max = matrix[row][col]
                visible.add((row, col))

    return visible

def compute_visible_on_cols(matrix: list[list[int]], direction: Literal["F", "B"]) -> set[tuple[int, int]]:
    start_row = 0 if direction == "F" else len(matrix) - 1
    range_row = range(len(matrix)) if direction == "F" else range(len(matrix) - 1, -1, -1)
    visible: set[tuple[int, int]] = set()

    for col in range(len(matrix[0])):
        visible.add((start_row, col))
        current_max = matrix[start_row][col]
        for row in range_row:
            if matrix[row][col] > current_max:
                current_max = matrix[row][col]
                visible.add((row, col))

    return visible

def parse_day8_input(data: str) -> list[list[int]]:
    result: list[list[int]] = []
    for line in data.strip().splitlines():
        numbers: list[int] = list(map(int, list(line.strip())))
        result.append(numbers)

    return result