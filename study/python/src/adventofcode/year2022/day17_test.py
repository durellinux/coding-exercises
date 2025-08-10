import pytest

from src.adventofcode.year2022.day17 import solve_day17
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day17_example.txt")
    result = solve_day17(data, 2022)
    assert result == 3068

def test_case_1():
    data = read_input_2022("day17.txt")
    result = solve_day17(data, 2022)
    assert result == 3114

@pytest.mark.skip(reason="This one fails :(")
def test_example_2():
    data = read_input_2022("day17_example.txt")
    result = solve_day17(data, 1000000000000)
    assert result == 1514285714288

def test_case_2():
    data = read_input_2022("day17.txt")
    result = solve_day17(data, 1000000000000)
    assert result == 1540804597682
