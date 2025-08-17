import pytest

from src.adventofcode.year2022.day20 import solve_day20_part1, solve_day20_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1() -> None:
    data = read_input_2022("day20_example.txt")
    solution = solve_day20_part1(data)
    assert solution == 3

def test_case_1() -> None:
    data = read_input_2022("day20.txt")
    solution = solve_day20_part1(data)
    assert solution == 8028

def test_example_2() -> None:
    data = read_input_2022("day20_example.txt")
    solution = solve_day20_part2(data)
    assert solution == 1623178306

@pytest.mark.skip(reason="too slow ~30s :(")
def test_case_2() -> None:
    data = read_input_2022("day20.txt")
    solution = solve_day20_part2(data)
    assert solution == 8798438007673