from typing import Callable, Union

class ModuloNumber:
    values: dict[int, int] = {}

    def __init__(self, number: int, modulos: list[int]) -> None:
        self.values = dict()
        for m in modulos:
            self.values[m] = number % m

    def __add__(self, other: int) -> 'ModuloNumber':
        for modulo in self.values.keys():
            number = self.values[modulo]
            number += other
            self.values[modulo] = number % modulo

        return self

    def __mul__(self, other: Union[int, 'ModuloNumber']) -> 'ModuloNumber':
        if isinstance(other, int):
            for modulo in self.values.keys():
                number = self.values[modulo]
                number *= other
                self.values[modulo] = number % modulo
        else:
            for modulo in self.values.keys():
                number1 = self.values[modulo]
                number2 = other.values[modulo]
                number1 *= number2
                self.values[modulo] = number1 % modulo

        return self

    def __mod__(self, other: int) -> int:
        result = self.values.get(other)
        if result is None:
            raise ValueError(f'Modulo number {other} does not exist')

        return result

    def __truediv__(self, value: int) -> None:
        raise NotImplementedError()

class Monkey:
    id: int
    items: list[Union[ModuloNumber, int]] = []
    operation: Callable[[int], int]
    divide_by: int
    throw_at: tuple[int, int]

    def __init__(self, id: int, items: list[Union[ModuloNumber, int]], operation: str, divide_by: int, thrown_at: tuple[int, int]) -> None:
        self.id = id
        self.items = items
        self.operation = lambda old: eval(operation)
        self.divide_by = divide_by
        self.throw_at = thrown_at

    def give_items(self, new_items: list[Union[ModuloNumber, int]]) -> None:
        self.items.extend(new_items)

    def do_turn(self, contain_worry: bool = True) -> dict[int, list[int]]:
        items_destination: dict[int, list[int]] = {self.throw_at[0]: [], self.throw_at[1]: []}
        for item in self.items:
            worry_level = int(self.operation(item) / 3.0) if contain_worry else self.operation(item)
            test = (worry_level % self.divide_by) == 0
            if test:
                items_destination[self.throw_at[0]].append(worry_level)
            else:
                items_destination[self.throw_at[1]].append(worry_level)

        self.items = []
        return items_destination

    def __repr__(self):
        return f'Monkey {self.id}: {self.items}'

    def __hash__(self):
        return hash((hash(self.id), hash(tuple(self.items))))

def solve_day11_part1(data: str) -> int:
    monkeys = parse_day11_input(data, item_generator=lambda x: x)

    items_inspected: list[int] = [0 for _ in range(len(monkeys))]

    for t in range(20):
        for monkey in monkeys:
            items_inspected[monkey.id] += len(monkey.items)
            thrown_items = monkey.do_turn()
            for destination_monkey in thrown_items.keys():
                monkeys[destination_monkey].give_items(thrown_items[destination_monkey])

    items_inspected.sort(reverse=True)
    return items_inspected[0] * items_inspected[1]

def solve_day11_part2(data: str) -> int:
    modulos = get_modulos(data)
    monkeys = parse_day11_input(data, lambda x: ModuloNumber(x, modulos))

    items_inspected: list[int] = [0 for _ in range(len(monkeys))]

    for t in range(10000):
        for monkey in monkeys:
            items_inspected[monkey.id] += len(monkey.items)
            thrown_items = monkey.do_turn(contain_worry=False)
            for destination_monkey in thrown_items.keys():
                monkeys[destination_monkey].give_items(thrown_items[destination_monkey])

    items_inspected.sort(reverse=True)
    return items_inspected[0] * items_inspected[1]

def parse_day11_input(data: str, item_generator: Callable[[int], Union[ModuloNumber, int]]) -> list[Monkey]:
    monkeys: list[Monkey] = []
    lines = data.splitlines()

    current_id: Union[int, None] = None
    current_items: Union[list[ModuloNumber], None] = None
    current_operation: Union[str, None] = None
    current_divide_by: Union[int, None] = None
    current_throw_at_true: Union[int, None] = None
    current_throw_at_false: Union[int, None] = None

    for line in lines:
        if line.strip() == '':
            monkey = Monkey(current_id, current_items, current_operation, current_divide_by, (current_throw_at_true, current_throw_at_false))
            monkeys.append(monkey)
            current_id = None
            continue
        if line.startswith("Monkey "):
            current_id = int(line[len("Monkey "):-1])
            continue
        if line.startswith("  Starting items: "):
            current_items = []
            items_number = list(map(int, line[len("  Starting items: "):].split(",")))
            for number in items_number:
                current_items.append(item_generator(number))
            continue
        if line.startswith("  Operation: new = "):
            operation = line[len("  Operation: new = "):].strip()
            current_operation = operation
            continue
        if line.startswith("  Test: divisible by "):
            current_divide_by = int(line[len("  Test: divisible by "):].strip())
            continue
        if line.startswith("    If true: throw to monkey "):
            current_throw_at_true = int(line[len("    If true: throw to monkey "):].strip())
            continue
        if line.startswith("    If false: throw to monkey "):
            current_throw_at_false = int(line[len("    If false: throw to monkey "):].strip())
            continue

    if current_id is not None:
        monkey = Monkey(current_id, current_items, current_operation, current_divide_by,
                        (current_throw_at_true, current_throw_at_false))
        monkeys.append(monkey)

    return monkeys

def get_modulos(data: str) -> list[int]:
    lines = data.splitlines()
    modulos: list[int] = []

    for line in lines:
        if line.startswith("  Test: divisible by "):
            divisible = int(line[len("  Test: divisible by "):].strip())
            modulos.append(divisible)

    return modulos