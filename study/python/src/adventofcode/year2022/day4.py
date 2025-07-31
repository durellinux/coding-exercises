from os.path import split
from src.adventofcode.utils import read_input_file

def solve_day4_part1(pairs: list[list[str]]) -> int:
    included = 0

    for pair in pairs:
        range1_str, range2_str = pair
        range1 = get_start_end(range1_str)
        range2 = get_start_end(range2_str)

        if is_inside(range1, range2) or is_inside(range2, range1):
            included += 1

    return included

def solve_day4_part2(pairs: list[list[str]]) -> int:
    included = 0

    for pair in pairs:
        range1_str, range2_str = pair
        range1 = get_start_end(range1_str)
        range2 = get_start_end(range2_str)

        if do_overlap(range1, range2):
            included += 1

    return included

def get_start_end(range: str) -> list[int]:
    return list(map(int, range.strip().split("-")))

def is_inside(range1: list[int], range2: list[int]) -> bool:
    return range1[0] >= range2[0] and range1[1] <= range2[1]

def do_overlap(range1: list[int], range2: list[int]) -> bool:
    return range1[0] <= range2[1] and range1[1] >= range2[0]

def parse_day4_input(input: str) -> list[list[str]]:
    lines = input.splitlines()
    data = []
    for line in lines:
        data.append(line.strip().split(","))

    return data