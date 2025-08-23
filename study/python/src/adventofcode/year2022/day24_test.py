import pytest

from src.adventofcode.year2022.day24 import solve_day24_part1, solve_day24_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day24_example.txt")
    solution = solve_day24_part1(data)
    assert solution == 18

def test_case_1():
    data = read_input_2022("day24.txt")
    solution = solve_day24_part1(data)
    assert solution == 269

def test_example_2():
    data = read_input_2022("day24_example.txt")
    solution = solve_day24_part2(data)
    assert solution == 54

@pytest.mark.skip(reason="too slow ~30s :(")
def test_case_2():
    data = read_input_2022("day24.txt")
    solution = solve_day24_part2(data)
    assert solution == 825