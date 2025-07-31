from src.adventofcode.year2022.day5 import parse_input_day5, solve_day5_part1, solve_day5_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    input_txt = read_input_2022("day5_example.txt")
    input_data = parse_input_day5(input_txt, 3)
    result = solve_day5_part1(input_data)
    assert result == "CMZ"

def test_case_1():
    input_txt = read_input_2022("day5.txt")
    input_data = parse_input_day5(input_txt, 9)
    result = solve_day5_part1(input_data)
    assert result == "BWNCQRMDB"

def test_example_2():
    input_txt = read_input_2022("day5_example.txt")
    input_data = parse_input_day5(input_txt, 3)
    result = solve_day5_part2(input_data)
    assert result == "MCD"

def test_case_2():
    input_txt = read_input_2022("day5.txt")
    input_data = parse_input_day5(input_txt, 9)
    result = solve_day5_part2(input_data)
    assert result == "NHWZCBNBF"

