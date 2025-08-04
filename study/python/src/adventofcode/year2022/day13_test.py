from src.adventofcode.year2022.day13 import solve_day13_part1, solve_day13_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day13_example.txt")
    result = solve_day13_part1(data)
    assert result == 13

def test_case_1():
    data = read_input_2022("day13.txt")
    result = solve_day13_part1(data)
    assert result == 5198

def test_example_2():
    data = read_input_2022("day13_example.txt")
    result = solve_day13_part2(data)
    assert result == 140

def test_case_2():
    data = read_input_2022("day13.txt")
    result = solve_day13_part2(data)
    assert result == 22344
