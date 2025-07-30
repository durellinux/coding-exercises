import os


def read_input_file(file_path) -> str:
    """
    Reads the content of a file and returns it as a string.

    :param file_path: Path to the input file.
    :return: Content of the file as a string.
    """
    current_dir = os.path.dirname(os.path.abspath(__file__))
    with open(file_path, 'r') as file:
        return file.read()