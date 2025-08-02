from src.adventofcode.year2022.day11 import solve_day11_part1, solve_day11_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day11_example.txt")
    result = solve_day11_part1(data)
    assert result == 10605

def test_case_1():
    data = read_input_2022("day11.txt")
    result = solve_day11_part1(data)
    assert result == 50172

def test_example_2():
    data = read_input_2022("day11_example.txt")
    result = solve_day11_part2(data)
    assert result == 2713310158

def test_case_2():
    data = read_input_2022("day11.txt")
    result = solve_day11_part2(data)
    assert result == 11614682178