import pytest

from src.adventofcode.year2022.day15 import solve_day15_part1, solve_day15_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day15_example.txt")
    solution = solve_day15_part1(data, 10)
    assert solution == 26

def test_case_1():
    data = read_input_2022("day15.txt")
    solution = solve_day15_part1(data, 2000000)
    assert solution == 4951427

def test_example_2():
    data = read_input_2022("day15_example.txt")
    solution = solve_day15_part2(data, 20)
    assert solution == 56000011

@pytest.mark.skip(reason="too slow ~30s :(")
def test_case_2():
    data = read_input_2022("day15.txt")
    solution = solve_day15_part2(data, 4000000)
    assert solution == 13029714573243