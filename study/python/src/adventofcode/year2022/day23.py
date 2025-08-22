from src.adventofcode.year2022.utils2022 import Coordinate

type Movement = tuple[Coordinate, list[Coordinate]]

def solve_day23_part1(data: str) -> int:
    rounds = 10
    elves = parse_day23(data)
    movements: list[Movement] = [
        ((-1, 0), [(-1, -1), (-1, 0), (-1, 1)]),
        ((1, 0), [(1, -1), (1, 0), (1, 1)]),
        ((0, -1), [(-1, -1), (0, -1), (1, -1)]),
        ((0, 1), [(-1, 1), (0, 1), (1, 1)]),
    ]
    neighbors: list[Coordinate] = [
        (-1, -1), (-1, 0), (-1, 1),
        (0, -1), (0, 1),
        (1, -1), (1, 0), (1, 1),
    ]

    for r in range(rounds):
        selected_move: dict[Coordinate, list[Coordinate]] = {}

        # Plan moves
        for elf in elves:
            has_elves_around = False

            for neighbor in neighbors:
                if (elf[0] + neighbor[0], elf[1] + neighbor[1]) in elves:
                    has_elves_around = True
                    break

            if not has_elves_around:
                continue

            for movements_index in range(len(movements)):
                movement = movements[(movements_index + r) % len(movements)]
                to_check = movement[1]
                can_move = True
                for test in to_check:
                    check_position = (elf[0] + test[0], elf[1] + test[1])
                    if check_position in elves:
                        can_move = False
                        break

                if can_move:
                    new_position = (elf[0] + movement[0][0], elf[1] + movement[0][1])
                    if new_position not in selected_move:
                        selected_move[new_position] = []

                    selected_move[new_position].append(elf)
                    break

        for new_position in selected_move.keys():
            moved_elves = selected_move[new_position]
            if len(moved_elves) == 1:
                elves.remove(moved_elves[0])
                if new_position in elves:
                    raise "Error"
                elves.add(new_position)

    min_r = min(elves, key=lambda e: e[0])[0]
    max_r = max(elves, key=lambda e: e[0])[0]
    min_c = min(elves, key=lambda e: e[1])[1]
    max_c = max(elves, key=lambda e: e[1])[1]

    rectangle_area = (max_r - min_r + 1) * (max_c - min_c + 1)
    return rectangle_area - len(elves)

def solve_day23_part2(data: str) -> int:
    elves = parse_day23(data)
    movements: list[Movement] = [
        ((-1, 0), [(-1, -1), (-1, 0), (-1, 1)]),
        ((1, 0), [(1, -1), (1, 0), (1, 1)]),
        ((0, -1), [(-1, -1), (0, -1), (1, -1)]),
        ((0, 1), [(-1, 1), (0, 1), (1, 1)]),
    ]
    neighbors: list[Coordinate] = [
        (-1, -1), (-1, 0), (-1, 1),
        (0, -1), (0, 1),
        (1, -1), (1, 0), (1, 1),
    ]

    r = 0
    while True:
        selected_move: dict[Coordinate, list[Coordinate]] = {}

        # Plan moves
        for elf in elves:
            has_elves_around = False

            for neighbor in neighbors:
                if (elf[0] + neighbor[0], elf[1] + neighbor[1]) in elves:
                    has_elves_around = True
                    break

            if not has_elves_around:
                continue

            for movements_index in range(len(movements)):
                movement = movements[(movements_index + r) % len(movements)]
                to_check = movement[1]
                can_move = True
                for test in to_check:
                    check_position = (elf[0] + test[0], elf[1] + test[1])
                    if check_position in elves:
                        can_move = False
                        break

                if can_move:
                    new_position = (elf[0] + movement[0][0], elf[1] + movement[0][1])
                    if new_position not in selected_move:
                        selected_move[new_position] = []

                    selected_move[new_position].append(elf)
                    break

        if len(selected_move.keys()) == 0:
            break

        for new_position in selected_move.keys():
            moved_elves = selected_move[new_position]
            if len(moved_elves) == 1:
                elves.remove(moved_elves[0])
                if new_position in elves:
                    raise "Error"
                elves.add(new_position)

        r = r + 1

    return r + 1

def parse_day23(data: str) -> set[Coordinate]:
    lines = data.strip().splitlines()

    elves: set[Coordinate] = set()

    for r in range(len(lines)):
        values = list(lines[r])
        for c in range(len(values)):
            if values[c] == "#":
                elves.add((r, c))

    return elves

def print_elves(elves: set[Coordinate]):
    min_r = min(elves, key=lambda e: e[0])[0]
    max_r = max(elves, key=lambda e: e[0])[0]
    min_c = min(elves, key=lambda e: e[1])[1]
    max_c = max(elves, key=lambda e: e[1])[1]

    for i in range(min_r, max_r + 1):
        for j in range(min_c, max_c + 1):
            if (i, j) in elves:
                print('#', end='')
            else:
                print('.', end='')
        print()