from src.adventofcode.year2022.day21 import solve_day21_part1, solve_day21_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day21_example.txt")
    solution = solve_day21_part1(data)
    assert solution == 152

def test_case_1():
    data = read_input_2022("day21.txt")
    solution = solve_day21_part1(data)
    assert solution == 41857219607906

def test_example_2():
    data = read_input_2022("day21_example.txt")
    solution = solve_day21_part2(data)
    assert solution == 301

def test_case_2():
    data = read_input_2022("day21.txt")
    solution = solve_day21_part2(data)
    assert solution == 3916936880448