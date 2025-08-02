from src.adventofcode.year2022.day4 import parse_day4_input, solve_day4_part1, solve_day4_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day4_example.txt")
    result = solve_day4_part1(data)
    assert result == 2

def test_case_1():
    data = read_input_2022("day4.txt")
    result = solve_day4_part1(data)
    assert result == 532

def test_example_2():
    data = read_input_2022("day4_example.txt")
    result = solve_day4_part2(data)
    assert result == 4

def test_case_2():
    data = read_input_2022("day4.txt")
    result = solve_day4_part2(data)
    assert result == 854