def solve_day3_part1(data: list[str]) -> int:
    score = 0

    for rucksack in data:
        size = len(rucksack)
        comp1 = rucksack[:int(size/2)]
        comp2 = rucksack[int(size/2):]
        comp1_set = set(list(comp1))
        comp2_set = set(list(comp2))

        wrong_element = comp1_set.intersection(comp2_set)
        score += evaluate_priority(list(wrong_element)[0])

    return score

def solve_day3_part2(data: list[str]) -> int:
    score = 0

    for i in range(0, len(data), 3):
        rucksack_1 = set(list(data[i]))
        rucksack_2 = set(list(data[i+1]))
        rucksack_3 = set(list(data[i+2]))

        common = rucksack_1.intersection(rucksack_2)
        badge_set = common.intersection(rucksack_3)
        score += evaluate_priority(list(badge_set)[0])

    return score

def evaluate_priority(element: str) -> int:
    if 'a' <= element <= 'z':
        return ord(element) - ord('a') + 1
    elif 'A' <= element <= 'Z':
        return ord(element) - ord('A') + 27

    raise ValueError