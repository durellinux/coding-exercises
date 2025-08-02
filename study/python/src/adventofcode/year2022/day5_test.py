from src.adventofcode.year2022.day5 import parse_input_day5, solve_day5_part1, solve_day5_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    input_txt = read_input_2022("day5_example.txt")
    result = solve_day5_part1(input_txt)
    assert result == "CMZ"

def test_case_1():
    input_txt = read_input_2022("day5.txt")
    result = solve_day5_part1(input_txt)
    assert result == "BWNCQRMDB"

def test_example_2():
    input_txt = read_input_2022("day5_example.txt")
    result = solve_day5_part2(input_txt)
    assert result == "MCD"

def test_case_2():
    input_txt = read_input_2022("day5.txt")
    result = solve_day5_part2(input_txt)
    assert result == "NHWZCBNBF"

