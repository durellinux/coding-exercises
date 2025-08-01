from src.adventofcode.year2022.day8 import solve_day8_part1, solve_day8_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day8_example.txt")
    result = solve_day8_part1(data)
    assert result == 21

def test_case_1():
    data = read_input_2022("day8.txt")
    result = solve_day8_part1(data)
    assert result == 1814

def test_example_2():
    data = read_input_2022("day8_example.txt")
    result = solve_day8_part2(data)
    assert result == 8

def test_case_2():
    data = read_input_2022("day8.txt")
    result = solve_day8_part2(data)
    assert result == 330786