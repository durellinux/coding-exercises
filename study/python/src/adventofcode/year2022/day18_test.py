from src.adventofcode.year2022.day18 import solve_day18_part1, solve_day18_part2
from src.adventofcode.year2022.utils2022 import read_input_2022


def test_example_1():
    data = read_input_2022("day18_example.txt")
    solution = solve_day18_part1(data)
    assert solution == 64

def test_case_1():
    data = read_input_2022("day18.txt")
    solution = solve_day18_part1(data)
    assert solution == 4456

def test_example_2():
    data = read_input_2022("day18_example.txt")
    solution = solve_day18_part2(data)
    assert solution == 58

def test_case_2():
    data = read_input_2022("day18.txt")
    solution = solve_day18_part2(data)
    assert solution == 2497