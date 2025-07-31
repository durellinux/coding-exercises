from src.adventofcode.year2022.day6 import solve_day6_part1, solve_day6_part2
from src.adventofcode.year2022.utils2022 import read_input_2022


def test_example_1():
    result = solve_day6_part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb")
    assert result == 7

def test_case_1():
    input_txt = read_input_2022("day6.txt")
    result = solve_day6_part1(input_txt)
    assert result == 1802

def test_example_2():
    result = solve_day6_part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb")
    assert result == 19

def test_case_2():
    input_txt = read_input_2022("day6.txt")
    result = solve_day6_part2(input_txt)
    assert result == 3551