from idlelib.tree import TreeNode
from typing import List, Generic, TypeVar, Union, Iterator, Optional

T = TypeVar('T')

class TreeNode(Generic[T]):
    def __init__(self, content: T, parent: Optional['TreeNode[T]'] = None) -> None:
        self.content: T = content
        self.parent: Optional[TreeNode[T]] = parent
        self.children: list[TreeNode[T]] = []

    def __repr__(self) -> str:
        return f'TreeNode(content={self.content}, parent={self.parent}, children={len(self.children)})'


def post_order_traversal(tree_node: TreeNode[T]) -> Iterator[TreeNode[T]]:
    for child in tree_node.children:
        yield from post_order_traversal(child)

    yield tree_node