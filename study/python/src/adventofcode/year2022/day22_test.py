from src.adventofcode.year2022.day22 import solve_day22_part1, solve_day22_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day22_example.txt")
    solution = solve_day22_part1(data)
    assert solution == 6032

def test_case_1():
    data = read_input_2022("day22.txt")
    solution = solve_day22_part1(data)
    assert solution == 50412

def test_example_2():
    data = read_input_2022("day22_example.txt")
    solution = solve_day22_part2(data)
    assert solution == 5031

def test_case_2():
    data = read_input_2022("day22.txt")
    solution = solve_day22_part2(data)
    assert solution == 130068
