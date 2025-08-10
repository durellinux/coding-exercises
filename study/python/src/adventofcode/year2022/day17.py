from abc import ABC
from typing import Callable, Optional
from src.adventofcode.year2022.utils2022 import Coordinate


class TetrisBrick(ABC):
    position: Coordinate

    def __init__(self, position: Coordinate):
        self.position = position

    def update_position(self, position: Coordinate):
        self.position = position

    def get_occupied_coordinates(self) -> set[Coordinate]:
        raise NotImplementedError("To be overridden in subclass")

    def get_line_range(self) -> list[int]:
        raise NotImplementedError("To be overridden in subclass")

    def get_block_width(self) -> int:
        raise NotImplementedError("To be overridden in subclass")

def solve_day17(data: str, pieces_to_place: int) -> int:
    width = 7

    pieces_per_line: dict[int, set[Coordinate]] = dict()
    cycle_detection: dict[tuple[int, int, int], tuple[int, int, int]] = {}

    commands = parse_day17(data)
    block_generators: list[Callable[[Coordinate], TetrisBrick]] = [
        lambda position: HorizontalBrick(position),
        lambda position: CrossBrick(position),
        lambda position: LBrick(position),
        lambda position: VerticalBrick(position),
        lambda position: SquareBrick(position),
    ]

    current_status, status_at_loop = simulate_tetris_with_cycle_detection(width, 0, 0, pieces_to_place, commands, block_generators, pieces_per_line, cycle_detection)

    if status_at_loop is None:
        return current_status[1]

    loop_height_increase = current_status[1] - status_at_loop[1]
    loop_length = current_status[0] - status_at_loop[0]
    loop_iterations = int((pieces_to_place - current_status[0]) / loop_length)
    placed_pieces_after_loop = current_status[0] + loop_length * loop_iterations
    height_added_by_loop = loop_height_increase * loop_iterations
    remaining_after_loops = pieces_to_place - placed_pieces_after_loop
    if remaining_after_loops > 0:
        final_placement = simulate_tetris_with_cycle_detection(width, current_status[2], current_status[1], current_status[1] + remaining_after_loops, commands, block_generators, pieces_per_line, {})
        print(final_placement)

    return len(pieces_per_line) + height_added_by_loop

def simulate_tetris_with_cycle_detection(
        width: int,
        start_time: int,
        already_placed_pieces: int,
        pieces_to_place: int,
        commands: list[int],
        block_generators: list[Callable[[Coordinate], TetrisBrick]],
        pieces_per_line: dict[int, set[Coordinate]],
        cycle_detection: dict[tuple[int, int, int], tuple[int, int, int]]
) -> tuple[tuple[int, int, int], Optional[tuple[int, int, int]]]:
    current_time = start_time
    placed_pieces = already_placed_pieces

    cycle_to_match: Optional[int] = None

    while placed_pieces < pieces_to_place:
        block_c = 2
        block_r = len(pieces_per_line) + 3
        block = get_block(placed_pieces, (block_r, block_c), block_generators)
        placed = False
        while not placed:
            command = get_command(current_time, commands)
            current_position = block.position
            next_lateral_position = (current_position[0], current_position[1] + command)
            block.update_position(next_lateral_position)
            if do_intersect(block, pieces_per_line, width):
                block.update_position(current_position)
            else:
                current_position = block.position

            next_down_position = (current_position[0] - 1, current_position[1])
            block.update_position(next_down_position)
            if do_intersect(block, pieces_per_line, width):
                block.update_position(current_position)
                placed = True
                occupied_positions = block.get_occupied_coordinates()
                for occupied_position in occupied_positions:
                    if occupied_position[0] not in pieces_per_line:
                        pieces_per_line[occupied_position[0]] = set()
                    pieces_per_line[occupied_position[0]].add(occupied_position)
            else:
                current_position = block.position

            current_time += 1

        placed_pieces += 1
        cycle_detection_key = get_cycle_detection_key(current_time, placed_pieces, pieces_per_line, block,
                                                      commands,
                                                      block_generators)
        if cycle_detection_key in cycle_detection:
            if cycle_to_match is None:
                cycle_to_match = placed_pieces - cycle_detection[cycle_detection_key][0]
            elif cycle_to_match == 0:
                # print("Found cycle detection key:", (placed_pieces, len(pieces_per_line), current_time), cycle_detection[cycle_detection_key])
                return (placed_pieces, len(pieces_per_line), current_time), cycle_detection[cycle_detection_key]
            else:
                cycle_to_match -= 1
                # print("Continuous cycle detection:", cycle_to_match)
        elif cycle_to_match is not None:
            # print("Failed to find cycle at piece:", cycle_to_match)
            cycle_return = None

        cycle_detection[cycle_detection_key] = (placed_pieces, len(pieces_per_line), current_time)

    return (placed_pieces, len(pieces_per_line), current_time), None

def do_intersect(block: TetrisBrick, pieces_per_line: dict[int, set[Coordinate]], max_width: int):
    block_position = block.position
    if block_position[1] < 0 or block_position[1] + block.get_block_width() - 1 >= max_width:
        return True

    if block_position[0] < 0:
        return True

    lines_range = block.get_line_range()
    block_coordinates = block.get_occupied_coordinates()
    for line in lines_range:
        occupied_coordinates = pieces_per_line[line] if line in pieces_per_line else set()
        if len(block_coordinates.intersection(occupied_coordinates)) > 0:
            return True

    return False

def get_command(time: int, commands: list[int]) -> int:
    command_index = time % len(commands)
    return commands[command_index]

def get_block(time: int, position: Coordinate, block_generator: list[Callable[[Coordinate], TetrisBrick]]) -> TetrisBrick:
    generator_index = time % len(block_generator)
    return block_generator[generator_index](position)

def print_all_things(pieces_per_line: dict[int, set[Coordinate]], max_width: int):
    if len(pieces_per_line) == 0:
        print("No pieces yet.")

    max_line = max(pieces_per_line.keys())
    for r in range(0, max_line + 1):
        print("|", end="")
        line = max_line - r
        if line not in pieces_per_line:
            print(".......", end="")
        else:
            for c in range(max_width):
                if (line, c) in pieces_per_line[line]:
                    print("#", end="")
                else:
                    print(".", end="")

        print("|")

def get_cycle_detection_key(current_time: int, placed_pieces: int, pieces_per_line: dict[int, set[Coordinate]], block: TetrisBrick, commands: list[int], generators: list[Callable]) -> tuple[int, int, int]:
    command_index = current_time % len(commands)
    block_generator_index = placed_pieces % len(generators)

    block_position = block.position
    tetris_hash = 0
    for line in range(-100, 100):
        row = block_position[0] + line
        line_hash = 0
        if row in pieces_per_line:
            for occupied_position in pieces_per_line[row]:
                line_hash |= (1 << occupied_position[1])
        tetris_hash = (tetris_hash << 8) | line_hash

    return command_index, block_generator_index, tetris_hash


def parse_day17(data: str) -> list[int]:
    commands_str = list(data.strip())
    commands: list[int] = []

    for command in commands_str:
        if command == "<":
            commands.append(-1)
        else:
            commands.append(1)

    return commands


class HorizontalBrick(TetrisBrick):
    def __init__(self, position: Coordinate):
        super().__init__(position)

    def get_occupied_coordinates(self) -> set[Coordinate]:
        positions = []
        for i in range(4):
            positions.append((self.position[0], self.position[1] + i))

        return set(positions)

    def get_line_range(self) -> list[int]:
        return list(range(self.position[0], self.position[0] + 1))

    def get_block_width(self) -> int:
        return 4

class VerticalBrick(TetrisBrick):
    def __init__(self, position: Coordinate):
        super().__init__(position)

    def get_occupied_coordinates(self) -> set[Coordinate]:
        positions = set()
        for i in range(4):
            positions.add((self.position[0] + i, self.position[1]))

        return positions

    def get_line_range(self) -> list[int]:
        return list(range(self.position[0], self.position[0] + 4))

    def get_block_width(self) -> int:
        return 1

class LBrick(TetrisBrick):
    def __init__(self, position: Coordinate):
        super().__init__(position)

    def get_occupied_coordinates(self) -> set[Coordinate]:
        positions = [
            (self.position[0], self.position[1]),
            (self.position[0], self.position[1] + 1),
            (self.position[0], self.position[1] + 2),
            (self.position[0] + 1, self.position[1] + 2),
            (self.position[0] + 2, self.position[1] + 2),
        ]
        return set(positions)

    def get_line_range(self) -> list[int]:
        return list(range(self.position[0], self.position[0] + 3))

    def get_block_width(self) -> int:
        return 3

class CrossBrick(TetrisBrick):
    def __init__(self, position: Coordinate):
        super().__init__(position)

    def get_occupied_coordinates(self) -> set[Coordinate]:
        positions = [
            (self.position[0], self.position[1] + 1),
            (self.position[0] + 1, self.position[1]),
            (self.position[0] + 1, self.position[1] + 1),
            (self.position[0] + 1, self.position[1] + 2),
            (self.position[0] + 2, self.position[1] + 1),
        ]
        return set(positions)

    def get_line_range(self) -> list[int]:
        return list(range(self.position[0], self.position[0] + 3))

    def get_block_width(self) -> int:
        return 3

class SquareBrick(TetrisBrick):
    def __init__(self, position: Coordinate):
        super().__init__(position)

    def get_occupied_coordinates(self) -> set[Coordinate]:
        positions = set()
        for x in range(2):
            for y in range(2):
                positions.add((self.position[0] + x, self.position[1] + y))

        return positions

    def get_line_range(self) -> list[int]:
        return list(range(self.position[0], self.position[0] + 2))

    def get_block_width(self) -> int:
        return 2