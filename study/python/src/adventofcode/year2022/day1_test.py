import os

from src.adventofcode.year2022.day1 import parseInputDay1, solveDay1, solveDay1Part2
from src.adventofcode.year2022.utils2022 import read_input_2022


def test_day1_part1():
    inputTxt = read_input_2022('day1.txt')
    input = parseInputDay1(inputTxt)
    solution = solveDay1(input)
    assert solution == 70509

def test_day1_part2():
    inputTxt = read_input_2022('day1.txt')
    input = parseInputDay1(inputTxt)
    solution = solveDay1Part2(input)
    assert solution == 208567
