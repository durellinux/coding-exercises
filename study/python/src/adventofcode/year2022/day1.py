def solve_day1_part1(data: str) -> int:
    elves = parse_input_day1(data)
    max_calories: int = 0

    for elf in elves:
        total_calories: int = sum(elf)
        if total_calories > max_calories:
            max_calories = total_calories

    return max_calories

def solve_day1_part2(data: str) -> int:
    elves = parse_input_day1(data)
    total_calories: list[int] = []

    for elf in elves:
        total_calories.append(sum(elf))

    total_calories.sort(reverse=True)
    return sum(total_calories[:3])

def parse_input_day1(data: str) -> list[list[int]]:
    elves: list[list[int]] = []
    current_elf: list[int] = []

    for line in data.splitlines():
        if line.strip() == "":
            if current_elf:
                elves.append(current_elf)
                current_elf = []
        else:
            current_elf.append(int(line.strip()))

    if current_elf:
        elves.append(current_elf)

    return elves