import os

from src.adventofcode.year2022.day1 import parse_input_day1, solve_day1_part1, solve_day1_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_day1_part1():
    data = read_input_2022('day1.txt')
    solution = solve_day1_part1(data)
    assert solution == 70509

def test_day1_part2():
    data = read_input_2022('day1.txt')
    solution = solve_day1_part2(data)
    assert solution == 208567
