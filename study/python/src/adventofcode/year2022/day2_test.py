from src.adventofcode.year2022.utils2022 import read_input_2022
from src.adventofcode.year2022.day2 import parse_input_day_2, solve_day2_part1, solve_day2_part2


def test_example():
    inputTxt = read_input_2022('day2_example.txt')
    input = parse_input_day_2(inputTxt)
    solution = solve_day2_part1(input)
    assert solution == 15

def test_case_1():
    inputTxt = read_input_2022('day2.txt')
    input = parse_input_day_2(inputTxt)
    solution = solve_day2_part1(input)
    assert solution == 10718

def test_example_2():
    inputTxt = read_input_2022('day2_example.txt')
    input = parse_input_day_2(inputTxt)
    solution = solve_day2_part2(input)
    assert solution == 12

def test_case_2():
    inputTxt = read_input_2022('day2.txt')
    input = parse_input_day_2(inputTxt)
    solution = solve_day2_part2(input)
    assert solution == 14652