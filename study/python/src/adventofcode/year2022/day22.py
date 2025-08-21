import re
from typing import Literal, Union, Optional, Callable

from src.adventofcode.year2022.utils2022 import Coordinate

type Direction = Literal[">", "<", "^", "v"]
type Turn = Literal["L", "R"]
type Map = dict[Coordinate, str]
type Links = dict[tuple[Coordinate, Direction], tuple[Coordinate, Direction]]
type Instruction = Union[int, Turn]
type Instructions = list[Instruction]

def solve_day22_part1(data: str) -> int:
    position, direction = solve(data, lambda d: parse_day22(d))
    directions_map: dict[Direction, int] = {
        ">": 0,
        "v": 1,
        "<": 2,
        "^": 3,
    }

    return 1000 * position[0] + 4 * position[1] + directions_map[direction]

def solve_day22_part2(data: str) -> int:
    position, direction = solve(data, lambda d: parse_day22_part2(d))
    directions_map: dict[Direction, int] = {
        ">": 0,
        "v": 1,
        "<": 2,
        "^": 3,
    }

    return 1000 * position[0] + 4 * position[1] + directions_map[direction]

def solve(data: str, parse_function: Callable[[str], tuple[Map, Links, Coordinate, Instructions]]) -> tuple[Coordinate, Direction]:
    board, links, start, instructions = parse_function(data)

    movements: list[Coordinate] = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    directions: list[Direction] = [">", "v", "<", "^"]
    directions_map: dict[Direction, int] = {
        ">": 0,
        "v": 1,
        "<": 2,
        "^": 3,
    }

    position = start
    direction: Direction = ">"
    for instruction in instructions:
        if instruction == "R":
            new_direction_index = (directions_map[direction] + 1) % len(directions)
            direction = directions[new_direction_index]
        elif instruction == "L":
            new_direction_index = (directions_map[direction] - 1) % len(directions)
            direction = directions[new_direction_index]
        else:
            moves = 0
            while moves < instruction:
                movement = movements[directions_map[direction]]
                new_position: Coordinate = (position[0] + movement[0], position[1] + movement[1])
                if new_position in board and board[new_position] == "#":
                    break
                elif new_position in board and board[new_position] == ".":
                    position = new_position
                    moves += 1
                elif new_position not in board and (new_position, direction) in links:
                    position = links[(new_position, direction)][0]
                    direction = links[(new_position, direction)][1]
                    moves += 1
                elif new_position not in board and (new_position, direction) not in links:
                    break

        print(f'Moving {position} to {direction}')

    print(f'Arrived at {position} with {direction} bearing')

    return position, direction


def parse_day22(data: str) -> tuple[Map, Links, Coordinate, Instructions]:
    is_parsing_map: bool = True
    instructions: list[Instruction] = []
    board: Map = {}
    links: Links = {}
    start_coordinate: Optional[Coordinate] = None

    lines = data.splitlines()
    map_row = 1
    for line in lines:
        if line.strip() == "":
            is_parsing_map = False
        elif is_parsing_map:
            values = list(line)
            map_col = 1
            first_value = None
            for val in values:
                if val != " ":
                    if first_value is None:
                        first_value = map_col
                        if map_row == 1:
                            start_coordinate = (map_row, map_col)
                    board[(map_row, map_col)] = val
                map_col += 1

            last_value = map_col - 1
            if board[(map_row, first_value)] == "." and board[(map_row, last_value)] == ".":
                links[((map_row, last_value + 1), ">")] = ((map_row, first_value), ">")
                links[((map_row, first_value - 1), "<")] = ((map_row, last_value), "<")
            map_row += 1
        else:
            regex = re.compile(r"(\d+)*([RL])*")
            matches = regex.findall(line)
            for match in matches:
                match_groups = [match[0], match[1]]
                for group in match_groups:
                    if group == "":
                        continue
                    if group == "R" or group == "L":
                        instructions.append(group)
                    else:
                        instructions.append(int(group))

    valid_coordinates = board.keys()
    max_row = max(valid_coordinates, key=lambda c: c[0])[0]
    max_col = max(valid_coordinates, key=lambda c: c[1])[1]

    for c in range(1, max_col + 1):
        first_value: Optional[int] = None
        last_value: Optional[int] = None
        for r in range(1, max_row + 1):
            if (r, c) in board:
                if first_value is None:
                    first_value = r

            if first_value is not None and (r, c) not in board:
                last_value = r - 1
                break

        if last_value is None:
            last_value = max_row

        if board[(first_value, c)] == "." and board[(last_value, c)] == ".":
            links[((first_value - 1, c), "^")] = ((last_value, c), "^")
            links[((last_value + 1, c), "v")] = ((first_value, c), "v")

    return board, links, start_coordinate, instructions

def parse_day22_part2(data: str) -> tuple[Map, Links, Coordinate, Instructions]:
    is_parsing_map: bool = True
    instructions: list[Instruction] = []
    board: Map = {}
    links: Links = {}
    start_coordinate: Optional[Coordinate] = None

    lines = data.splitlines()
    map_row = 1
    for line in lines:
        if line.strip() == "":
            is_parsing_map = False
        elif is_parsing_map:
            values = list(line)
            map_col = 1
            first_value = None
            for val in values:
                if val != " ":
                    if first_value is None:
                        first_value = map_col
                        if map_row == 1:
                            start_coordinate = (map_row, map_col)
                    board[(map_row, map_col)] = val
                map_col += 1
            map_row += 1
        else:
            regex = re.compile(r"(\d+)*([RL])*")
            matches = regex.findall(line)
            for match in matches:
                match_groups = [match[0], match[1]]
                for group in match_groups:
                    if group == "":
                        continue
                    if group == "R" or group == "L":
                        instructions.append(group)
                    else:
                        instructions.append(int(group))

    valid_coordinates = board.keys()
    max_row = max(valid_coordinates, key=lambda c: c[0])[0]
    max_col = max(valid_coordinates, key=lambda c: c[1])[1]

    cube_length = int(max(max_row, max_col) / 4)

    if cube_length == 4:
        links_example(board, links, cube_length)
    else:
        links_aoc(board, links, cube_length)

    return board, links, start_coordinate, instructions

def links_example(board: Map, links: Links, cube_length: int) -> None:

    start_1 = (1, 1 + cube_length * 2)
    start_2 = (1 + cube_length, 1)
    start_3 = (1 + cube_length, 1 + cube_length)
    start_4 = (1 + cube_length, 1 + cube_length * 2)
    start_5 = (1 + cube_length * 2, 1 + cube_length * 2)
    start_6 = (1 + cube_length * 2, 1 + cube_length * 3)

    # 1 top - 2 top
    for i in range(cube_length):
        position_1 = (start_1[0], start_1[1] + i)
        position_2 = (start_2[0], start_2[1] + cube_length - 1 - i)
        if board[position_1] == "." and board[position_2] == ".":
            links[((position_1[0] - 1, position_1[1]), '^')] = (position_2, "v")
            links[((position_2[0] - 1, position_2[1]), '^')] = (position_1, "v")

    # 1 left - 3 top
    for i in range(cube_length):
        position_1 = (start_1[0] + i, start_1[1])
        position_3 = (start_3[0], start_3[1] + i)
        if board[position_1] == "." and board[position_3] == ".":
            links[((position_1[0], position_1[1] - 1), '<')] = (position_3, "v")
            links[((position_3[0] - 1, position_3[1]), '^')] = (position_1, ">")

    # 1 right - 6 right
    for i in range(cube_length):
        position_1 = (start_1[0] + i, start_1[1] + cube_length - 1)
        position_6 = (start_6[0] + cube_length - 1 - i, start_6[1] + cube_length - 1)
        if board[position_1] == "." and board[position_6] == ".":
            links[((position_1[0], position_1[1] + 1), '>')] = (position_6, "<")
            links[((position_6[0], position_6[1] + 1), '>')] = (position_1, "<")

    # 2 left - 6 bottom
    for i in range(cube_length):
        position_2 = (start_2[0] + i, start_2[1])
        position_6 = (start_6[0] + cube_length - 1, start_6[1] + cube_length - 1 - i)
        if board[position_2] == "." and board[position_6] == ".":
            links[((position_2[0], position_2[1] - 1), '<')] = (position_6, "^")
            links[((position_6[0] + 1, position_6[1]), 'v')] = (position_2, ">")

    # 2 bottom - 5 bottom
    for i in range(cube_length):
        position_2 = (start_2[0] + cube_length - 1, start_2[1] + i)
        position_5 = (start_5[0] + cube_length - 1, start_5[1] + cube_length - 1 - i)
        if board[position_2] == "." and board[position_5] == ".":
            links[((position_2[0] + 1, position_2[1]), 'v')] = (position_5, "^")
            links[((position_5[0] + 1, position_5[1]), 'v')] = (position_2, "^")

    # 3 bottom - 5 left
    for i in range(cube_length):
        position_3 = (start_3[0] + cube_length - 1, start_3[1] + cube_length - 1 - i)
        position_5 = (start_5[0] + i, start_5[1])
        if board[position_3] == "." and board[position_5] == ".":
            links[((position_3[0] + 1, position_3[1]), 'v')] = (position_5, ">")
            links[((position_5[0], position_5[1] - 1), '<')] = (position_3, "^")

    # 4 right - 6 top
    for i in range(cube_length):
        position_4 = (start_4[0] + i, start_4[1] + cube_length - 1)
        position_6 = (start_6[0], start_6[1] + cube_length - 1 - i)
        if board[position_4] == "." and board[position_6] == ".":
            links[((position_4[0], position_4[1] + 1), '>')] = (position_6, "v")
            links[((position_6[0] - 1, position_6[1]), '^')] = (position_4, "<")

def links_aoc(board: Map, links: Links, cube_length: int) -> None:
    start_1 = (1, 1 + cube_length)
    start_2 = (1, 1 + cube_length * 2)
    start_3 = (1 + cube_length, 1 + cube_length)
    start_4 = (1 + cube_length * 2, 1)
    start_5 = (1 + cube_length * 2, 1 + cube_length)
    start_6 = (1 + cube_length * 3, 1)

    # 1 left - 4 left
    for i in range(cube_length):
        position_1 = (start_1[0] + i, start_1[1])
        position_4 = (start_4[0] + cube_length - 1 - i, start_4[1])
        if board[position_1] == "." and board[position_4] == ".":
            links[((position_1[0], position_1[1] - 1), '<')] = (position_4, ">")
            links[((position_4[0], position_4[1] - 1), '<')] = (position_1, ">")

    # 1 up - 6 left
    for i in range(cube_length):
        position_1 = (start_1[0], start_1[1] + i)
        position_6 = (start_6[0] + i, start_6[1])
        if board[position_1] == "." and board[position_6] == ".":
            links[((position_1[0] - 1, position_1[1]), '^')] = (position_6, ">")
            links[((position_6[0], position_6[1] - 1), '<')] = (position_1, "v")

    # 2 bottom - 3 right
    for i in range(cube_length):
        position_2 = (start_2[0] + cube_length - 1, start_2[1] + i)
        position_3 = (start_3[0] + i, start_3[1] + cube_length - 1)
        if board[position_2] == "." and board[position_3] == ".":
            links[((position_2[0] + 1, position_2[1]), 'v')] = (position_3, "<")
            links[((position_3[0], position_3[1] + 1), '>')] = (position_2, "^")

    # 2 right - 5 right
    for i in range(cube_length):
        position_2 = (start_2[0] + i, start_2[1] + cube_length - 1)
        position_5 = (start_5[0] + cube_length - 1 - i, start_5[1] + cube_length - 1)
        if board[position_2] == "." and board[position_5] == ".":
            links[((position_2[0], position_2[1] + 1), '>')] = (position_5, "<")
            links[((position_5[0], position_5[1] + 1), '>')] = (position_2, "<")

    # 2 up - 6 down
    for i in range(cube_length):
        position_2 = (start_2[0], start_2[1] + i)
        position_6 = (start_6[0] + cube_length - 1, start_6[1] + i)
        if board[position_2] == "." and board[position_6] == ".":
            links[((position_2[0] - 1, position_2[1]), '^')] = (position_6, "^")
            links[((position_6[0] + 1, position_6[1]), 'v')] = (position_2, "v")

    # 3 left - 4 top
    for i in range(cube_length):
        position_3 = (start_3[0] + i, start_3[1])
        position_4 = (start_4[0], start_4[1] + i)
        if board[position_3] == "." and board[position_4] == ".":
            links[((position_3[0], position_3[1] - 1), '<')] = (position_4, "v")
            links[((position_4[0] - 1, position_4[1]), '^')] = (position_3, ">")

    # 5 down - 6 right
    for i in range(cube_length):
        position_5 = (start_5[0] + cube_length - 1, start_5[1] + i)
        position_6 = (start_6[0] + i, start_6[1]  + cube_length - 1)
        if board[position_5] == "." and board[position_6] == ".":
            links[((position_5[0] + 1, position_5[1]), 'v')] = (position_6, "<")
            links[((position_6[0], position_6[1] + 1), '>')] = (position_5, "^")