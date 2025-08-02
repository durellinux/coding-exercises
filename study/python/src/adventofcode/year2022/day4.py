def solve_day4_part1(data: str) -> int:
    pairs: list[list[str]] = parse_day4_input(data)
    included = 0

    for pair in pairs:
        range1_str, range2_str = pair
        range1 = get_start_end(range1_str)
        range2 = get_start_end(range2_str)

        if is_inside(range1, range2) or is_inside(range2, range1):
            included += 1

    return included

def solve_day4_part2(data: str) -> int:
    pairs: list[list[str]] = parse_day4_input(data)
    included = 0

    for pair in pairs:
        range1_str, range2_str = pair
        range1 = get_start_end(range1_str)
        range2 = get_start_end(range2_str)

        if do_overlap(range1, range2):
            included += 1

    return included

def get_start_end(range_str: str) -> list[int]:
    return list(map(int, range_str.strip().split("-")))

def is_inside(range1: list[int], range2: list[int]) -> bool:
    return range1[0] >= range2[0] and range1[1] <= range2[1]

def do_overlap(range1: list[int], range2: list[int]) -> bool:
    return range1[0] <= range2[1] and range1[1] >= range2[0]

def parse_day4_input(data: str) -> list[list[str]]:
    lines = data.splitlines()
    data = []
    for line in lines:
        data.append(line.strip().split(","))

    return data