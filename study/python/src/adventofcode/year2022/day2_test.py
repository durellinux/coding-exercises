from src.adventofcode.year2022.utils2022 import read_input_2022
from src.adventofcode.year2022.day2 import parse_input_day_2, solve_day2_part1, solve_day2_part2

def test_example():
    data = read_input_2022('day2_example.txt')
    solution = solve_day2_part1(data)
    assert solution == 15

def test_case_1():
    data = read_input_2022('day2.txt')
    solution = solve_day2_part1(data)
    assert solution == 10718

def test_example_2():
    data = read_input_2022('day2_example.txt')
    solution = solve_day2_part2(data)
    assert solution == 12

def test_case_2():
    data = read_input_2022('day2.txt')
    solution = solve_day2_part2(data)
    assert solution == 14652