from collections import deque

def solve_day6_part1(input_txt):
    return solve_day6(input_txt, 4)

def solve_day6_part2(input_txt):
    return solve_day6(input_txt, 14)

def solve_day6(data: str, target_length: int) -> int:
    characters = list(data)
    dequeue = deque()
    char_set = set()

    index = 0
    for char in characters:
        index += 1
        if char in char_set:
            val = dequeue.popleft()
            char_set.remove(val)
            while val != char:
                val = dequeue.popleft()
                char_set.remove(val)

        dequeue.append(char)
        char_set.add(char)

        if len(dequeue) == target_length:
            break

    return index