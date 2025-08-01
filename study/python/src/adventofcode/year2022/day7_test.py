from src.adventofcode.year2022.day7 import solve_day7_part1, solve_day7_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    input_data = read_input_2022("day7_example.txt")
    result = solve_day7_part1(input_data)
    assert result == 95437

def test_case_1():
    input_data = read_input_2022("day7.txt")
    result = solve_day7_part1(input_data)
    assert result == 2104783

def test_example_2():
    input_data = read_input_2022("day7_example.txt")
    result = solve_day7_part2(input_data)
    assert result == 24933642

def test_case_2():
    input_data = read_input_2022("day7.txt")
    result = solve_day7_part2(input_data)
    assert result == 5883165