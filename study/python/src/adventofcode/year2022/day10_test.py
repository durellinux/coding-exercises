from src.adventofcode.year2022.day10 import solve_day10_part1, solve_day10_part2
from src.adventofcode.year2022.utils2022 import read_input_2022

def test_debug():
    solve_day10_part1("""noop\naddx 3\naddx -5""")

def test_example_1():
    data = read_input_2022("day10_example.txt")
    result = solve_day10_part1(data)
    assert result == 13140

def test_case_1():
    data = read_input_2022("day10.txt")
    result = solve_day10_part1(data)
    assert result == 16880

def test_example_2():
    data = read_input_2022("day10_example.txt")
    result = solve_day10_part2(data)

    expected = """##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######....."""

    expected_matrix = list(map(lambda line: list(line), expected.splitlines()))
    assert result == expected_matrix


def test_case_2():
    print() # To align the output print
    data = read_input_2022("day10.txt")
    result = solve_day10_part2(data)

    expected = """###..#..#..##..####..##....##.###..###..
#..#.#.#..#..#....#.#..#....#.#..#.#..#.
#..#.##...#..#...#..#..#....#.###..#..#.
###..#.#..####..#...####....#.#..#.###..
#.#..#.#..#..#.#....#..#.#..#.#..#.#.#..
#..#.#..#.#..#.####.#..#..##..###..#..#."""

    expected_matrix = list(map(lambda line: list(line), expected.splitlines()))
    assert result == expected_matrix