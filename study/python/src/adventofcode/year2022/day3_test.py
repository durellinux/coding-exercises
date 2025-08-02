from src.adventofcode.year2022.day3 import solve_day3_part1, solve_day3_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example():
    data = read_input_2022('day3_example.txt')
    solution = solve_day3_part1(data.splitlines())
    assert solution == 157

def test_case_1():
    data = read_input_2022('day3.txt')
    solution = solve_day3_part1(data.splitlines())
    assert solution == 7889

def test_example_2():
    data = read_input_2022('day3_example.txt')
    solution = solve_day3_part2(data.splitlines())
    assert solution == 70

def test_case_2():
    data = read_input_2022('day3.txt')
    solution = solve_day3_part2(data.splitlines())
    assert solution == 2825