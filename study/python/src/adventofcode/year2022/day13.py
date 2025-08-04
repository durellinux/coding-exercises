import re
from queue import LifoQueue
from typing import Union
from functools import cmp_to_key


type Elem = Union[list[Elem], int]

def solve_day13_part1(data: str) -> int:
    result = 0
    packets_pairs = parse_day13_part1(data)
    print(packets_pairs)
    for index in range(len(packets_pairs)):
        pair = packets_pairs[index]
        comparison = compare(pair[0], pair[1], 0, 0)
        if comparison < 0:
            result += index + 1
    return result

def solve_day13_part2(data: str) -> int:
    divider1: Elem = [[2]]
    divider2: Elem = [[6]]

    result = 0
    packets_pairs = parse_day13_part1(data)
    all_packets: list[list[Elem]] = [divider1, divider2]
    for pair in packets_pairs:
        all_packets.append(pair[0])
        all_packets.append(pair[1])

    all_packets = sorted(all_packets, key=cmp_to_key(lambda a,b: compare(a, b, 0, 0)))

    position_divider1 = all_packets.index(divider1) + 1
    position_divider2 = all_packets.index(divider2) + 1

    return position_divider1 * position_divider2

def compare(packet1: list[Elem], packet2: list[Elem], index1: int, index2: int) -> int:
    while index1 < len(packet1) and index2 < len(packet2):
        val1 = packet1[index1]
        val2 = packet2[index2]

        if isinstance(val1, int) and isinstance(val2, int):
            diff = val1 - val2
            if diff != 0:
                return diff
            else:
                index1 += 1
                index2 += 1
                continue

        val1_list = [val1] if isinstance(val1, int) else val1
        val2_list = [val2] if isinstance(val2, int) else val2

        diff = compare(val1_list, val2_list, 0, 0)
        if diff != 0:
            return diff
        else:
            index1 += 1
            index2 += 1
            continue

    if index1 == len(packet1) and index2 == len(packet2):
        return 0

    if index1 == len(packet1):
        return -1

    return 1


def parse_day13_part1(data: str) -> list[tuple[list[list[int]], list[list[int]]]]:
    packets_pairs: list[tuple[list[list[int]], list[list[int]]]] = []

    lines = data.splitlines()

    for i in range(0, len(lines), 3):
        packet1_str = parse_packet(lines[i].strip())
        packet2_str = parse_packet(lines[i + 1].strip())
        packets_pairs.append((packet1_str, packet2_str))

    return packets_pairs

def parse_packet(packet_str: str) -> list[list[int]]:
    packet_blocks = [match.group(0) for match in re.finditer(r'(\d{1,3}|\[|\])', packet_str)]

    stack = LifoQueue()
    for char in packet_blocks:
        if char.startswith("["):
            stack.put(char.strip())
        elif char == "]":
            if stack.empty():
                raise Exception(f"Unexpected end of packet: {packet_str}")
            else:
                new_list = list()
                value = stack.get()
                while value != "[":
                    new_list.append(value)
                    value = stack.get()
                new_list.reverse()
                stack.put(new_list)
        else:
            stack.put(int(char.strip()))

    return stack.get()

