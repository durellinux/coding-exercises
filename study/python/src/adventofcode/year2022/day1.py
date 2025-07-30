def solveDay1(input: list[list[int]]) -> int:
    max_calories: int = 0

    for elf in input:
        total_calories: int = sum(elf)
        if total_calories > max_calories:
            max_calories = total_calories

    return max_calories

def solveDay1Part2(input: list[list[int]]) -> int:
    total_calories: list[int] = []

    for elf in input:
        total_calories.append(sum(elf))

    total_calories.sort(reverse=True)
    return sum(total_calories[:3])

def parseInputDay1(input: str) -> list[list[int]]:
    elves: list[list[int]] = []
    current_elf: list[int] = []

    for line in input.splitlines():
        if line.strip() == "":
            if current_elf:
                elves.append(current_elf)
                current_elf = []
        else:
            current_elf.append(int(line.strip()))

    if current_elf:
        elves.append(current_elf)

    return elves