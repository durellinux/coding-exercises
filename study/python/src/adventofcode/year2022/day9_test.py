from src.adventofcode.year2022.day9 import solve_day9_part1, solve_day9_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day9_example.txt")
    result = solve_day9_part1(data)
    assert result == 13

def test_case_1():
    data = read_input_2022("day9.txt")
    result = solve_day9_part1(data)
    assert result == 5858

def test_example_2():
    data = read_input_2022("day9_example.txt")
    result = solve_day9_part2(data)
    assert result == 1

def test_case_2():
    data = read_input_2022("day9.txt")
    result = solve_day9_part2(data)
    assert result == 2602
