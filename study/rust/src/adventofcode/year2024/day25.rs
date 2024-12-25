pub fn solve1(locks: Vec<Vec<usize>>, keys: Vec<Vec<usize>>, height: usize) -> usize {
    let mut result = 0;
    for lock in locks {
        for key in &keys {
            if match_key(&lock, key, height) {
                result += 1;
            }
        }
    }

    result
}

fn match_key(lock: &Vec<usize>, key: &Vec<usize>, max_value: usize) -> bool {
    for i in 0..lock.len() {
        if lock[i] + key[i] > max_value {
            return false;
        }
    }

    true
}

#[cfg(test)]
mod tests {
    use std::cmp::max;
    use crate::utils::file_utils::read_aoc_input_lines;
    use crate::utils::geometry::GenericError;
    use crate::utils::string_parsing::{as_char_list};
    use super::*;

    fn is_lock(schematic: &Vec<Vec<char>>) -> bool {
        schematic[0][0] == '#'
    }

    fn get_values(schematic: &Vec<Vec<char>>) -> Vec<usize> {
        let mut values = vec![];
        for c in 0..schematic[0].len() {
            let mut filled = 0;
            for r in 0..schematic.len() {
                if schematic[r][c] == '#' {
                    filled += 1;
                }
            }
            values.push(filled);
        }

        values
    }

    fn parse_input(name: &str) -> Result<(Vec<Vec<usize>>, Vec<Vec<usize>>, usize), GenericError> {
        let lines = read_aoc_input_lines(2024, name)?;
        let mut schematic: Vec<Vec<char>> = vec![];
        let mut locks: Vec<Vec<usize>> = vec![];
        let mut keys: Vec<Vec<usize>> = vec![];
        let mut max_height = 0;

        for line in lines {
            if line.is_empty() {
                let values = get_values(&schematic);
                if is_lock(&schematic) {
                    locks.push(values);
                } else {
                    keys.push(values);
                }

                max_height = schematic.len();
                schematic.clear();
            } else {
                let row = as_char_list(&line);
                schematic.push(row)
            }
        }

        let values = get_values(&schematic);
        if is_lock(&schematic) {
            locks.push(values);
        } else {
            keys.push(values);
        }

        Ok((locks, keys, max_height))
    }

    #[test]
    fn test_case_1() {
        let (locks, keys, height) = parse_input("day25-test").unwrap();
        let result = solve1(locks, keys, height);
        assert_eq!(result, 3);
    }

    #[test]
    fn test_solution_1() {
        let (locks, keys, height) = parse_input("day25").unwrap();
        let result = solve1(locks, keys, height);
        assert_eq!(result, 3196);
    }
}