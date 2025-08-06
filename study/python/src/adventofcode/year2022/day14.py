from collections import deque

type Coordinate = tuple[int, int]
type Particle = tuple[int, Coordinate]

MAX_SIMULATION_TIME = 100000

def solve_day14_part1(data: str) -> int:
    rocks: set[tuple[int, int]] = parse_day14(data)
    bounding_box = get_bounding_box(rocks)
    source = (0, 500)

    current_list: deque[Particle] = deque()
    next_list: deque[Particle] = deque()

    for i in range(1, MAX_SIMULATION_TIME):
        while len(current_list) > 0:
            current_particle: Particle = current_list.popleft()
            particle_id = current_particle[0]
            current_position = current_particle[1]


            if current_position[0] == bounding_box[0][0]:
                return particle_id - 1

            next_position_down = (current_position[0] + 1, current_position[1])
            next_position_down_left = (current_position[0] + 1, current_position[1] - 1)
            next_position_down_right = (current_position[0] + 1, current_position[1] + 1)

            if next_position_down not in rocks:
                next_list.append((particle_id, next_position_down))
            elif next_position_down_left not in rocks:
                next_list.append((particle_id, next_position_down_left))
            elif next_position_down_right not in rocks:
                next_list.append((particle_id, next_position_down_right))
            else:
                rocks.add(current_position)

        next_list.append((i, source))
        current_list = next_list
        next_list = deque()


    print_rocks(rocks, bounding_box)

    return 0

def solve_day14_part2(data: str) -> int:
    rocks: set[tuple[int, int]] = parse_day14(data)
    bounding_box = get_bounding_box(rocks)
    source = (0, 500)
    floor_level = bounding_box[0][0] + 2

    current_list: deque[Particle] = deque()
    next_list: deque[Particle] = deque()

    for i in range(1, MAX_SIMULATION_TIME):
        while len(current_list) > 0:
            current_particle: Particle = current_list.popleft()
            particle_id = current_particle[0]
            current_position = current_particle[1]

            next_position_down = (current_position[0] + 1, current_position[1])
            next_position_down_left = (current_position[0] + 1, current_position[1] - 1)
            next_position_down_right = (current_position[0] + 1, current_position[1] + 1)

            if next_position_down not in rocks and next_position_down[0] != floor_level:
                next_list.append((particle_id, next_position_down))
            elif next_position_down_left not in rocks and next_position_down_left[0] != floor_level:
                next_list.append((particle_id, next_position_down_left))
            elif next_position_down_right not in rocks and next_position_down_right[0] != floor_level:
                next_list.append((particle_id, next_position_down_right))
            else:
                rocks.add(current_position)

        if source in rocks:
            return i - 1

        next_list.append((i, source))
        current_list = next_list
        next_list = deque()

    print_rocks(rocks, bounding_box)

    return 0

def parse_day14(data: str) -> set[Coordinate]:
    rocks: set[Coordinate] = set()
    paths = data.splitlines()

    for path in paths:
        segments = path.split(" -> ")
        for s_id in range(len(segments) - 1):
            point_1 = list(map(int, segments[s_id].split(",")))
            point_2 = list(map(int, segments[s_id + 1].split(",")))

            min_c = min(point_1[0], point_2[0])
            max_c = max(point_1[0], point_2[0])
            min_r = min(point_1[1], point_2[1])
            max_r = max(point_1[1], point_2[1])

            for c in range(min_c, max_c + 1):
                for r in range(min_r, max_r + 1):
                    rocks.add((r, c))

    return rocks

def get_bounding_box(rocks: set[Coordinate]) -> tuple[Coordinate, Coordinate]:
    max_r = max(rocks, key=lambda rock: rock[0])[0]
    min_c = min(rocks, key=lambda rock: rock[1])[1]
    max_c = max(rocks, key=lambda rock: rock[1])[1]

    return (max_r, min_c - 1), (max_r, max_c + 1)

def print_rocks(rocks: set[Coordinate], bounding_box: tuple[Coordinate, Coordinate]) -> None:
    min_r = 0
    max_r = bounding_box[0][0]
    min_c = bounding_box[0][1]
    max_c = bounding_box[1][1]

    print(bounding_box)

    for r in range(min_r, max_r + 1):
        for c in range(min_c, max_c + 1):
            coord = (r, c)
            if coord in rocks:
                print("#", end="")
            elif coord == (0, 500):
                print("+", end="")
            else:
                print(".", end="")
        print()
