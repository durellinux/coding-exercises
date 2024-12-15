use std::collections::{HashMap, HashSet};
use crate::utils::types::{Point, PointWithDirectionI32};

pub fn solve1(mut mat: Vec<Vec<char>>) -> i32 {
    let mut guard_position = find_guard(&mat).unwrap();
    let mut current_direction = 'N';
    let mut result = 1;
    mat[guard_position.x as usize][guard_position.y as usize] = 'X';

    while guard_position.x >= 0 && guard_position.x < mat.len() as i32 && guard_position.y >= 0 && guard_position.y < mat.len() as i32 {
        let direction = get_direction(&current_direction);
        let new_x = guard_position.x + direction.0;
        let new_y = guard_position.y + direction.1;

        let optional_next_cell = get_next_cell(&mat, new_x, new_y);
        if optional_next_cell.is_none() {
            return result
        }

        let next_cell = optional_next_cell.unwrap();
        if next_cell == '.' {
            mat[new_x as usize][new_y as usize] = 'X';
            result += 1;
            guard_position.x = new_x;
            guard_position.y = new_y;
        } else if next_cell == 'X' {
            guard_position.x = new_x;
            guard_position.y = new_y;
        } else if next_cell == '#' {
            current_direction = turn(current_direction);
        }
    }

    result
}

fn find_guard(mat: &Vec<Vec<char>>) -> Option<Point<i32>> {
    for i in 0..mat.len() {
        for j in 0..mat[0].len() {
            if mat[i][j] == '^' {
                return Some(Point {x: i as i32, y: j as i32})
            }
        }
    }

    None
}

fn get_next_cell(mat: &Vec<Vec<char>>, new_x: i32, new_y: i32) -> Option<char> {
    if new_x < 0 || new_x >= mat.len() as i32 || new_y < 0 || new_y >= mat[0].len() as i32 {
        return None
    }

    Some(mat[new_x as usize][new_y as usize])
}

fn get_direction(cardinal_point: &char) -> (i32, i32) {
    let directions: HashMap<char, (i32, i32)> = [
        ('N', (-1, 0)),
        ('S', (1, 0)),
        ('E', (0, 1)),
        ('W', (0, -1)),
    ].iter().cloned().collect();

    directions[cardinal_point]
}

fn turn(cardinal_point: char) -> char {
    if cardinal_point == 'N' {
        'E'
    } else if cardinal_point == 'E' {
        'S'
    } else if cardinal_point == 'S' {
        'W'
    } else if cardinal_point == 'W' {
        'N'
    } else {
        panic!("Unknown direction");
    }
}

pub fn solve2(mut mat: Vec<Vec<char>>) -> i32 {
    let initial_position = find_guard(&mat).unwrap();
    let mut guard_position = initial_position;

    let mut current_direction = 'N';
    let mut result = 0;
    mat[guard_position.x as usize][guard_position.y as usize] = 'X';

    while guard_position.x >= 0 && guard_position.x < mat.len() as i32 && guard_position.y >= 0 && guard_position.y < mat.len() as i32 {
        let direction = get_direction(&current_direction);
        let new_x = guard_position.x + direction.0;
        let new_y = guard_position.y + direction.1;

        let optional_next_cell = get_next_cell(&mat, new_x, new_y);
        if optional_next_cell.is_none() {
            return result
        }

        let next_cell = optional_next_cell.unwrap();
        if next_cell == '.' {
            mat[new_x as usize][new_y as usize] = 'X';
            let block_position = Point {x: new_x, y:new_y};

            if block_position != initial_position {
                let mut mat_copy = mat.clone();
                let loop_found = loop_detection(&mut mat_copy, &guard_position, current_direction, block_position);
                // if loop_found == 1 {
                //     println!("Loop at ({},{})", new_x, new_y);
                //     print_matrix(mat_copy);
                // }
                result += loop_found;
            }

            guard_position.x = new_x;
            guard_position.y = new_y;
        } else if next_cell == 'X' {
            guard_position.x = new_x;
            guard_position.y = new_y;
        } else if next_cell == '#' {
            current_direction = turn(current_direction);
        }
    }

    result
}

fn loop_detection(mat: &mut Vec<Vec<char>>, initial_position: &Point<i32>, block_direction: char, block_position: Point<i32>) -> i32 {
    let mut guard_position = initial_position.clone();
    let mut current_direction = block_direction;
    mat[block_position.x as usize][block_position.y as usize] = 'O';

    let mut visited: HashSet<PointWithDirectionI32<i32>> = HashSet::new();

    while guard_position.x >= 0 && guard_position.x < mat.len() as i32 && guard_position.y >= 0 && guard_position.y < mat.len() as i32 {
        let point_with_direction = PointWithDirectionI32{point: guard_position, direction: current_direction};
        if visited.contains(&point_with_direction) {
            return 1
        }
        visited.insert(point_with_direction);

        let direction = get_direction(&current_direction);
        let new_x = guard_position.x + direction.0;
        let new_y = guard_position.y + direction.1;

        let optional_next_cell = get_next_cell(&mat, new_x, new_y);
        if optional_next_cell.is_none() {
            return 0
        }

        let next_cell = optional_next_cell.unwrap();
        if next_cell == '.' {
            mat[new_x as usize][new_y as usize] = 'X';
            guard_position.x = new_x;
            guard_position.y = new_y;
        } else if next_cell == 'X' {
            guard_position.x = new_x;
            guard_position.y = new_y;
        } else if next_cell == '#' || next_cell == 'O' {
            current_direction = turn(current_direction);
        }
    }

    0
}

#[cfg(test)]
mod tests {
    use crate::utils::file_utils::read_file;
    use crate::utils::types::GenericError;
    use super::*;

    fn parse_input(content: String) -> Result<Vec<Vec<char>>, GenericError> {
        let lines: Vec<&str> = content.split("\n").collect();
        let mut mat: Vec<Vec<char>> = vec![];

        for line in lines {
            let values = line.chars().collect();
            mat.push(values);
        }

        Ok(mat)
    }

    #[test]
    pub fn test_case_1() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day6-test.txt".parse()?)?;
        let mat = parse_input(content)?;
        let result = solve1(mat);
        assert_eq!(result, 41);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day6.txt".parse()?)?;
        let mat = parse_input(content)?;
        let result = solve1(mat);
        assert_eq!(result, 4982);
        Ok(())
    }

    #[test]
    pub fn test_case_2() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day6-test.txt".parse()?)?;
        let mat = parse_input(content)?;
        let result = solve2(mat);
        assert_eq!(result, 6);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day6.txt".parse()?)?;
        let mat = parse_input(content)?;
        let result = solve2(mat);
        assert_eq!(result, 1663);
        Ok(())
    }
}