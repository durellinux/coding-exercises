from src.adventofcode.year2022.day25 import solve_day25_part1
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_example_1():
    data = read_input_2022("day25_example.txt")
    solution = solve_day25_part1(data)
    assert solution == "2=-1=0"

def test_case_1():
    data = read_input_2022("day25.txt")
    solution = solve_day25_part1(data)
    assert solution == "2=10---0===-1--01-20"