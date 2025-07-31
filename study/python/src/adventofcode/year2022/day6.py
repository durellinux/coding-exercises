from collections import deque

class UniqueQueue:
    dequeue: deque[str]
    char_set: set[str]

    def __init__(self):
        self.dequeue = deque()
        self.char_set = set()

    def enqueue(self, char: str):
        if char in self.char_set:
            val = self.dequeue.popleft()
            self.char_set.remove(val)
            while val != char:
                val = self.dequeue.popleft()
                self.char_set.remove(val)

        self.dequeue.append(char)
        self.char_set.add(char)

    def length(self) -> int:
        return len(self.dequeue)

def solve_day6_part1(input_txt):
    return solve_day6(input_txt, 4)

def solve_day6_part2(input_txt):
    return solve_day6(input_txt, 14)

def solve_day6(data: str, target_length: int) -> int:
    characters = list(data)
    unique_queue = UniqueQueue()

    index = 0
    for char in characters:
        index += 1
        unique_queue.enqueue(char)

        if unique_queue.length() == target_length:
            break

    return index