import math

from src.adventofcode.year2022.utils2022 import Coordinate

def solve_day20_part1(data: str) -> int:
    values, zero = parse_day10(data)
    values_position: dict[Coordinate, int] = {}
    moving_order: list[Coordinate] = values.copy()
    for i in range(len(values)):
        values_position[values[i]] = i

    total_values = len(values)
    mix(values, total_values, moving_order, values_position)

    zero_position = values_position[zero]

    value_1000 = values[(zero_position + 1000) % total_values][0]
    value_2000 = values[(zero_position + 2000) % total_values][0]
    value_3000 = values[(zero_position + 3000) % total_values][0]

    return value_1000 + value_2000 + value_3000

def solve_day20_part2(data: str) -> int:
    decryption_key = 811589153
    values, zero = parse_day10(data)

    values_position: dict[Coordinate, int] = {}
    for i in range(len(values)):
        values[i] = (values[i][0] * decryption_key, values[i][1])
        values_position[values[i]] = i
    moving_order: list[Coordinate] = values.copy()

    total_values = len(values)

    for i in range(10):
        mix(values, total_values, moving_order, values_position)

    zero_position = values_position[zero]

    value_1000 = values[(zero_position + 1000) % total_values][0]
    value_2000 = values[(zero_position + 2000) % total_values][0]
    value_3000 = values[(zero_position + 3000) % total_values][0]

    return value_1000 + value_2000 + value_3000

def mix(values: list[Coordinate], total_values: int, moving_order: list[Coordinate], values_position: dict[Coordinate, int]) -> None:
    for value_pair in moving_order:
        value = value_pair[0]
        index = values_position[value_pair]
        offset = -1 if value < 0 else 1
        exchanges = int(math.fabs(value) % (total_values - 1))

        # new_position = (index + offset * exchanges + total_values) % total_values
        # print(f'Moving {value_pair} from {index} to {new_position} - {exchanges} exchanges')

        if value > 0:
            for i in range(exchanges):
                current_position = (index + i + total_values) % total_values
                next_position = (index + i + 1 + total_values) % total_values
                tmp = values[next_position]
                values[next_position] = values[current_position]
                values_position[values[next_position]] = next_position
                values[current_position] = tmp
                values_position[values[current_position]] = current_position
        if value < 0:
            for i in range(exchanges):
                current_position = (index - i + total_values) % total_values
                next_position = (index - i - 1 + total_values) % total_values
                tmp = values[next_position]
                values[next_position] = values[current_position]
                values_position[values[next_position]] = next_position
                values[current_position] = tmp
                values_position[values[current_position]] = current_position


def parse_day10(data: str) -> tuple[list[Coordinate], Coordinate]:
    lines = data.splitlines()
    values: list[Coordinate] = []
    zero_value = (0, -1)

    for i in range(len(lines)):
        line = lines[i]
        value = int(line.strip())
        values.append((value, i))
        if value == 0:
            zero_value = (0, i)

    return values, zero_value