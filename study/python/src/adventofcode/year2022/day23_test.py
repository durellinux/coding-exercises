from src.adventofcode.year2022.day23 import solve_day23_part1, solve_day23_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day23_example.txt")
    result = solve_day23_part1(data)
    assert result == 110

def test_case_1():
    data = read_input_2022("day23.txt")
    result = solve_day23_part1(data)
    assert result == 4579

def test_example_2():
    data = read_input_2022("day23_example.txt")
    result = solve_day23_part2(data)
    assert result == 20

def test_case_2():
    data = read_input_2022("day23.txt")
    result = solve_day23_part2(data)
    assert result == 1079