from src.adventofcode.year2022.day12 import solve_day12_part1, solve_day12_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day12_example.txt")
    solution = solve_day12_part1(data)
    assert solution == 31

def test_case_1():
    data = read_input_2022("day12.txt")
    solution = solve_day12_part1(data)
    assert solution == 370

def test_example_2():
    data = read_input_2022("day12_example.txt")
    solution = solve_day12_part2(data)
    assert solution == 29

def test_case_2():
    data = read_input_2022("day12.txt")
    solution = solve_day12_part2(data)
    assert solution == 363