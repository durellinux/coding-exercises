from src.adventofcode.year2022.day16 import solve_day16_part1, solve_day16_part2, solve_day16_part2_iterative
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day16_example.txt")
    solution = solve_day16_part1(data)
    assert solution == 1651

def test_case_1():
    data = read_input_2022("day16.txt")
    solution = solve_day16_part1(data)
    assert solution == 1638

def test_example_2():
    data = read_input_2022("day16_example.txt")
    solution = solve_day16_part2(data)
    assert solution == 1707

def test_case_2():
    data = read_input_2022("day16.txt")
    solution = solve_day16_part2(data)
    assert solution == 1638

def test_example_2_iterative():
    data = read_input_2022("day16_example.txt")
    solution = solve_day16_part2_iterative(data)
    assert solution == 1707

def test_case_2_iterative():
    data = read_input_2022("day16.txt")
    solution = solve_day16_part2_iterative(data)
    assert solution == 1638