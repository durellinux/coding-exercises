import os

from src.adventofcode.utils import read_input_file

type Coordinate = tuple[int, int]
type Coordinate3D = tuple[int, int, int]

def read_input_2022(file_name: str) -> str:
    current_dir = os.path.dirname(os.path.abspath(__file__))
    full_path = os.path.join(current_dir, '../../../', 'resources', 'adventofcode', 'year2022', file_name)

    return read_input_file(full_path)