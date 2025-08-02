moves = ["R", "P", "S"]
abc_moves = ["A", "B", "C"]
xyz_moves = ["X", "Y", "Z"]
offset_moves = [-1, 0, +1]

def solve_day2_part1(data: str) -> int:
    rounds = parse_input_day_2(data)
    total_score = 0

    for instruction in rounds:
        other = map_to_rock_paper_scissor(instruction[0], abc_moves)
        me = map_to_rock_paper_scissor(instruction[1], xyz_moves)

        round_score = score_move(me)

        other_index = moves.index(other)
        me_index = moves.index(me)

        if other == me:
            round_score += 3
        elif (me_index - 1) % len(moves) == other_index:
            round_score += 6

        total_score += round_score

    return total_score

def solve_day2_part2(data: str) -> int:
    rounds = parse_input_day_2(data)
    total_score = 0

    for instruction in rounds:
        other_index = abc_moves.index(instruction[0])
        me_offset_index = xyz_moves.index(instruction[1])
        me_offset = offset_moves[me_offset_index]

        me_index = (other_index + me_offset) % len(moves)
        me = moves[me_index]

        round_score = score_move(me)
        if me_offset == 0:
            round_score += 3
        elif me_offset == 1:
            round_score += 6

        total_score += round_score

    return total_score

def score_move(value: str) -> int:
    if value == "R":
        return 1
    elif value == "P":
        return 2
    elif value == "S":
        return 3

    raise ValueError

def map_to_rock_paper_scissor(value: str, moves_list: list[str]) -> str:
    index = moves_list.index(value)
    return moves[index]

def parse_input_day_2(input: str) -> list[list[str]]:
    content = []

    for line in input.splitlines():
        content.append(line.strip().split(" "))

    return content