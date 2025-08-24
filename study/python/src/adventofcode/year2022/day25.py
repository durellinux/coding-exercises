def solve_day25_part1(data: str) -> str:
    numbers = parse_day25(data)

    total = 0
    for number in numbers:
        total += decode_number(number)

    return encode_number(total)

def decode_number(number: list[str]) -> int:
    number.reverse()

    decoded_number = 0
    for p in range(len(number)):
        symbol = number[p]
        value = decode_symbol(symbol)
        power_value = 5 ** p
        decoded_number += (value * power_value)

    return decoded_number

def encode_number(number: int) -> str:
    encoded = ""
    remainders = []

    cur_number = number
    while cur_number != 0:
        remainder = cur_number % 5
        cur_number //= 5
        if remainder >= 3:
            remainder = remainder - 5
            cur_number += 1

        remainders.append(remainder)

    remainders.reverse()
    for remainder in remainders:
        encoded += encode_symbol(remainder)

    return encoded


def encode_symbol(symbol: int) -> str:
    if symbol == -2:
        return "="
    elif symbol == -1:
        return "-"
    elif symbol == 0:
        return "0"
    elif symbol == 1:
        return "1"
    elif symbol == 2:
        return "2"

    raise ValueError(f"Unknown symbol {symbol}")

def decode_symbol(symbol: str) -> int:
    if symbol == "0":
        return 0
    if symbol == "1":
        return 1
    if symbol == "2":
        return 2
    if symbol == "-":
        return -1
    if symbol == "=":
        return -2

    raise ValueError(f"Unknown symbol: {symbol}")

def parse_day25(data: str) -> list[list[str]]:
    lines = data.splitlines()

    numbers: list[list[str]] = []
    for line in lines:
        numbers.append(list(line.strip()))

    return numbers