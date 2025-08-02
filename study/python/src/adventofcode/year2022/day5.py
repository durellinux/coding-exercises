type Day5Input = tuple[list[list[str]], list[list[int]]]

def solve_day5_part1(data: str) -> str:
    total_stacks = get_stacks_number(data)
    state = parse_input_day5(data, total_stacks)
    return solve_day5(state, False)

def solve_day5_part2(data: str) -> str:
    total_stacks = get_stacks_number(data)
    state = parse_input_day5(data, total_stacks)
    return solve_day5(state, True)

def solve_day5(state: Day5Input, do_reverse) -> str:
    stacks: list[list[str]] = state[0]
    instructions: list[list[int]] = state[1]

    for instruction in instructions:
        from_stack = instruction[1]
        to_stack = instruction[2]
        to_move = []
        for crate in range(instruction[0]):
            value = stacks[from_stack].pop()
            to_move.append(value)

        insertion_range = range(len(to_move)) if not do_reverse else reversed(range(len(to_move)))
        for index in insertion_range:
            stacks[to_stack].append(to_move[index])

    top_crates = []
    for stack in stacks:
        if len(stack) > 0:
            top_crates.append(stack[-1])

    return "".join(top_crates)

def parse_input_day5(data: str, total_stacks: int) -> Day5Input:
    stacks: list[list[str]] = []
    instructions: list[list[int]] = []

    for stack in range(total_stacks):
        stacks.append([])

    parsing_stacks: bool = True
    for line in data.splitlines():
        if len(line.strip()) == 0:
            continue

        if parsing_stacks and line[1] == '1':
            parsing_stacks = False
            continue

        if parsing_stacks:
            for stack in range(total_stacks):
                index = 1 + 4 * stack
                value = line[index] if index < len(line) else ' '
                if value != ' ':
                    stacks[stack].append(value)
        else:
            data = line.strip().split(' ')
            instructions.append([int(data[1]), int(data[3]) - 1, int(data[5]) - 1])

    for stack in range(total_stacks):
        stacks[stack].reverse()

    return stacks, instructions

def get_stacks_number(data: str) -> int:
    for line in data.splitlines():
        if line.startswith(' 1'):
            return int(line[-1])

    raise ValueError