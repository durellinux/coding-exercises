from src.adventofcode.year2022.day4 import parse_day4_input, solve_day4_part1, solve_day4_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    inputTxt = read_input_2022("day4_example.txt")
    input = parse_day4_input(inputTxt)
    result = solve_day4_part1(input)
    assert result == 2

def test_case_1():
    inputTxt = read_input_2022("day4.txt")
    input = parse_day4_input(inputTxt)
    result = solve_day4_part1(input)
    assert result == 532

def test_example_2():
    inputTxt = read_input_2022("day4_example.txt")
    input = parse_day4_input(inputTxt)
    result = solve_day4_part2(input)
    assert result == 4

def test_case_2():
    inputTxt = read_input_2022("day4.txt")
    input = parse_day4_input(inputTxt)
    result = solve_day4_part2(input)
    assert result == 854