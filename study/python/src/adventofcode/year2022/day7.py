from typing import Union, Optional

from src.adventofcode.datastructures.trees import TreeNode, post_order_traversal


class INode:
    def __init__(self, name: str, size: Union[int, None]) -> None:
        self.name = name
        self.size = size

    def __repr__(self) -> str:
        return f"INode(name={self.name}, size={self.size})"

def solve_day7_part1(data: str) -> int:
    tree = parse_day7_input(data)
    for tree_node in post_order_traversal(tree):
        if tree_node.content.size is None:
            tree_node.content.size = sum(map(lambda v: v.content.size, tree_node.children))

    small_directories = map(
        lambda tree_node: tree_node.content,
        filter(
            lambda tree_node: len(tree_node.children) > 0 and tree_node.content.size <= 100000,
            post_order_traversal(tree)
        )
    )
    return sum(map(lambda i_node: i_node.size, small_directories))

def solve_day7_part2(data: str) -> int:
    total_space = 70000000
    required_space = 30000000

    tree = parse_day7_input(data)
    for tree_node in post_order_traversal(tree):
        if tree_node.content.size is None:
            tree_node.content.size = sum(map(lambda v: v.content.size, tree_node.children))

    available_space = total_space - tree.content.size
    to_free_space = required_space - available_space
    candidate_directories: list[INode] = list(map(
        lambda tree_node: tree_node.content,
        filter(
            lambda tree_node: len(tree_node.children) > 0 and tree_node.content.size >= to_free_space,
            post_order_traversal(tree)
        )
    ))

    smallest_directory = sorted(candidate_directories, key=lambda i_node: i_node.size)[0]
    return smallest_directory.size

def parse_day7_input(data: str) -> TreeNode[INode]:
    tree: Optional[TreeNode[INode]] = None

    current_node: Optional[TreeNode[INode]] = tree
    for line in data.splitlines():
        line = line.strip()
        if line == "$ cd ..":
          current_node = current_node.parent
        elif line.startswith("$ cd"):
            dir_name = line[len("$ cd "):]
            if tree is None:
                i_node = INode(dir_name, None)
                tree = TreeNode(i_node)
                current_node = tree
            else:
                found: bool = False
                for child in current_node.children:
                    if child.content.name == dir_name:
                        current_node = child
                        found = True
                if not found:
                    raise ValueError(f'Could not find {dir_name} in {current_node}')
        elif line.startswith("dir "):
            dir_name = line[len("dir "):]
            i_node = INode(dir_name, None)
            tree_node = TreeNode(i_node, current_node)
            current_node.children.append(tree_node)
        elif line == "$ ls":
            continue
        else:
            size, name = line.split(" ")
            i_node = INode(name, int(size))
            file_node = TreeNode(i_node, current_node)
            current_node.children.append(file_node)

    if tree is None:
        raise ValueError("No tree found")

    return tree


