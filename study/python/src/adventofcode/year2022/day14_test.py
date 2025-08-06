from src.adventofcode.year2022.day14 import solve_day14_part1, solve_day14_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day14_example.txt")
    solution = solve_day14_part1(data)
    assert solution == 24

def test_case_1():
    data = read_input_2022("day14.txt")
    solution = solve_day14_part1(data)
    assert solution == 1133

def test_example_2():
    data = read_input_2022("day14_example.txt")
    solution = solve_day14_part2(data)
    assert solution == 93

def test_case_2():
    data = read_input_2022("day14.txt")
    solution = solve_day14_part2(data)
    assert solution == 27566

