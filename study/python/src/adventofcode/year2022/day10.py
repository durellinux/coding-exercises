def solve_day10_part1(data: str) -> int:
    instructions = data.strip().splitlines()
    x_register = simulate_instructions(instructions)

    if len(x_register) < 220:
        raise ValueError("Expected at least 220 cycles")

    signal_strength = 0
    for cycle in range(20, 221, 40):
        signal_strength += cycle * x_register[cycle]

    return signal_strength

def solve_day10_part2(data: str) -> list[list[str]]:
    instructions = data.strip().splitlines()
    x_register = simulate_instructions(instructions)

    if len(x_register) < 240:
        raise ValueError("Expected at least 220 cycles")

    crt_height = 6
    crt_width = 40
    crt = [['.' for _ in range(crt_width)] for _ in range(crt_height)]

    for r in range(crt_height):
        for c in range(crt_width):
            current_cycle = r * crt_width + c + 1
            sprite_position = x_register[current_cycle]
            if sprite_position -1 <= c <= sprite_position + 1:
                crt[r][c] = "#"


    print('\n'.join(map(''.join, crt)))

    return crt


def simulate_instructions(instructions: list[str]) -> dict[int, int]:
    x_register = {1: 1}
    current_cycle = 1

    for instruction in instructions:
        if instruction == "noop":
            x_register[current_cycle + 1] = x_register[current_cycle]
            current_cycle += 1
        else:
            parts = instruction.split(" ")
            operation, value = parts[0], int(parts[1])

            x_register[current_cycle + 1] = x_register[current_cycle]
            x_register[current_cycle + 2] = x_register[current_cycle + 1] + value
            current_cycle += 2

    return x_register