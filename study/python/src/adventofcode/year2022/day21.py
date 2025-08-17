import re
from abc import ABC
from collections import deque
from typing import Union

type Node = tuple[str, list[str], str]

def solve_day21_part1(data: str) -> float:
    operation: dict[str, Node] = parse_day21(data)
    return evaluate('root', operation, {})


def solve_day21_part2(data: str) -> float:
    operation: dict[str, Node] = parse_day21(data)
    results: dict[str, float] = {}
    evaluate('root', operation, results)

    to_recompute: set[str] = get_node_to_recompute(operation, 'humn')
    evaluate_part2('root', operation, results, to_recompute, 0)
    return results['humn']


def evaluate(key: str, operation: dict[str, Node], results: dict[str, float]) -> float:
    node = operation[key]

    # Simple value
    if len(node[1]) == 0:
        result = int(node[2])
    else:
        left = evaluate(node[1][0], operation, results)
        right = evaluate(node[1][1], operation, results)
        operator = node[2]
        if operator == '+':
            result = left + right
        elif operator == '-':
            result = left - right
        elif operator == '*':
            result = left * right
        elif operator == '/':
            result = left / right
        else:
            raise ValueError(f'Unknown operator {operator}')

    results[key] = result
    return result


def evaluate_part2(key: str, operation: dict[str, Node], results: dict[str, float], to_recompute: set[str], required_value: float) -> float:
    node = operation[key]

    if key not in to_recompute:
        return results[key]

    # Simple value
    if len(node[1]) == 0:
        result = required_value
    else:
        left_node = node[1][0]
        right_node = node[1][1]
        is_left_to_recompute = True if left_node in to_recompute else False
        known_value = 0
        unknown_value = 0
        if is_left_to_recompute:
            known_value = results[right_node]
        else:
            known_value = results[left_node]

        operator = node[2]
        if key == 'root':
            unknown_value = known_value
        elif operator == '+':
            # required_value = known_value + unknown_value
            unknown_value = required_value - known_value
        elif operator == '-':
            # required_value = known_value - unknown_value
            # required_value = unknown_value - known_value
            unknown_value = required_value + known_value if is_left_to_recompute else known_value - required_value
        elif operator == '*':
            # required_value = known_value * unknown_value
            unknown_value = required_value / known_value
        elif operator == '/':
            # required_value = known_value / unknown_value
            # required_value = unknown_value / known_value
            unknown_value = required_value * known_value if is_left_to_recompute else known_value / required_value
        else:
            raise ValueError(f'Unknown operator {operator}')

        node_to_evaluate = left_node if is_left_to_recompute else right_node
        result = evaluate_part2(node_to_evaluate, operation, results, to_recompute, unknown_value)

    results[key] = result
    return result


def get_node_to_recompute(operation: dict[str, Node], target: str) -> set[str]:
    parent_relation: dict[str, Union[str, None]] = {'root': None}

    for node in operation.keys():
        for child in operation[node][1]:
            if child in parent_relation:
                raise ValueError("This is not a tree!!!")
            parent_relation[child] = node

    to_recompute: set[str] = set('humn')
    node = target
    while node is not None:
        to_recompute.add(node)
        node = parent_relation[node]

    return to_recompute


def parse_day21(data: str) -> dict[str, Node]:
    graph: dict[str, Node] = {}
    regex = re.compile(r"(.+) ([+\-*/]) (.+)")

    lines = data.strip().splitlines()
    for line in lines:
        node, operation_str = line.split(": ")
        if '+' in operation_str or '-' in operation_str or '*' in operation_str or '/' in operation_str:
            matches = regex.match(operation_str)
            if not matches:
                raise ValueError(f'Invalid operator {operation_str}')
            left = matches.group(1)
            operator = matches.group(2)
            right = matches.group(3)

            graph[node] = (node, [left, right], operator)
        else:
            graph[node] = (node, [], operation_str)

    return graph